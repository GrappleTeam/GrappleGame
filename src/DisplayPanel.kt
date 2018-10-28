import GameLogic.gamestate.*
import common.Block
import common.IBlock
import common.Tile
import javax.swing.*
import java.awt.*

internal class DisplayPanel : JPanel() {
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        when (GameLogic.current_gamestate) {
            main_screen -> drawMainScreen(g)
            gameplay_screen -> drawGameScreen(g)
            title_screen -> TODO()
        }
    }

    fun drawGameScreen(g: Graphics) {
        drawingBackground(g)
        drawingTiles(g)
        drawingBlocks(g)
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
            "sword-and-sworcery.png" -> g.drawImage(GameLogic.BackgroundImages[0], background.x, background.y, null)
            "windows_xp_bliss-wide.jpg" -> g.drawImage(GameLogic.BackgroundImages[1], background.x, background.y, null)
            "whiteBackground.png" -> g.drawImage(GameLogic.BackgroundImages[2], background.x, background.y, null)
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

    private fun drawingBlocks(g: Graphics) {
        fun draw(it: IBlock, index: Int) {
            g.drawImage(GameLogic.blockImages[index], it.x, it.y, this)
        }
        GameLogic.levelArray[GameLogic.currentLevel].levelBlocks.forEach {
            when (it.type) {
                Block.Type.PLAIN -> draw(it, 0)
                Block.Type.DEATH -> draw(it, 1)
                Block.Type.SLOW -> draw(it, 2)
                Block.Type.PLATFORM -> draw(it, 3)
                Block.Type.PORTAL -> draw(it, 4)
            }
        }
    }

    private fun drawingTiles(g: Graphics) {
        fun draw(it: Tile, index: Int) {
            g.drawImage(GameLogic.tileImages[index], it.x, it.y, this)
        }
        GameLogic.levelArray[GameLogic.currentLevel].levelTiles.forEach {
            when (it.image) {
                "door" -> draw(it, 0)
                "grass" -> draw(it, 1)
                "topright" -> draw(it, 2)
                "topleft" -> draw(it, 3)
                "horizontal" -> draw(it, 4)
                "bottomright" -> draw(it, 5)
                "bottomleft" -> draw(it, 6)
                "bottomhorizontal" -> draw(it, 7)
            }
        }
    }

    private fun drawMainScreen(g: Graphics) {
        val level = GameLogic.levelArray[GameLogic.currentLevel]
        g.drawImage(
                GameLogic.BackgroundImages[0],
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
}
