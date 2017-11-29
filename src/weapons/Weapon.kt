package weapons

import java.awt.image.BufferedImage


interface Weapon {
    var fireSpeed: Int
    var hitX: Int
    var hitY: Int
    var image: BufferedImage?

    fun fire()
    fun reload()
    fun incrementReloadCounter()
}
