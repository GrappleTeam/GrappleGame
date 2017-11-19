import blocks.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

import static blocks.Block.Type.*;


public class GameLogic implements KeyListener, Runnable, MouseListener, MouseMotionListener {

    DisplayPanel j;

    int cycle = 0;
    static int currentLevel;
    final int maxLevel = 5;
    static ArrayList<Level> levelArray;
   static Mob character;
    boolean musicOn = true;

    boolean upButtonPressed = false;
    boolean leftButtonPressed = false;
    boolean rightButtonPressed = false;
    boolean downButtonPressed = false;
    boolean buttonReleased = false;

    static int mouseX;
    static int mouseY;
    static boolean mousePressed = false;
    int displacement;


    //Image initialization
    BufferedImage window, background0, background1, characterImage, inputList;
   static BufferedImage[] blockImages = {
            ImageUtils.getImage(new Block(0, 0, 0, 0, PLAIN).getImageString()),
            ImageUtils.getImage(new Block(0, 0, 0, 0, DEATH).getImageString()),
            ImageUtils.getImage(new Block(0, 0, 0, 0, SLOW).getImageString()),
            ImageUtils.getImage(new Block(0, 0, 0, 0, PLATFORM).getImageString()),
            ImageUtils.getImage(new Block(0, 0, 0, 0, PORTAL).getImageString()),
    };
 static   BufferedImage[] tileImages = {
            ImageUtils.getImage("graphics/Door01.png"),
            ImageUtils.getImage("graphics/GrassBlock01.png"),
            ImageUtils.getImage("graphics/CornerBlockTopRight.png"),
            ImageUtils.getImage("graphics/CornerBlockTopLeft.png"),
            ImageUtils.getImage("graphics/Horizontal.png"),
            ImageUtils.getImage("graphics/CornerBlockBottomRight.png"),
            ImageUtils.getImage("graphics/CornerBlockBottomLeft.png"),
            ImageUtils.getImage("graphics/BottomHorizontal.png"),
    };
   static BufferedImage[] BackgroundImages = {
            ImageUtils.getImage("graphics/sword-and-sworcery.png"),
            ImageUtils.getImage("graphics/windows_xp_bliss-wide.jpg"),
            ImageUtils.getImage("graphics/whiteBackground.png"),
    };
    //	//sound initialization
//		AudioInputStream audioIn;
//		Clip clip;
    URL soundtrack1, soundtrack2;
    URL currentSoundtrack;

    GameLogic() {
        j = new DisplayPanel();
        j.setPreferredSize(new Dimension(900, 600));
        inputList = ImageUtils.getImage("graphics/InputList2.png");
        soundtrack1 = this.getClass().getClassLoader().getResource("sounds/Grapple - Main Menu Theme - 5-17-14, 10.57 PM.wav");
        soundtrack2 = this.getClass().getClassLoader().getResource("sounds/Grapple - City (Level 01) - 5-27-14, 2.52 AM.wav");

        currentLevel = 0;
        levelArray = new ArrayList<>();
        levelArray.add(new Level(0, new Tile(0, 0, "graphics/whiteBackground.png"), soundtrack1));
        levelArray.add(new Level(1, new Tile(0, 0, "graphics/windows_xp_bliss-wide.jpg"), soundtrack2));
        levelArray.add(new Level(2, new Tile(0, 0, "graphics/whiteBackground.png"), soundtrack1));
        levelArray.add(new Level(3, new Tile(0, 0, "graphics/windows_xp_bliss-wide.jpg"), soundtrack1));
        levelArray.add(new Level(4, new Tile(0, 0, "graphics/sword-and-sworcery.png"), soundtrack1));
        levelArray.add(new Level(5, new Tile(0, 0, "graphics/sword-and-sworcery.png"), soundtrack1));

        character = new MobPlayer(5, 50, 50, 200);
        character.getWeaponArray().add(new GrappleGun());

//		fillClip();
//		currentSoundtrack = levelArray.get(currentLevel).getCurrentSoundtrack();
//		//runSoundFile(currentSoundtrack, 10);

    }

    public void fillClip() {
//		try{
//			clip = AudioSystem.getClip();
//		}
//	    catch (LineUnavailableException e) {
//	         e.printStackTrace();
//	    }
    }

    public void runSoundFile(URL soundUrl, int loops) {
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
    }

    public void run() {

        character.setXacc(0);
        if (DisplayFrame.Companion.getLevelChanged()) {
            changeLevel();
        }
        DisplayFrame.Companion.setLevelChanged(false);

        processInput();

        if (character.getX() < 250 && character.getXspeed() < 0 &&
                levelArray.get(currentLevel).getBackground().getX() < 0) {

            character.setXlocked(true);
            displacement = character.getXspeed();
            if (levelArray.get(currentLevel).getBackground().getX() - displacement > 0)
                displacement = (levelArray.get(currentLevel).getBackground().getX() - displacement);
            levelArray.get(currentLevel).getBackground().setX(levelArray.get(currentLevel).getBackground().getX() - displacement);
            if (levelArray.get(currentLevel).getBackground().getX() > 0) {
                displacement -= levelArray.get(currentLevel).getBackground().getX();
            }
            for (IBlock b : levelArray.get(currentLevel).getLevelBlocks())
                b.setX(b.getX() - displacement);
            for (Tile b : levelArray.get(currentLevel).getLevelTiles())
                b.setX(b.getX() - displacement);


        }
        if (character.getX() > j.getWidth() - 250 && character.getXspeed() > 0 &&
                levelArray.get(currentLevel).getBackground().getX() +
                        levelArray.get(currentLevel).levelWidth * levelArray.get(currentLevel).blockSize > j.getWidth()) {

            character.setXlocked(true);
            displacement = character.getXspeed();
            if (levelArray.get(currentLevel).levelWidth *
                    levelArray.get(currentLevel).blockSize - displacement < j.getWidth())
                displacement = levelArray.get(currentLevel).levelWidth *
                        levelArray.get(currentLevel).blockSize - displacement - j.getWidth();
            for (IBlock b : levelArray.get(currentLevel).getLevelBlocks())
                b.setX(b.getX() - character.getXspeed());
            for (Tile b : levelArray.get(currentLevel).getLevelTiles())
                b.setX(b.getX() - character.getXspeed());
            levelArray.get(currentLevel).getBackground().setX(levelArray.get(currentLevel).getBackground().getX() - character.getXspeed());
        }
//		if(character.getY()<getHeight()-250 && 
//				levelArray.get(currentLevel).getBackground().getY()+
//				levelArray.get(currentLevel).levelHeight*levelArray.get(currentLevel).blockSize>=getHeight()){
//			character.ylocked = true;
//			for(blocks.IBlock b : levelArray.get(currentLevel).getLevelBlocks())
//				b.setX(b.getX()-character.getYspeed());
//			for(Tile b : levelArray.get(currentLevel).getLevelTiles())
//				b.setX(b.getX()-character.getYspeed());
//			levelArray.get(currentLevel).getBackground().setY(levelArray.get(currentLevel).getBackground().getY()-character.getYspeed());
//			//character.setX(character.getX()-character.getXspeed());
//		}


        character.tetherMove();
        character.setXlocked(false);
        character.setYlocked(false);
        character.checkWindowBoundaries(j.getWidth(), j.getHeight());
        character.checkBlockBoundaries(levelArray.get(currentLevel).getLevelBlocks());

        for (Mob c : levelArray.get(currentLevel).getLevelMobs()) {
            c.pattern();
            c.checkWindowBoundaries(j.getWidth(), j.getHeight());
            c.checkBlockBoundaries(levelArray.get(currentLevel).getLevelBlocks());
        }

        j.repaint();
        cycle++;
        //System.out.println(cycle);
        try {
            Thread.sleep(29);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }

    }

    public void changeLevel() {
//		clip.stop();
//		clip.close();
        currentLevel += 1;
        //DisplayFrame.levelChanged = true;
        if (currentLevel > maxLevel)
            currentLevel = 0;
        currentSoundtrack = levelArray.get(currentLevel).getCurrentSoundtrack();
        //runSoundFile(currentSoundtrack, 10);
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //KEYLISTENER
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public void keyPressed(KeyEvent k) {
        switch (k.getKeyCode()) {

            case KeyEvent.VK_W:        //-------------
            case KeyEvent.VK_UP:
                upButtonPressed = true;
                break;

            case KeyEvent.VK_A:        //-------------
            case KeyEvent.VK_LEFT:
                leftButtonPressed = true;
                break;

            case KeyEvent.VK_D:        //-------------
            case KeyEvent.VK_RIGHT:
                rightButtonPressed = true;
                break;

            case KeyEvent.VK_S:        //-------------
            case KeyEvent.VK_DOWN:
                downButtonPressed = true;
                break;

            case KeyEvent.VK_SPACE:
                character.setX(character.getWeaponArray().get(0).getHitX());
                character.setY(character.getWeaponArray().get(0).getHitY());
                character.setYspeed(0);
                break;

            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);

            case KeyEvent.VK_T:
                if (current_gamestate == gamestate.main_screen)
                    current_gamestate = gamestate.gameplay_screen;
                else if (current_gamestate == gamestate.gameplay_screen)
                    current_gamestate = gamestate.main_screen;
                break;

            case KeyEvent.VK_L:
                DisplayFrame.Companion.setLevelChanged(true);
                break;
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

            default:
                break;
        }
    }

    public enum gamestate {main_screen, title_screen, gameplay_screen}

    public static    gamestate current_gamestate = gamestate.main_screen;
    public void processInput() {
        if (upButtonPressed) character.jump();
        if (leftButtonPressed) character.setXacc(-2);
        if (rightButtonPressed) character.setXacc(2);
        if (downButtonPressed) character.setYspeed(character.getYspeed() + 2);
    }

    public void keyReleased(KeyEvent k) {
        switch (k.getKeyCode()) {

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upButtonPressed = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftButtonPressed = false;
                character.setXacc(0);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightButtonPressed = false;
                character.setXacc(0);
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downButtonPressed = false;
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent k) {
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //MOUSELISTENER
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseX = e.getX();
        mouseY = e.getY();
        character.getWeaponArray().get(0).setHitX(mouseX);
        character.getWeaponArray().get(0).setHitY(mouseY);
        character.setTethered(true);

        character.setDistanceToGun(character.getDistance(character.getX(), character.getY(), character.getWeaponArray().get(0).getHitX(), character.getWeaponArray().get(0).getHitY()));
        e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        character.setYspeed(0);
        character.setXacc(0);
        character.setXspeed(0);
        mouseX = e.getX();
        mouseY = e.getY();
        character.setTethered(false);
        e.consume();
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        e.consume();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        e.consume();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public JPanel getJPanel() {
        return j;
    }
}



