import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Mover {
	  protected int jumpable=0;
	  public int getJumpable(){return jumpable;}
	  public void incrementJumpable(){
		  jumpable+=1;
		  if(jumpable == 1){
			  //playJumpSound();
		  }
	  }
//Images======================================================
	  protected BufferedImage sprite;
	  public BufferedImage getSprite(){return sprite;}
	  public int getWidth(){return sprite.getWidth();}
	  public int getHeight(){return sprite.getHeight();}
//Positioning=================================================
	  protected int oldX, oldY;
	  protected int x,y;
	  protected boolean tethered = false;
	  protected boolean xlocked = false;
	  protected boolean ylocked= false;
	  
	  //Speed and acceleration
	  protected int xspeed = 0;
	  protected int yspeed = 1;
	  protected int yacc = 2;
	  protected int xacc = 0;
	  protected int topXSpeed = 15;
	  protected int topYSpeed = 15;
	  protected int gravity = yacc;
	  protected int xfriction = 1;
	  protected int yfriction = 0;
	  
	  //Speed and acceleration
	  public int getX()			{return x;}
	  public int getY()			{return y;}
	  public int getXspeed()	{return xspeed;}
	  public int getYspeed()	{return yspeed;}
	  public int getXacc()		{return xacc;}
	  public int getYacc()		{return yacc;}
	  public void setX(int x)		{this.x = x;}
	  public void setY(int y)		{this.y = y;}
	  public void setXspeed(int n)	{this.xspeed = n;}
	  public void setYspeed(int n)	{this.yspeed = n;}
	  public void setXacc(int n)	{this.xacc = n;}
	  public void setYacc(int n)	{this.yacc = n;}
//Boundary Checkers=============================================
	  public void move(){
		  
	      xspeed+=xacc;
	      if(xspeed>topXSpeed) xspeed = topXSpeed;
	      if(xspeed<-topXSpeed) xspeed = -topXSpeed;
	      
	      yspeed+=yacc;
	      if(yspeed>topYSpeed) yspeed = topYSpeed;
	      
	      if(!xlocked){
		      oldX=x;
		      x+=xspeed;
	      }
		  if(!ylocked){
		      oldY=y;
		      y+=yspeed;
	      }
	      
	      //friction
		  for(int i=xfriction;i>0;i--){
			  if(xspeed>0)	xspeed-=1;
			  if(xspeed<0)	xspeed+=1;
		  }
	     yacc = gravity;
		 
	     	  
	  }
	  public void checkWindowBoundaries(int windowWidth, int windowHeight){
		  if(x<0){
				x=0; xspeed = 0;
			}
			if(x>windowWidth-getWidth()){
				x=windowWidth-getWidth(); xspeed = 0;
			}
				
			if(y<0){
				y=0; yspeed = 0;
			}
			if(y>windowHeight-getHeight()){
				y=windowHeight-getHeight(); yspeed = 0;
				jumpable = 0;
			}
	  }
	  public void bounceWindowBoundaries(int windowWidth, int windowHeight){
		  if(x<getWidth()/2){
				x=getWidth()/2; xspeed = Math.abs(xspeed);
			}
			if(x>windowWidth-getWidth()/2){
				x=windowWidth-getWidth()/2; xspeed = -Math.abs(xspeed);
			}
				
			if(y<getHeight()){
				y=getHeight(); yspeed = Math.abs(yspeed);
			}
			if(y>windowHeight-getHeight()){
				y=windowHeight-getHeight(); yspeed = -Math.abs(yspeed);
			}
	  }
	  public void checkBlockBoundaries(ArrayList<Block> blocks){
			 
		  for(Block b: blocks){
			  //only check the block if the character is inside it.
			  if(b.withinBounds(x, y, getWidth(), getHeight())){
				  if(b.getType().equals("portal"))
					  DisplayFrame.levelChanged=true;
				  if(xspeed==0){
					  switch(yspeedSign()){
					  	//moving down
					  	case 1:		topCollision(b);	break;
					  	//moving up
					  	case -1:	bottomCollision(b); break;
					  }
				  }		
				  if(xspeed>0){
					  switch(yspeedSign()){
					  	//moving east
					  	case 0:		leftCollision(b);
					  					break;
					  	//moving southeast
					  	case 1:		if(this.withinXparam(b)){
					  					topCollision(b);
					  					break;
					  				}
					  				if(this.withinYparam(b)){
					  					leftCollision(b);
					  					break;
					  				}
					  				if(xspeed>yspeed)
		  								topCollision(b);
					  				else leftCollision(b);
					  					break;
					  	//moving northeast
					  	case -1:	if(this.withinXparam(b)){
					  					bottomCollision(b);
		  								break;
		  							}
		  							if(this.withinYparam(b)){
		  								leftCollision(b);
		  								break;
		  							}
					  				leftCollision(b);
					  					break;
					  }
				  }
				  if(xspeed<0){
					  switch(yspeedSign()){
					  	//moving west
					  	case 0:		rightCollision(b); 
					  					break;
					  	//moving southwest
					  	case 1:		if(this.withinXparam(b)){
		  								topCollision(b);
		  								break;
		  							}
		  							if(this.withinYparam(b)){
		  								rightCollision(b);
		  								break;
		  							}
		  							if(-xspeed>yspeed)
		  								topCollision(b);
		  							else rightCollision(b);
		  								break;
					  	//moving northwest
					  	case -1:	if(this.withinXparam(b)){
					  					bottomCollision(b);
					  					break;
					  				}
					  				if(this.withinYparam(b)){
					  					rightCollision(b);
					  					break;
					  				}
					  				rightCollision(b);
					  					break;
					  }
				  }
			  }//end withinBounds
		  }//end for
	  }//end check block boundaries
//Collision Basics==============================================
	  public void leftCollision(Block b){
		  if(!b.isPlatform()){
			  x = b.getX()-b.getWidth();
			  xspeed = 0;
			  yacc = gravity-yfriction;
		  }
	  }
	  public void rightCollision(Block b){
		  if(!b.isPlatform()){
			  x = b.getX()+b.getWidth();
			  xspeed = 0;
			  yacc = gravity-yfriction;
		  }
	  }
	  public void bottomCollision(Block b){
		  if(!b.isPlatform()){
			  y = b.getY()+b.getHeight();
			  yspeed = 0;
		  }
	  }
	  public void topCollision(Block b){
		  	  if(b.getType().equals("slowblock"))
		  		  xspeed = xspeed/3;
		  	  y = b.getY()-this.getHeight();
			  yspeed = -yacc;
			  jumpable = 0;
	  }
//Util==========================================================
	  public boolean withinXparam(Block b){
		  return (b.getX()<oldX+this.getWidth() && oldX<b.getX()+b.getWidth());
	  }
	  public boolean withinYparam(Block b){
		  if(b.isPlatform())
			  return (oldY+this.getHeight()-yacc<b.getY() && b.getY()<oldY+this.getHeight());
		  return (b.getY()<oldY+this.getHeight() && oldY<b.getY()+b.getHeight());
	  }
	  public int yspeedSign(){
		  if(yspeed>0) return 1;
		  if(yspeed<0) return -1;
		  return 0;
	  }
}
