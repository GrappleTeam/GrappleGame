import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by pbeagan on 11/6/17.
 */
public class ImageUtils {
    public static BufferedImage getImage(String imageUrl){
        try {
            return ImageIO.read(GameLogic.class.getResource(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
