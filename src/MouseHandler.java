import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pbeagan on 12/14/17.
 */
public class MouseHandler implements MouseListener {
    public void mousePressed(MouseEvent e) {
        GameLogic.mousePressed = true;
        GameLogic.mouseX = e.getX();
        GameLogic.mouseY = e.getY();
        GameLogic.character.getWeaponArray().get(0).setHitX(GameLogic.mouseX);
        GameLogic.character.getWeaponArray().get(0).setHitY(GameLogic.mouseY);
        GameLogic.character.setTethered(true);

        GameLogic.character.setDistanceToGun(GameLogic.character.getDistance(GameLogic.character.getX(), GameLogic.character.getY(), GameLogic.character.getWeaponArray().get(0).getHitX(), GameLogic.character.getWeaponArray().get(0).getHitY()));
        e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        GameLogic.mousePressed = false;
        GameLogic.character.setYspeed(0);
        GameLogic.character.setXacc(0);
        GameLogic.character.setXspeed(0);
        GameLogic.mouseX = e.getX();
        GameLogic.mouseY = e.getY();
        GameLogic.character.setTethered(false);
        e.consume();
    }

    public void mouseClicked(MouseEvent e) {
        GameLogic.mouseX = e.getX();
        GameLogic.mouseY = e.getY();
        e.consume();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
