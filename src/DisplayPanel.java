import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;


public class DisplayPanel extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener{
	
	static final int standing = 0;
	static final int walkingleft = 1;
	static final int walkingright = 2;
	static final int jumping = 3;
	
	int cycle = 0;
	static int currentLevel;
	final int maxLevel = 5;
	static ArrayList<Level> levelArray;
	Mob character;
	boolean musicOn = true;
		
	boolean upButtonPressed = false;
	boolean leftButtonPressed = false;
	boolean rightButtonPressed = false; 
	boolean downButtonPressed = false;
	boolean buttonReleased= false;
		
	static int mouseX;
	static int mouseY;	
	static boolean mousePressed = false;
	int displacement;
	
	
	//Image initialization 
		BufferedImage window, background0, background1, characterImage, inputList;
		
		BufferedImage[] blockImages = {
				getImage(new Block_Plain(0,0,0,0).getImageString()),
				getImage(new Block_Death(0,0,0,0).getImageString()),
				getImage(new Block_Slow(0,0,0,0).getImageString()),
				getImage(new Block_Platform(0,0,0,0).getImageString()),
				getImage(new Block_Portal(0,0,0,0).getImageString()),
		};
		
		BufferedImage[] tileImages = {
				getImage("graphics/Door01.png"),
				getImage("graphics/GrassBlock01.png"),
				getImage("graphics/CornerBlockTopRight.png"),
				getImage("graphics/CornerBlockTopLeft.png"),
				getImage("graphics/Horizontal.png"),
				getImage("graphics/CornerBlockBottomRight.png"),
				getImage("graphics/CornerBlockBottomLeft.png"),
				getImage("graphics/BottomHorizontal.png"),
		};
		
		BufferedImage[] BackgroundImages = {
				getImage("graphics/sword-and-sworcery.png"),
			    getImage("graphics/windows_xp_bliss-wide.jpg"),
				getImage("graphics/whiteBackground.png"),
		};
		
	//sound initialization
//		AudioInputStream audioIn;
//		Clip clip;
		URL soundtrack1, soundtrack2;
		URL currentSoundtrack;
		
	DisplayPanel(){
		
		    inputList = getImage("graphics/InputList2.png");		    		    
		    soundtrack1 = this.getClass().getClassLoader().getResource("sounds/Grapple - Main Menu Theme - 5-17-14, 10.57 PM.wav");
		    soundtrack2 = this.getClass().getClassLoader().getResource("sounds/Grapple - City (Level 01) - 5-27-14, 2.52 AM.wav");
		
		currentLevel = 0;
		levelArray = new ArrayList<Level>();
		levelArray.add(new Level(0, new Tile(0,0,"graphics/whiteBackground.png"), soundtrack1));
		levelArray.add(new Level(1, new Tile(0,0,"graphics/windows_xp_bliss-wide.jpg"), soundtrack2));
		levelArray.add(new Level(2, new Tile(0,0,"graphics/whiteBackground.png"), soundtrack1));
		levelArray.add(new Level(3, new Tile(0,0,"graphics/windows_xp_bliss-wide.jpg"), soundtrack1));
		levelArray.add(new Level(4, new Tile(0,0,"graphics/sword-and-sworcery.png"), soundtrack1));
		levelArray.add(new Level(5, new Tile(0,0,"graphics/sword-and-sworcery.png"), soundtrack1));
		
		character=new Mob_Player(5, 50, 50, 200);
		character.weaponArray.add(new GrappleGun());
				
//		fillClip();
//		currentSoundtrack = levelArray.get(currentLevel).getCurrentSoundtrack();
//		//runSoundFile(currentSoundtrack, 10);
		
	}
	
	public BufferedImage getImage(String imageUrl){
		try {
			return ImageIO.read(DisplayPanel.class.getResource(imageUrl));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public void fillClip(){
//		try{
//			clip = AudioSystem.getClip();
//		}
//	    catch (LineUnavailableException e) {
//	         e.printStackTrace();
//	    }
//	}
//	public void runSoundFile(URL soundUrl, int loops){
//		try {
//	         // Open an audio input stream.
//	         audioIn = AudioSystem.getAudioInputStream(soundUrl);
//	         // Get a sound clip resource.
//	         //clip = AudioSystem.getClip();
//	         // Open audio clip and load samples from the audio input stream.
//	         clip.open(audioIn);
//	         if(musicOn)clip.loop(loops);
//
//	      } catch (UnsupportedAudioFileException e) {
//	         e.printStackTrace();
//	      } catch (IOException e) {
//	         e.printStackTrace();
//	      } catch (LineUnavailableException e) {
//	         e.printStackTrace();
//	      }
//	}
	
	public void run(){
		
		character.setXacc(0);
		if(DisplayFrame.levelChanged){
			changeLevel();
		}
		DisplayFrame.levelChanged = false;
		
		processInput();
		
		if(character.getX()<250 && character.getXspeed()<0 &&
				levelArray.get(currentLevel).getBackground().getX()<0){
			
			character.xlocked = true;
			displacement = character.getXspeed();
			if(levelArray.get(currentLevel).getBackground().getX()-displacement > 0)
				displacement = (levelArray.get(currentLevel).getBackground().getX()-displacement);
			levelArray.get(currentLevel).getBackground().setX(levelArray.get(currentLevel).getBackground().getX()-displacement);
			if(levelArray.get(currentLevel).getBackground().getX()>0){
				displacement-= levelArray.get(currentLevel).getBackground().getX();
			}
			for(Block b : levelArray.get(currentLevel).getLevelBlocks())
				b.setX(b.getX()-displacement);
			for(Tile b : levelArray.get(currentLevel).getLevelTiles())
				b.setX(b.getX()-displacement);
			
			
		}
		if(character.getX()>getWidth()-250 && character.getXspeed()>0 &&
				levelArray.get(currentLevel).getBackground().getX()+
				levelArray.get(currentLevel).levelWidth*levelArray.get(currentLevel).blockSize>getWidth()){
			
			character.xlocked = true;
			displacement = character.getXspeed();
			if(levelArray.get(currentLevel).levelWidth*
					levelArray.get(currentLevel).blockSize-displacement<getWidth())
				displacement = levelArray.get(currentLevel).levelWidth*
						levelArray.get(currentLevel).blockSize-displacement-getWidth();
			for(Block b : levelArray.get(currentLevel).getLevelBlocks())
				b.setX(b.getX()-character.getXspeed());
			for(Tile b : levelArray.get(currentLevel).getLevelTiles())
				b.setX(b.getX()-character.getXspeed());
			levelArray.get(currentLevel).getBackground().setX(levelArray.get(currentLevel).getBackground().getX()-character.getXspeed());
		}
//		if(character.getY()<getHeight()-250 && 
//				levelArray.get(currentLevel).getBackground().getY()+
//				levelArray.get(currentLevel).levelHeight*levelArray.get(currentLevel).blockSize>=getHeight()){
//			character.ylocked = true;
//			for(Block b : levelArray.get(currentLevel).getLevelBlocks())
//				b.setX(b.getX()-character.getYspeed());
//			for(Tile b : levelArray.get(currentLevel).getLevelTiles())
//				b.setX(b.getX()-character.getYspeed());
//			levelArray.get(currentLevel).getBackground().setY(levelArray.get(currentLevel).getBackground().getY()-character.getYspeed());
//			//character.setX(character.getX()-character.getXspeed());
//		}
		
	
		character.tetherMove();
		character.xlocked = false;
		character.ylocked = false;
		character.checkWindowBoundaries(getWidth(), getHeight());
		character.checkBlockBoundaries(levelArray.get(currentLevel).getLevelBlocks());
		
		
		
		for(Mob c: levelArray.get(currentLevel).getLevelMobs()){
			c.pattern();
			c.checkWindowBoundaries(getWidth(), getHeight());
			c.checkBlockBoundaries(levelArray.get(currentLevel).getLevelBlocks());
		}
		
		repaint();
		cycle++;
		
		try{
			Thread.sleep(29);
		}
		catch(InterruptedException e){
			System.out.println("Thread was interrupted.");
		}

	}
	
	public void changeLevel(){
//		clip.stop();
//		clip.close();
		currentLevel+=1;
		//DisplayFrame.levelChanged = true;
		if(currentLevel>maxLevel)
			currentLevel = 0;
		currentSoundtrack = levelArray.get(currentLevel).getCurrentSoundtrack();
		//runSoundFile(currentSoundtrack, 10);	
	}

	public void paintComponent(Graphics g){
	     
		super.paintComponent(g);
			//drawing the background
			if(levelArray.get(currentLevel).getBackground().getImage().equals("graphics/sword-and-sworcery.png"))
		    	g.drawImage(BackgroundImages[0],
		    			levelArray.get(currentLevel).getBackground().getX(), 
		    			levelArray.get(currentLevel).getBackground().getY(),
		    			null);
			if(levelArray.get(currentLevel).getBackground().getImage().equals("graphics/windows_xp_bliss-wide.jpg"))
			    g.drawImage(BackgroundImages[1],
	    				levelArray.get(currentLevel).getBackground().getX(), 
	    				levelArray.get(currentLevel).getBackground().getY(),
	    				null);
			if(levelArray.get(currentLevel).getBackground().getImage().equals("graphics/whiteBackground.png"))
			    g.drawImage(BackgroundImages[2],
	    				levelArray.get(currentLevel).getBackground().getX(), 
	    				levelArray.get(currentLevel).getBackground().getY(),
	    				null);
						
			//drawing the tiles
			for(Tile t: levelArray.get(currentLevel).getLevelTiles()){
				if(t.getImage().equals("door"))
					g.drawImage(tileImages[0], t.getX(), t.getY(), this);
				if(t.getImage().equals("grass"))
					g.drawImage(tileImages[1], t.getX(), t.getY(), this);
				if(t.getImage().equals("topright"))
					g.drawImage(tileImages[2], t.getX(), t.getY(), this);
				if(t.getImage().equals("topleft"))
					g.drawImage(tileImages[3], t.getX(), t.getY(), this);
				if(t.getImage().equals("horizontal"))
					g.drawImage(tileImages[4], t.getX(), t.getY(), this);
				if(t.getImage().equals("bottomright"))
					g.drawImage(tileImages[5], t.getX(), t.getY(), this);
				if(t.getImage().equals("bottomleft"))
					g.drawImage(tileImages[6], t.getX(), t.getY(), this);
				if(t.getImage().equals("bottomhorizontal"))
					g.drawImage(tileImages[7], t.getX(), t.getY(), this);
			}
			
			//drawing the blocks
			for(Block b: levelArray.get(currentLevel).getLevelBlocks()){
				if(b.getType().equals("standard"))
					g.drawImage(blockImages[0], b.getX(), b.getY(), this);
				if(b.getType().equals("deathblock"))
					g.drawImage(blockImages[1], b.getX(), b.getY(), this);
				if(b.getType().equals("slowblock"))
					g.drawImage(blockImages[2], b.getX(), b.getY(), this);
				if(b.getType().equals("platformblock"))
					g.drawImage(blockImages[3], b.getX(), b.getY(), this);
				if(b.getType().equals("portal"))
					g.drawImage(blockImages[4], b.getX(), b.getY(), this);
			}
			
			//drawing the character
			if(character.jumpable==0){
				if(character.xspeed <0) character.state=1;
				if(character.xspeed==0) character.state=0;
				if(character.xspeed <0) character.state=2;
			}
			else character.state=3;

			g.drawImage(character.getSprite(), character.getX(), character.getY(), this);
			//drawing the mobs
			for(Mob m: levelArray.get(currentLevel).getLevelMobs())
				g.drawImage(m.getSprite(), m.getX(), m.getY(), this);
					
			//drawing the grapple
			if(character.tethered){
				g.setColor(Color.black);
				g.drawLine(character.getX()+character.getWidth()/2,
						   character.getY()+character.getHeight()/2, 
						   character.weaponArray.get(0).getHitX(), 
						   character.weaponArray.get(0).getHitY());
			}
			
			
			//drawing the red aiming circle	
				int charCenterX = character.getX()+(character.getWidth()/2);
				int charCenterY = character.getY()+(character.getHeight()/2);

				int r = -28;
				
				int vectorx = character.getCircleProjection(charCenterX, charCenterY, mouseX, mouseY, r);
				int vectory = character.getCircleProjection(charCenterY, charCenterX, mouseY, mouseX, r);
				
				//create a vector by b-a for points
				g.setColor(Color.RED);
				g.fillOval(vectorx-5, vectory-5, 10, 10);
				
				//g.drawImage(testanimation[cycle%testanimation.length], 40, 40, this);
	  }
	
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	//KEYLISTENER
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

		public void keyPressed(KeyEvent k) {
			switch(k.getKeyCode()){
			
			case KeyEvent.VK_W:		//-------------
			case KeyEvent.VK_UP:	upButtonPressed = true;		break;
			
			case KeyEvent.VK_A:		//-------------
			case KeyEvent.VK_LEFT:	leftButtonPressed = true;	break;
			
			case KeyEvent.VK_D:		//-------------
			case KeyEvent.VK_RIGHT: rightButtonPressed = true;	break;
			
			case KeyEvent.VK_S:		//-------------
			case KeyEvent.VK_DOWN: 	downButtonPressed = true;	break;
			
			case KeyEvent.VK_SPACE:	character.setX(character.weaponArray.get(0).getHitX());
									character.setY(character.weaponArray.get(0).getHitY());
									character.setYspeed(0);
									break;
			
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_Q:		System.exit(0);
												
			case KeyEvent.VK_L:		DisplayFrame.levelChanged = true;	break;
//			case KeyEvent.VK_M:		if(musicOn){
//										musicOn = false;
//										if(clip.isRunning())
//											clip.stop();
//									}
//									else{
//										musicOn = true;
//										clip.start();
//									}
//									break;
												
			default: break;
			}	
		}
		public void processInput(){
			if(upButtonPressed)		character.jump();
			if(leftButtonPressed)	character.setXacc(-2);
			if(rightButtonPressed)	character.setXacc(2);
			if(downButtonPressed) 	character.setYspeed(character.getYspeed()+2);
		}	
		public void keyReleased(KeyEvent k) {
			switch(k.getKeyCode()){
			
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:	upButtonPressed = false;	
									break;
			
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:	leftButtonPressed = false;	
									character.setXacc(0);
									break;
			
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT: rightButtonPressed = false;	
									character.setXacc(0);
									break;
			
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN: 	downButtonPressed = false;	
									break;
			default: break;
			}	
		}
		public void keyTyped(KeyEvent k) {}
		
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
	//MOUSELISTENER
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		public void mousePressed(MouseEvent e)
		{
			mousePressed = true;
			mouseX = e.getX();
			mouseY = e.getY();
			character.weaponArray.get(0).setHitX(mouseX);
			character.weaponArray.get(0).setHitY(mouseY);
			character.tethered = true;
			
			character.distanceToGun = character.getDistance(character.getX(), character.getY(), character.weaponArray.get(0).getHitX(), character.weaponArray.get(0).getHitY());
			e.consume();
		}
		public void mouseReleased(MouseEvent e){
			mousePressed = false;
			character.setYspeed(0);
			character.setXacc(0);
			character.setXspeed(0);
			mouseX = e.getX();
			mouseY = e.getY();
			character.tethered = false;
			e.consume();
		}
		public void mouseClicked(MouseEvent e)
		{
			mouseX = e.getX();
			mouseY = e.getY();
			e.consume();
		}
		public void mouseEntered(MouseEvent e){

		}
		public void mouseExited(MouseEvent e){

		}
		public void mouseDragged(MouseEvent e) {

		}
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			e.consume();
		}
	//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}

