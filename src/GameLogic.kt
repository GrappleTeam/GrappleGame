import common.Block
import common.Tile
import mob.Mob
import mob.MobPlayer
import weapons.GrappleGun
import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage
import java.net.URL
import java.util.ArrayList
import common.Block.Type.*
import common.IBlock

class GameLogic constructor() : Runnable {
    var j: DisplayPanel = DisplayPanel()
    var cycle = 0
    private val maxLevel = 5
    var musicOn = true
    var upButtonPressed = false
    var leftButtonPressed = false
    var rightButtonPressed = false
    var downButtonPressed = false
    var displacement: Int = 0
    var inputList: BufferedImage
    //	//sound initialization
    //		AudioInputStream audioIn;
    //		Clip clip;
    var soundtrack1: URL
    var soundtrack2: URL

    //    internal var currentSoundtrack: URL
    init {
        j.preferredSize = Dimension(900, 600)
        inputList = ResourceLoader.getImage("InputList2.png")
        soundtrack1 = ResourceLoader.getSoundUrl("Grapple - Main Menu Theme - 5-17-14, 10.57 PM.wav")
        soundtrack2 = ResourceLoader.getSoundUrl("Grapple - City (Level 01) - 5-27-14, 2.52 AM.wav")
        currentLevel = 0

        character = MobPlayer(5, 50, 50, 200)
        character.weaponArray.add(GrappleGun())
        //		fillClip();
        //		currentSoundtrack = levelArray.get(currentLevel).getCurrentSoundtrack();
        //		//runSoundFile(currentSoundtrack, 10);
    }

    fun fillClip() {
        //		try{
        //			clip = AudioSystem.getClip();
        //		}
        //	    catch (LineUnavailableException e) {
        //	         e.printStackTrace();
        //	    }
    }

    fun runSoundFile(soundUrl: URL, loops: Int) {
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

    override fun run() {
        character.xacc = 0
        if (Main.levelChanged) {
            changeLevel()
        }
        Main.levelChanged = false

        processInput()
        val level = levelArray[currentLevel]
        val background = level.background
        if (character.x < 250 && character.xspeed < 0 &&
                background.x < 0) {
            character.xlocked = true
            displacement = character.xspeed
            if (background.x - displacement > 0)
                displacement = background.x - displacement
            background.x = background.x - displacement
            if (background.x > 0) {
                displacement -= background.x
            }
            level.levelTiles.forEach { b -> b.x = b.x - displacement }
        }
        val b1 = background.x + level.levelWidth * level.blockSize > j.width
        if (character.x > j.width - 250
                && character.xspeed > 0
                && b1) {
            character.xlocked = true
            displacement = character.xspeed
            if (level.levelWidth * level.blockSize - displacement < j.width) {
                displacement = level.levelWidth * level.blockSize - displacement - j.width
            }
            level.levelTiles.forEach { it.x = it.x - character.xspeed }
            background.x = background.x - character.xspeed
        }
        //untested start
        val b2 = background.y + level.levelHeight * level.blockSize > j.height
        if (character.y > j.height - 250
                && character.yspeed > 0
                && b2) {
            character.ylocked = true
            displacement = character.yspeed
            if (level.levelHeight * level.blockSize - displacement < j.height) {
                displacement = level.levelHeight * level.blockSize - displacement - j.height
            }
            level.levelTiles.forEach { b -> b.y = b.y - character.yspeed }
            background.y = background.y - character.yspeed
        }
        //untested end
        character.run {
            tetherMove()
            xlocked = false
            ylocked = false
            checkWindowBoundaries(j.width, j.height)
            checkBlockBoundaries(level.levelTiles.filterIsInstance(IBlock::class.java))
        }

        level.levelMobs.forEach {
            it.pattern()
            it.checkWindowBoundaries(j.width, j.height)
            it.checkBlockBoundaries(level.levelTiles.filterIsInstance(IBlock::class.java))
        }

        j.repaint()
        cycle++
        //System.out.println(cycle);
        try {
            Thread.sleep(29)
        } catch (e: InterruptedException) {
            println("Thread was interrupted.")
        }
    }

    fun changeLevel() {
        //		clip.stop();
        //		clip.close();
        currentLevel += 1
        //DisplayFrame.levelChanged = true;
        if (currentLevel > maxLevel)
            currentLevel = 0
//        currentSoundtrack = levelArray[currentLevel].currentSoundtrack
        //runSoundFile(currentSoundtrack, 10);
    }

    enum class Gamestate {
        MAIN_SCREEN, TITLE_SCREEN, GAMEPLAY_SCREEN
    }

    private fun processInput() {
        if (upButtonPressed) character.jump()
        if (leftButtonPressed) character.xacc = -2
        if (rightButtonPressed) character.xacc = 2
        if (downButtonPressed) character.yspeed = character.yspeed + 2
    }

    companion object {
        var currentLevel: Int = 0
        var levelArray: ArrayList<Level> = arrayListOf(
                Level(0, Tile(0, 0, "whiteBackground.png")),
                Level(1, Tile(0, 0, "windows_xp_bliss-wide.jpg")),
                Level(2, Tile(0, 0, "whiteBackground.png")),
                Level(3, Tile(0, 0, "windows_xp_bliss-wide.jpg")),
                Level(4, Tile(0, 0, "sword-and-sworcery.png")),
                Level(5, Tile(0, 0, "sword-and-sworcery.png"))
        )
        var character: Mob = MobPlayer(1, 1, 20, 20)
        var mouseX: Int = 0
        var mouseY: Int = 0
        var mousePressed = false
        var current_gamestate = Gamestate.MAIN_SCREEN
    }
}



