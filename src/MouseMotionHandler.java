import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by pbeagan on 12/14/17.
 */
public class MouseMotionHandler implements MouseMotionListener {
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        GameLogic.mouseX = e.getX();
        GameLogic.mouseY = e.getY();
        e.consume();
    }
}
