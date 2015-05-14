import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Level {
	
	private Tile background;
	private ArrayList<Block> blockArray;
	private ArrayList<Tile> tileArray;
	private ArrayList<Mob> mobArray;
	
	Toolkit tk = Toolkit.getDefaultToolkit();  
	int xSize = ((int) tk.getScreenSize().getWidth());  
	int ySize = ((int) tk.getScreenSize().getHeight());
	
	int blockSize;
	int thisLevelNumber;
	private URL currentSoundtrack;
	
	char[][] level = new char[ySize/10][xSize/10];
	//10 represents the smallest possible block size.
	String levelString = null;
	int levelWidth;
	int levelHeight;
	
	Block_Plain template;

	
	//Constructor
	Level(int i, Tile background0, URL soundtrack){
		blockArray = new ArrayList<Block>();
		tileArray = new ArrayList<Tile>();
		mobArray = new ArrayList<Mob>();
		this.background = background0;
		thisLevelNumber=i;
		currentSoundtrack = soundtrack;
		
		switch(i){
			case 0:
				blockSize = 24;
				levelString = "Level0.txt";
				//levelSring is used to calculate URI
				//remaining ints are height and width respectively
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
				//adding enemies to the array
				mobArray.add(new Mob_Venus(100, 200));
				mobArray.add(new Mob_ChuChu(50, 5));
				break;
			case 1:
				blockSize = 24;
				levelString = "Level1.txt";
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
				//mobArray.add(new Enemy("Sprout", 5, 50, 5, getImage("graphics/Sprout01.png"), false));
				break;
			case 2:
				blockSize = 24;
				levelString = "Level2.txt";
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
				//mobArray.add(new Enemy("venus", 5, 50, 5, getImage("graphics/GroundVine02.png"), false));
				break;
			case 3:
				blockSize = 24;
				levelString = "Level3.txt";
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
//				mobArray.add(new Mob("mobius", 5, 50, 5, 7, 21, 1, 5, 
//						getImage("graphics/LittleManAnimation.png"),
//						getImage("graphics/LittleManAnimation.png"),
//						getImage("graphics/LittleManAnimation.png")));
				break;
			case 4:
				blockSize = 24;
				levelString = "Level4.txt";
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
				break;
			case 5:
				blockSize = 24;
				levelString = "Level5.txt";
				populateLevel(levelString);
				addBlocksToBlockArray(levelHeight,levelWidth);
				addTilesToTileArray(levelHeight, levelWidth);
				break;
			default:	System.out.println("Level error");
		}
	}
	
	public BufferedImage getImage(String imageUrl){
		try {
			return ImageIO.read(Display_Panel.class.getResource(imageUrl));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void populateLevel(String string){
		String line = null;
		int currentline = 0;
		InputStream stream = this.getClass().getResourceAsStream(string);
		Scanner input = new Scanner(stream);
		char[][] temp = new char[70][70];
			
		//filling a temporary array
		while (input.hasNextLine()){
			line = input.nextLine();
			levelHeight++;
			levelWidth=line.length(); 
			
			for(int a=0;a<levelWidth;a++){
				temp[currentline][a] = line.charAt(a);
		    }
			currentline++;
		}
		//flipping the array right side up
		for(int a=0;a<levelHeight;a++){
			for(int b=0;b<levelWidth;b++){
				level[levelHeight-a-1][b] = temp[a][b];
		    }
		}
				
		input.close();
	}
	
	public void addBlocksToBlockArray(int height, int width){
		for(int y=0;y<=height;y++){
			for(int x=0;x<=width;x++){
				if(level[y][x] == '1'){
					blockArray.add(new Block_Plain(blockSize*x, ySize-blockSize*(y+1), blockSize, blockSize));
				}
				if(level[y][x] == '2'){
					blockArray.add(new Block_Death(blockSize*x, ySize-blockSize*(y+1), blockSize, blockSize));
				}
				if(level[y][x] == '3'){
					blockArray.add(new Block_Slow(blockSize*x, ySize-blockSize*(y+1), blockSize, blockSize));
				}
				if(level[y][x] == 'P'){
					blockArray.add(new Block_Platform(blockSize*x, ySize-blockSize*(y+1), blockSize, blockSize));
				}
				if(level[y][x] == 'h'){
					blockArray.add(new Block_Portal(blockSize*x, ySize-blockSize*(y+1), blockSize, blockSize));
				}
			}
		}
	}
	public void addTilesToTileArray(int height, int width){
		for(int y=0;y<=height;y++){
			for(int x=0;x<=width;x++){
				if(level[y][x] == 'D'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "door"));
				}
				if(level[y][x] == 'e'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "grass"));
				}
				if(level[y][x] == '>'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "topright"));
				}
				if(level[y][x] == '<'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "topleft"));
				}
				if(level[y][x] == 'f'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "horizontal"));
				}
				if(level[y][x] == '('){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "bottomleft"));
				}
				if(level[y][x] == ')'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "bottomright"));
				}
				if(level[y][x] == 'F'){
					tileArray.add(new Tile(blockSize*x, ySize-blockSize*(y+1), "bottomhorizontal"));
				}
			}
		}
	}
	public int getThisLevelNumber(){
		return thisLevelNumber;
	}
	public Tile getBackground(){
		return background;
	}
	
	public ArrayList<Block> getLevelBlocks(){
		return blockArray;
	}
	public ArrayList<Mob> getLevelMobs(){
		return mobArray;
	}
	public ArrayList<Tile> getLevelTiles(){
		return tileArray;
	}
	public URL getCurrentSoundtrack(){
		return currentSoundtrack;
	}
}
