package mob

import weapons.Weapon
import java.util.ArrayList
import common.Mover

open class Mob(
        var name: String?,
        private val damage: Int,
        var health: Int,
        x: Int,
        y: Int
) : Mover() {
    private val jumpsound = this.javaClass.getResource("resources/sounds/Jump Sound 3 quieter.wav")
    var state = 0
    var jumpHeight = 7
    var weaponArray = ArrayList<Weapon>()
    var distanceToGun: Int = 0

    private val jumpLimit: Int = 3

    enum class mobstate {
        STANDING, WALKING_LEFT, WALKING_RIGHT, JUMPING
    }

    init {
        this.x = x
        this.y = y
    }

    fun jump() {
        if (jumpable < jumpLimit) {
            yspeed -= jumpHeight
            incrementJumpable()
        }
    }

    //  public void playJumpSound(){
    //	  try {
    //	      // Open an audio input stream.
    //	      AudioInputStream audioIn = AudioSystem.getAudioInputStream(jumpsound);
    //	      // Get a sound clip resource.
    //	      Clip clip = AudioSystem.getClip();
    //	      // Open audio clip and load samples from the audio input stream.
    //	      clip.open(audioIn);
    //	      clip.start();
    //
    //	   } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
    //	      e.printStackTrace();
    //	   }
    //  }

    fun getDistance(x: Int, y: Int, otherX: Int, otherY: Int): Int {
        val i = (otherX - x) * (otherX - x) + (otherY - y) * (otherY - y)
        return (-Math.sqrt(i.toDouble())).toInt()
    }

    /**
     * returns the coordinate of the projection onto a circle of radius r, centered at x, y.
     * returns the coordinate for the first number entered, so to get the y coordinate, input y first
     */
    fun getCircleProjection(x: Int, y: Int, otherX: Int, otherY: Int, r: Int): Int {
        val i = (otherX - x) * (otherX - x) + (otherY - y) * (otherY - y)
        return (x + r * ((x - otherX) / Math.sqrt(i.toDouble()))).toInt()
    }

    fun tetherMove() {
        move()
        if (tethered) {
            if (getDistance(x, y, weaponArray[0].hitX, weaponArray[0].hitY) < distanceToGun) {
                x = getCircleProjection(weaponArray[0].hitX, weaponArray[0].hitY, x, y, distanceToGun)
                oldX = x
                y = getCircleProjection(weaponArray[0].hitY, weaponArray[0].hitX, y, x, distanceToGun)
                oldY = y
            }
        }
    }

    open fun pattern() {
        move()
    }
}