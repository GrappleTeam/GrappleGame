package levels

import common.Block
import common.Block.Type.*
import common.IBlock
import common.Tile
import mob.Mob
import mob.MobChuChu
import mob.MobVenus
import java.awt.Toolkit
import java.net.URL
import java.util.*

class Level//Constructor
internal constructor(i: Int, val background: Tile, val currentSoundtrack: URL) {
    val levelBlocks: ArrayList<IBlock>
    val levelTiles: ArrayList<Tile>
    val levelMobs: ArrayList<Mob>

    var tk = Toolkit.getDefaultToolkit()
    var xSize = tk.screenSize.getWidth().toInt()
    var ySize = tk.screenSize.getHeight().toInt()

    var blockSize: Int = 0
    var thisLevelNumber: Int = 0
        internal set

    var level = Array(ySize / 10) { CharArray(xSize / 10) }
    //10 represents the smallest possible block size.
    var levelString: String? = null
    var levelWidth: Int = 0
    var levelHeight: Int = 0

    var template: Block? = null


    init {
        levelBlocks = ArrayList()
        levelTiles = ArrayList()
        levelMobs = ArrayList()
        thisLevelNumber = i

        when (i) {
            0 -> {
                blockSize = 24

                //levelSring is used to calculate URI
                //remaining ints are height and width respectively
                populateLevel("Level0.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
                //adding enemies to the array
                levelMobs.add(MobVenus(100, 200))
                levelMobs.add(MobChuChu(50, 5))
            }
            1 -> {
                blockSize = 24
                populateLevel("Level1.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
            }
            2 -> {
                blockSize = 24
                populateLevel("Level2.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
            }
            3 -> {
                blockSize = 24
                populateLevel("Level3.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
            }
            4 -> {
                blockSize = 24
                populateLevel("Level4.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
            }
            5 -> {
                blockSize = 24
                populateLevel("Level5.txt")
                addBlocksToBlockArray(levelHeight, levelWidth)
                addTilesToTileArray(levelHeight, levelWidth)
            }
            else -> println("levels.Level error")
        }//mobArray.add(new Enemy("Sprout", 5, 50, 5, getImage("resources.Sprout01.png"), false));
        //mobArray.add(new Enemy("venus", 5, 50, 5, getImage("resources.GroundVine02.png"), false));
        //				mobArray.add(new mob("mobius", 5, 50, 5, 7, 21, 1, 5,
        //						getImage("resources.LittleManAnimation.png"),
        //						getImage("resources.LittleManAnimation.png"),
        //						getImage("resources.LittleManAnimation.png")));
    }

    fun populateLevel(string: String) {
        var line: String? = null
        var currentline = 0
        val stream = this.javaClass.getResourceAsStream(string)
        val input = Scanner(stream)
        val temp = Array(70) { CharArray(70) }

        //filling a temporary array
        while (input.hasNextLine()) {
            line = input.nextLine()
            levelHeight++
            levelWidth = line!!.length

            for (a in 0 until levelWidth) {
                temp[currentline][a] = line[a]
            }
            currentline++
        }
        //flipping the array right side up
        for (a in 0 until levelHeight) {
            for (b in 0 until levelWidth) {
                level[levelHeight - a - 1][b] = temp[a][b]
            }
        }

        input.close()
    }

    fun addBlocksToBlockArray(height: Int, width: Int) {
        val addBlock = { x: Int, y: Int, type: Block.Type ->
            levelBlocks.add(Block(blockSize * x, ySize - blockSize * (y + 1), blockSize, blockSize, type))
        }
        for (y in 0..height) {
            for (x in 0..width) {
                when (level[y][x]) {
                    '1' -> addBlock(x, y, PLAIN)
                    '2' -> addBlock(x, y, DEATH)
                    '3' -> addBlock(x, y, SLOW)
                    'P' -> addBlock(x, y, PLATFORM)
                    'h' -> addBlock(x, y, PORTAL)
                }
            }
        }
    }

    fun addTilesToTileArray(height: Int, width: Int) {
        val addTile = { x: Int, y: Int, image: String? ->
            levelTiles.add(Tile(blockSize * x, ySize - blockSize * (y + 1), image))
        }
        for (y in 0..height) {
            for (x in 0..width) {
                when (level[y][x]) {
                    'D' -> addTile(x, y, "door")
                    'e' -> addTile(x, y, "grass")
                    '>' -> addTile(x, y, "topright")
                    '<' -> addTile(x, y, "topleft")
                    'f' -> addTile(x, y, "horizontal")
                    '(' -> addTile(x, y, "bottomleft")
                    ')' -> addTile(x, y, "bottomright")
                    'F' -> addTile(x, y, "bottomhorizontal")
                }
            }
        }
    }
}
