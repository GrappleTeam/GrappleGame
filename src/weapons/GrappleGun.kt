package weapons

import java.awt.image.BufferedImage


class GrappleGun : Weapon {
    override var hitX: Int = 0
    override var hitY: Int = 0
    override var image: BufferedImage? = null
    override var fireSpeed: Int = 0
    val reloadSpeed: Int
    val clipSize: Int
    val gunImage: BufferedImage? = null

    var reloadCounter: Int = 0
    var firing: Boolean = false
    var reloading: Boolean = false

    init {
        reloadSpeed = 500
        clipSize = 1
        fireSpeed = 40

        firing = false
        reloading = false

        //		try {
        //			gunImage = ImageIO.read(DisplayPanel.class.getResource("/src/resources.Enemy1.png"));
        //		} catch (IOException e) {
        //			e.printStackTrace();
        //		}

    }

    override fun fire() {
        firing = true
    }

    override fun reload() {
        reloading = true
    }

    override fun incrementReloadCounter() {
        reloadCounter++
    }
}
