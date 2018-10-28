import java.awt.image.BufferedImage
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

object ResourceLoader {
    private const val GRAPHICS_DIR = "./assets/graphics/"
    private const val SOUNDS_DIR = "./assets/sounds/"
    private const val LEVELS_DIR = "./assets/levels/"

    fun getImage(imageName: String): BufferedImage = ImageIO.read(getResource(GRAPHICS_DIR + imageName))
    fun getSoundUrl(soundName: String): URL = getResource(SOUNDS_DIR + soundName)
    fun getTextResource(fileName: String): Scanner = Scanner(ResourceLoader::class.java.getResourceAsStream(LEVELS_DIR + fileName))
    private fun getResource(resourceName: String): URL = ResourceLoader::class.java.getResource(resourceName)
}
