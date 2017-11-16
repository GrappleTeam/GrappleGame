import blocks.Block

import java.awt.image.BufferedImage
import java.util.ArrayList

open class Mover {
    var jumpable = 0

    //Images======================================================
     var sprite: BufferedImage? = null

    val width: Int
        get() = sprite!!.width

    val height: Int
        get() = sprite!!.height

    //Positioning=================================================
    protected var oldX: Int = 0
    protected var oldY: Int = 0
    //Speed and acceleration
    var x: Int = 0
    var y: Int = 0
    var tethered = false
    var xlocked = false
    var ylocked = false

    //Speed and acceleration
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

    //Boundary Checkers=============================================
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

     fun checkBlockBoundaries(blocks: ArrayList<Block>) {

        for (b in blocks) {
            //only check the block if the character is inside it.
            if (b.withinBounds(x, y, width, height)) {
                if (b.type == "portal")
                    Display_Frame.levelChanged = true
                if (xspeed == 0) {
                    when (yspeedSign()) {
                    //moving down
                        1 -> topCollision(b)
                    //moving up
                        -1 -> bottomCollision(b)
                    }
                }
                if (xspeed > 0) {
                    when (yspeedSign()) {
                    //moving east
                        0 -> leftCollision(b)
                    //moving southeast
                        1 -> {
                            if (this.withinXparam(b)) {
                                topCollision(b)

                            }
                            if (this.withinYparam(b)) {
                                leftCollision(b)

                            }
                            if (xspeed > yspeed)
                                topCollision(b)
                            else
                                leftCollision(b)
                        }
                    //moving northeast
                        -1 -> {
                            if (this.withinXparam(b)) {
                                bottomCollision(b)

                            }
                            if (this.withinYparam(b)) {
                                leftCollision(b)

                            }
                            leftCollision(b)
                        }
                    }
                }
                if (xspeed < 0) {
                    when (yspeedSign()) {
                    //moving west
                        0 -> rightCollision(b)
                    //moving southwest
                        1 -> {
                            if (this.withinXparam(b)) {
                                topCollision(b)

                            }
                            if (this.withinYparam(b)) {
                                rightCollision(b)

                            }
                            if (-xspeed > yspeed)
                                topCollision(b)
                            else
                                rightCollision(b)
                        }
                    //moving northwest
                        -1 -> {
                            if (this.withinXparam(b)) {
                                bottomCollision(b)

                            }
                            if (this.withinYparam(b)) {
                                rightCollision(b)

                            }
                            rightCollision(b)
                        }
                    }
                }
            }//end withinBounds
        }//end for
    }//end check block boundaries

    //Collision Basics==============================================
    private fun leftCollision(b: Block) {
        if (!b.isPlatform) {
            x = b.x - b.width
            xspeed = 0
            yacc = gravity - yfriction
        }
    }

    private fun rightCollision(b: Block) {
        if (!b.isPlatform) {
            x = b.x + b.width
            xspeed = 0
            yacc = gravity - yfriction
        }
    }

    private fun bottomCollision(b: Block) {
        if (!b.isPlatform) {
            y = b.y + b.height
            yspeed = 0
        }
    }

    private fun topCollision(b: Block) {
        if (b.type == "slowblock")
            xspeed = xspeed / 3
        y = b.y - this.height
        yspeed = -yacc
        jumpable = 0
    }

    //Util==========================================================
    private fun withinXparam(b: Block): Boolean {
        return b.x < oldX + this.width && oldX < b.x + b.width
    }

    private fun withinYparam(b: Block): Boolean {
        return if (b.isPlatform) oldY + this.height - yacc < b.y && b.y < oldY + this.height else b.y < oldY + this.height && oldY < b.y + b.height
    }

    private fun yspeedSign(): Int {
        if (yspeed > 0) return 1
        return if (yspeed < 0) -1 else 0
    }
}
