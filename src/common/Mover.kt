package common

import java.awt.image.BufferedImage
import java.util.*

open class Mover {
    var jumpable = 0
    var sprite: BufferedImage? = null
    val width: Int
        get() = sprite!!.width
    val height: Int
        get() = sprite!!.height
    protected var oldX: Int = 0
    protected var oldY: Int = 0
    var x: Int = 0
    var y: Int = 0
    var tethered = false
    var xlocked = false
    var ylocked = false
    var xspeed = 0
    var yspeed = 1
    var yacc = 2
    var xacc = 0
    private val topXSpeed = 15
    private val topYSpeed = 15
    private val gravity = yacc
    private val xfriction = 1
    private val yfriction = 0

    fun incrementJumpable() {
        jumpable += 1
        if (jumpable == 1) {
            //playJumpSound();
        }
    }

    fun move() {
        xspeed += xacc
        if (xspeed > topXSpeed) xspeed = topXSpeed
        if (xspeed < -topXSpeed) xspeed = -topXSpeed

        yspeed += yacc
        if (yspeed > topYSpeed) yspeed = topYSpeed

        if (!xlocked) {
            oldX = x
            x += xspeed
        }
        if (!ylocked) {
            oldY = y
            y += yspeed
        }
        //friction
        for (i in xfriction downTo 1) {
            if (xspeed > 0) xspeed -= 1
            if (xspeed < 0) xspeed += 1
        }
        yacc = gravity
    }

    fun checkWindowBoundaries(windowWidth: Int, windowHeight: Int) {
        if (x < 0) {
            x = 0
            xspeed = 0
        }
        if (x > windowWidth - width) {
            x = windowWidth - width
            xspeed = 0
        }

        if (y < 0) {
            y = 0
            yspeed = 0
        }
        if (y > windowHeight - height) {
            y = windowHeight - height
            yspeed = 0
            jumpable = 0
        }
    }

    fun bounceWindowBoundaries(windowWidth: Int, windowHeight: Int) {
        if (x < width / 2) {
            x = width / 2
            xspeed = Math.abs(xspeed)
        }
        if (x > windowWidth - width / 2) {
            x = windowWidth - width / 2
            xspeed = -Math.abs(xspeed)
        }
        if (y < height) {
            y = height
            yspeed = Math.abs(yspeed)
        }
        if (y > windowHeight - height) {
            y = windowHeight - height
            yspeed = -Math.abs(yspeed)
        }
    }

    fun checkBlockBoundaries(blocks: List<IBlock>) {
        for (b in blocks) {
            //only check the block if the character is inside it.
            if (b.withinBounds(x, y, width, height)) {
                if (b.type == Block.Type.PORTAL)
                    Main.levelChanged = true
                if (xspeed == 0) {
                    when (yspeedSign()) {
                        1 -> collisionNorth(b)
                        -1 -> collisionSouth(b)
                    }
                }
                when {
                    xspeed > 0 -> performCollisionHeadingRight(b)
                    xspeed < 0 -> performCollisionHeadingLeft(b)
                }
            }
        }
    }

    private fun performCollisionHeadingRight(b: IBlock) {
        when (yspeedSign()) {
            0 -> collisionEast(b)
            1 -> collisionSouthEast(b)
            -1 -> collisionNorthEast(b)
        }
    }

    private fun performCollisionHeadingLeft(b: IBlock) {
        when (yspeedSign()) {
            0 -> collisionWest(b)
            1 -> collisionSouthWest(b)
            -1 -> collisionNorthWest(b)
        }
    }

    private fun collisionSouthEast(b: IBlock) {
        when {
            this.withinXparam(b) -> collisionNorth(b)
            this.withinYparam(b) -> collisionEast(b)
            xspeed > yspeed -> collisionNorth(b)
            else -> collisionEast(b)
        }
    }

    private fun collisionNorthEast(b: IBlock) {
        when {
            this.withinXparam(b) -> collisionSouth(b)
            this.withinYparam(b) -> collisionEast(b)
            xspeed > yspeed -> collisionSouth(b)
            else -> collisionEast(b)
        }
    }

    private fun collisionSouthWest(b: IBlock) {
        when {
            this.withinXparam(b) -> collisionNorth(b)
            this.withinYparam(b) -> collisionWest(b)
            -xspeed > yspeed -> collisionNorth(b)
            else -> collisionWest(b)
        }
    }

    private fun collisionNorthWest(b: IBlock) {
        when {
            this.withinXparam(b) -> collisionSouth(b)
            this.withinYparam(b) -> collisionWest(b)
            -xspeed > yspeed -> collisionSouth(b)
            else -> collisionWest(b)
        }
    }

    private fun collisionEast(b: IBlock) {
        if (!b.isPlatform) {
            x = b.x - b.width
            xspeed = 0
            yacc = gravity - yfriction
        }
    }

    private fun collisionWest(b: IBlock) {
        if (!b.isPlatform) {
            x = b.x + b.width
            xspeed = 0
            yacc = gravity - yfriction
        }
    }

    private fun collisionSouth(b: IBlock) {
        if (!b.isPlatform) {
            y = b.y + b.height
            yspeed = 0
        }
    }

    private fun collisionNorth(b: IBlock) {
        if (b.type == Block.Type.SLOW) xspeed /= 3
        y = b.y - this.height
        yspeed = -yacc
        jumpable = 0
    }

    private fun withinXparam(b: IBlock): Boolean {
        val toLeftOfRightSide = b.x < oldX + this.width
        val toRightOfLeftSide = oldX < b.x + b.width
        return toLeftOfRightSide && toRightOfLeftSide
    }

    private fun withinYparam(b: IBlock): Boolean {
        val aboveBottomOfBlock = b.y < oldY + this.height
        val belowTopOfBlock = if (b.isPlatform) {
            oldY + this.height - yacc < b.y
        } else {
            oldY < b.y + b.height
        }
        return belowTopOfBlock && aboveBottomOfBlock
    }

    private fun yspeedSign(): Int {
        if (yspeed > 0) return 1
        return if (yspeed < 0) -1 else 0
    }
}
