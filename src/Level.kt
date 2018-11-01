import common.Block
import common.Block.Type.*
import common.IBlock
import common.ITile
import common.Tile
import mob.Mob
import mob.MobChuChu
import mob.MobVenus
import java.awt.Toolkit
import java.util.*

class Level internal constructor(val levelNumber: Int, val background: Tile) {
    val levelTiles: ArrayList<ITile> = ArrayList()
    val levelMobs: ArrayList<Mob> = ArrayList()
    var tk = Toolkit.getDefaultToolkit()
    var xSize = tk.screenSize.getWidth().toInt()
    var ySize = tk.screenSize.getHeight().toInt()
    var blockSize: Int = 0
    var level = Array(ySize / 10) { CharArray(xSize / 10) }
    var levelWidth: Int = 0
    var levelHeight: Int = 0

    init {
        when (levelNumber) {
            0 -> {
                initializeLevel(24, "Level0.txt")
                //adding enemies to the array
                levelMobs.add(MobVenus(100, 200))
                levelMobs.add(MobChuChu(50, 5))
            }
            1 -> initializeLevel(24, "Level1.txt")
            2 -> initializeLevel(24, "Level2.txt")
            3 -> initializeLevel(24, "Level3.txt")
            4 -> initializeLevel(24, "Level4.txt")
            5 -> initializeLevel(24, "Level5.txt")
            else -> println("Level error")
        }
    }

    private fun initializeLevel(blockSize: Int, levelString: String) {
        //levelSring is used to calculate URI
        //remaining ints are height and width respectively
        this.blockSize = blockSize
        populateLevel(levelString)
        addBlocksAndTiles(levelHeight, levelWidth)
    }

    fun populateLevel(string: String) {
        var line: String? = null
        var currentline = 0
        val input = ResourceLoader.getTextResource(string)
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

    fun addBlocksAndTiles(height: Int, width: Int) {
        val addBlock = { x: Int, y: Int, type: Block.Type ->
            levelTiles.add(Block(blockSize * x, ySize - blockSize * (y + 1), blockSize, blockSize, type))
        }
        val addTile = { x: Int, y: Int, image: String? ->
            levelTiles.add(Tile(blockSize * x, ySize - blockSize * (y + 1), image))
        }
        for (y in 0..height) {
            for (x in 0..width) {
                when (level[y][x]) {
                    '1' -> addBlock(x, y, PLAIN)
                    '2' -> addBlock(x, y, DEATH)
                    '3' -> addBlock(x, y, SLOW)
                    'P' -> addBlock(x, y, PLATFORM)
                    'h' -> addBlock(x, y, PORTAL)
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
