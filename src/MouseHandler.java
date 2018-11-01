import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pbeagan on 12/14/17.
 */
public class MouseHandler implements MouseListener {
    public void mousePressed(MouseEvent e) {
        GameLogic.Companion.setMousePressed(true);
        GameLogic.Companion.setMouseX(e.getX());
        GameLogic.Companion.setMouseY(e.getY());
        GameLogic.Companion.getCharacter().getWeaponArray().get(0).setHitX(GameLogic.Companion.getMouseX());
        GameLogic.Companion.getCharacter().getWeaponArray().get(0).setHitY(GameLogic.Companion.getMouseY());
        GameLogic.Companion.getCharacter().setTethered(true);

        GameLogic.Companion.getCharacter().setDistanceToGun(GameLogic.Companion.getCharacter().getDistance(GameLogic.Companion.getCharacter().getX(), GameLogic.Companion.getCharacter().getY(), GameLogic.Companion.getCharacter().getWeaponArray().get(0).getHitX(), GameLogic.Companion.getCharacter().getWeaponArray().get(0).getHitY()));
        e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        GameLogic.Companion.setMousePressed(false);
        GameLogic.Companion.getCharacter().setYspeed(0);
        GameLogic.Companion.getCharacter().setXacc(0);
        GameLogic.Companion.getCharacter().setXspeed(0);
        GameLogic.Companion.setMouseX(e.getX());
        GameLogic.Companion.setMouseY(e.getY());
        GameLogic.Companion.getCharacter().setTethered(false);
        e.consume();
    }

    public void mouseClicked(MouseEvent e) {
        GameLogic.Companion.setMouseX(e.getX());
        GameLogic.Companion.setMouseY(e.getY());
        e.consume();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
