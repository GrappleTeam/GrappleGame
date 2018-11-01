import GameLogic.Gamestate.GAMEPLAY_SCREEN
import GameLogic.Gamestate.MAIN_SCREEN
import GameLogic.Gamestate.TITLE_SCREEN
import common.Block
import common.IBlock
import common.ITile
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class DisplayPanel : JPanel() {
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        when (GameLogic.current_gamestate) {
            MAIN_SCREEN -> drawMainScreen(g)
            GAMEPLAY_SCREEN -> drawGameScreen(g)
            TITLE_SCREEN -> TODO()
        }
    }

    private fun drawGameScreen(g: Graphics) {
        drawingBackground(g)
        drawingTiles(g)
        drawingCharacter(g)
        drawingMobs(g)
        drawingGrapple(g)
        drawingAimingCircle(g)
        //gameLogic.drawImage(testanimation[cycle%testanimation.length], 40, 40, this);
    }

    private fun drawingAimingCircle(g: Graphics) {
        val charCenterX = GameLogic.character.x + GameLogic.character.width / 2
        val charCenterY = GameLogic.character.y + GameLogic.character.height / 2
        val r = -28
        val vectorx = GameLogic.character.getCircleProjection(charCenterX, charCenterY, GameLogic.mouseX, GameLogic.mouseY, r)
        val vectory = GameLogic.character.getCircleProjection(charCenterY, charCenterX, GameLogic.mouseY, GameLogic.mouseX, r)
        //create a vector by b-a for points
        g.color = Color.RED
        g.fillOval(vectorx - 5, vectory - 5, 10, 10)
    }

    private fun drawingBackground(g: Graphics) {
        val background = GameLogic.levelArray[GameLogic.currentLevel].background
        when (background.image) {
            "sword-and-sworcery.png" -> g.drawImage(backgroundImages[0], background.x, background.y, null)
            "windows_xp_bliss-wide.jpg" -> g.drawImage(backgroundImages[1], background.x, background.y, null)
            "whiteBackground.png" -> g.drawImage(backgroundImages[2], background.x, background.y, null)
        }
    }

    private fun drawingMobs(g: Graphics) {
        GameLogic.levelArray[GameLogic.currentLevel].levelMobs.forEach {
            g.drawImage(it.sprite, it.x, it.y, this)
        }
    }

    private fun drawingCharacter(g: Graphics) {
        if (GameLogic.character.jumpable == 0) {
            when {
                GameLogic.character.xspeed < 0 -> GameLogic.character.state = 1
                GameLogic.character.xspeed == 0 -> GameLogic.character.state = 0
                GameLogic.character.xspeed < 0 -> GameLogic.character.state = 2
            }
        } else {
            GameLogic.character.state = 3
        }

        g.drawImage(GameLogic.character.sprite, GameLogic.character.x, GameLogic.character.y, this)
    }

    private fun drawingTiles(g: Graphics) {
        fun draw(tile: ITile, index: String) {
            g.drawImage(images[index]?.value, tile.x, tile.y, this)
        }
        GameLogic.levelArray[GameLogic.currentLevel].levelTiles.forEach {
            if (it is IBlock) {
                draw(it, it.type.toString())
            } else {
                draw(it, it.image!!)
            }
        }
    }

    private fun drawMainScreen(g: Graphics) {
        val level = GameLogic.levelArray[GameLogic.currentLevel]
        g.drawImage(
                backgroundImages[0],
                level.background.x,
                level.background.y,
                null
        )
    }

    private fun drawingGrapple(g: Graphics) {
        if (GameLogic.character.tethered) {
            g.color = Color.black
            g.drawLine(
                    GameLogic.character.x + GameLogic.character.width / 2,
                    GameLogic.character.y + GameLogic.character.height / 2,
                    GameLogic.character.weaponArray[0].hitX,
                    GameLogic.character.weaponArray[0].hitY
            )
        }
    }

    companion object {
        var images = mapOf(
                Block.Type.PLAIN.toString() to lazy { ResourceLoader.getImage(Block(0, 0, 0, 0, Block.Type.PLAIN).image!!) },
                Block.Type.DEATH.toString() to lazy { ResourceLoader.getImage(Block(0, 0, 0, 0, Block.Type.DEATH).image!!) },
                Block.Type.SLOW.toString() to lazy { ResourceLoader.getImage(Block(0, 0, 0, 0, Block.Type.SLOW).image!!) },
                Block.Type.PLATFORM.toString() to lazy { ResourceLoader.getImage(Block(0, 0, 0, 0, Block.Type.PLATFORM).image!!) },
                Block.Type.PORTAL.toString() to lazy { ResourceLoader.getImage(Block(0, 0, 0, 0, Block.Type.PORTAL).image!!) },
                "door" to lazy { ResourceLoader.getImage("Door01.png") },
                "grass" to lazy { ResourceLoader.getImage("GrassBlock01.png") },
                "topright" to lazy { ResourceLoader.getImage("CornerBlockTopRight.png") },
                "topleft" to lazy { ResourceLoader.getImage("CornerBlockTopLeft.png") },
                "horizontal" to lazy { ResourceLoader.getImage("Horizontal.png") },
                "bottomright" to lazy { ResourceLoader.getImage("CornerBlockBottomRight.png") },
                "bottomleft" to lazy { ResourceLoader.getImage("CornerBlockBottomLeft.png") },
                "bottomhorizontal" to lazy { ResourceLoader.getImage("BottomHorizontal.png") }
        )
        var backgroundImages = arrayOf(
                ResourceLoader.getImage("sword-and-sworcery.png"),
                ResourceLoader.getImage("windows_xp_bliss-wide.jpg"),
                ResourceLoader.getImage("whiteBackground.png")
        )
    }
}

