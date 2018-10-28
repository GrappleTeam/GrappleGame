import common.Block;
import common.IBlock;
import common.Tile;
import mob.Mob;
import mob.MobPlayer;
import weapons.GrappleGun;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import static common.Block.Type.*;


public class GameLogic implements Runnable {

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
            ResourceLoader.INSTANCE.getImage(new Block(0, 0, 0, 0, PLAIN).getImageString()),
            ResourceLoader.INSTANCE.getImage(new Block(0, 0, 0, 0, DEATH).getImageString()),
            ResourceLoader.INSTANCE.getImage(new Block(0, 0, 0, 0, SLOW).getImageString()),
            ResourceLoader.INSTANCE.getImage(new Block(0, 0, 0, 0, PLATFORM).getImageString()),
            ResourceLoader.INSTANCE.getImage(new Block(0, 0, 0, 0, PORTAL).getImageString()),
    };
    static BufferedImage[] tileImages = {
            ResourceLoader.INSTANCE.getImage("Door01.png"),
            ResourceLoader.INSTANCE.getImage("GrassBlock01.png"),
            ResourceLoader.INSTANCE.getImage("CornerBlockTopRight.png"),
            ResourceLoader.INSTANCE.getImage("CornerBlockTopLeft.png"),
            ResourceLoader.INSTANCE.getImage("Horizontal.png"),
            ResourceLoader.INSTANCE.getImage("CornerBlockBottomRight.png"),
            ResourceLoader.INSTANCE.getImage("CornerBlockBottomLeft.png"),
            ResourceLoader.INSTANCE.getImage("BottomHorizontal.png"),
    };
    static BufferedImage[] BackgroundImages = {
            ResourceLoader.INSTANCE.getImage("sword-and-sworcery.png"),
            ResourceLoader.INSTANCE.getImage("windows_xp_bliss-wide.jpg"),
            ResourceLoader.INSTANCE.getImage("whiteBackground.png"),
    };
    //	//sound initialization
//		AudioInputStream audioIn;
//		Clip clip;
    URL soundtrack1, soundtrack2;
    URL currentSoundtrack;

    GameLogic() {
        j = new DisplayPanel();
        j.setPreferredSize(new Dimension(900, 600));
        inputList = ResourceLoader.INSTANCE.getImage("InputList2.png");
        soundtrack1 = ResourceLoader.INSTANCE.getSoundUrl("Grapple - Main Menu Theme - 5-17-14, 10.57 PM.wav");
        soundtrack2 = ResourceLoader.INSTANCE.getSoundUrl("Grapple - City (Level 01) - 5-27-14, 2.52 AM.wav");

        currentLevel = 0;
        levelArray = new ArrayList<>();
        levelArray.add(new Level(0, new Tile(0, 0, "whiteBackground.png"), soundtrack1));
        levelArray.add(new Level(1, new Tile(0, 0, "windows_xp_bliss-wide.jpg"), soundtrack2));
        levelArray.add(new Level(2, new Tile(0, 0, "whiteBackground.png"), soundtrack1));
        levelArray.add(new Level(3, new Tile(0, 0, "windows_xp_bliss-wide.jpg"), soundtrack1));
        levelArray.add(new Level(4, new Tile(0, 0, "sword-and-sworcery.png"), soundtrack1));
        levelArray.add(new Level(5, new Tile(0, 0, "sword-and-sworcery.png"), soundtrack1));

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
        if (Main.getLevelChanged()) {
            changeLevel();
        }
        Main.setLevelChanged(false);

        processInput();

        Level level = levelArray.get(currentLevel);
        Tile background = level.getBackground();
        if (character.getX() < 250 && character.getXspeed() < 0 &&
                background.getX() < 0) {

            character.setXlocked(true);
            displacement = character.getXspeed();
            if (background.getX() - displacement > 0)
                displacement = (background.getX() - displacement);
            background.setX(background.getX() - displacement);
            if (background.getX() > 0) {
                displacement -= background.getX();
            }
            for (IBlock b : level.getLevelBlocks())
                b.setX(b.getX() - displacement);
            for (Tile b : level.getLevelTiles())
                b.setX(b.getX() - displacement);


        }
        boolean b1 = background.getX() + level.getLevelWidth() * level.getBlockSize() > j.getWidth();
        if (character.getX() > j.getWidth() - 250
                && character.getXspeed() > 0
                && b1) {

            character.setXlocked(true);
            displacement = character.getXspeed();
            if (level.getLevelWidth() * level.getBlockSize() - displacement < j.getWidth()) {
                displacement = level.getLevelWidth() * level.getBlockSize() - displacement - j.getWidth();
            }
            for (IBlock b : level.getLevelBlocks()) {
                b.setX(b.getX() - character.getXspeed());

            }
            for (Tile b : level.getLevelTiles()) {
                b.setX(b.getX() - character.getXspeed());
            }
            background.setX(background.getX() - character.getXspeed());
        }

        //untested start
        boolean b2 = background.getY() + level.getLevelHeight() * level.getBlockSize() > j.getHeight();
        if (character.getY() > j.getHeight() - 250
                && character.getYspeed() > 0
                && b2) {

            character.setYlocked(true);
            displacement = character.getYspeed();
            if (level.getLevelHeight() * level.getBlockSize() - displacement < j.getHeight()) {
                displacement = level.getLevelHeight() * level.getBlockSize() - displacement - j.getHeight();
            }
            for (IBlock b : level.getLevelBlocks()) {
                b.setY(b.getY() - character.getYspeed());

            }
            for (Tile b : level.getLevelTiles()) {
                b.setY(b.getY() - character.getYspeed());
            }
            background.setY(background.getY() - character.getYspeed());
        }

        //untested end

        character.tetherMove();
        character.setXlocked(false);
        character.setYlocked(false);
        character.checkWindowBoundaries(j.getWidth(), j.getHeight());
        character.checkBlockBoundaries(level.getLevelBlocks());

        for (Mob c : level.getLevelMobs()) {
            c.pattern();
            c.checkWindowBoundaries(j.getWidth(), j.getHeight());
            c.checkBlockBoundaries(level.getLevelBlocks());
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

    public enum gamestate {main_screen, title_screen, gameplay_screen}

    public static gamestate current_gamestate = gamestate.main_screen;

    public void processInput() {
        if (upButtonPressed) character.jump();
        if (leftButtonPressed) character.setXacc(-2);
        if (rightButtonPressed) character.setXacc(2);
        if (downButtonPressed) character.setYspeed(character.getYspeed() + 2);
    }

    public JPanel getJPanel() {
        return j;
    }

}



