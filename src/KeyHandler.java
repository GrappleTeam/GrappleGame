import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by pbeagan on 12/14/17.
 */
class KeyHandler implements KeyListener {
    private GameLogic gameLogic;

    public KeyHandler(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void keyPressed(KeyEvent k) {
        switch (k.getKeyCode()) {

            case KeyEvent.VK_W:        //-------------
            case KeyEvent.VK_UP:
                gameLogic.upButtonPressed = true;
                break;

            case KeyEvent.VK_A:        //-------------
            case KeyEvent.VK_LEFT:
                gameLogic.leftButtonPressed = true;
                break;

            case KeyEvent.VK_D:        //-------------
            case KeyEvent.VK_RIGHT:
                gameLogic.rightButtonPressed = true;
                break;

            case KeyEvent.VK_S:        //-------------
            case KeyEvent.VK_DOWN:
                gameLogic.downButtonPressed = true;
                break;

            case KeyEvent.VK_SPACE:
                GameLogic.character.setX(GameLogic.character.getWeaponArray().get(0).getHitX());
                GameLogic.character.setY(GameLogic.character.getWeaponArray().get(0).getHitY());
                GameLogic.character.setYspeed(0);
                break;

            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);

            case KeyEvent.VK_T:
                if (GameLogic.current_gamestate == GameLogic.gamestate.main_screen)
                    GameLogic.current_gamestate = GameLogic.gamestate.gameplay_screen;
                else if (GameLogic.current_gamestate == GameLogic.gamestate.gameplay_screen)
                    GameLogic.current_gamestate = GameLogic.gamestate.main_screen;
                break;

            case KeyEvent.VK_L:
                DisplayFrame.Companion.setLevelChanged(true);
                break;
//			case KeyEvent.VK_M:		if(musicOn){
//										musicOn = false;
//										if(clip.isRunning())
//											clip.stop();
//									}
//									else{
//										musicOn = true;
//										clip.start();
//									}
//									break;

            default:
                break;
        }
    }

    public void keyReleased(KeyEvent k) {
        switch (k.getKeyCode()) {

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                gameLogic.upButtonPressed = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                gameLogic.leftButtonPressed = false;
                GameLogic.character.setXacc(0);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                gameLogic.rightButtonPressed = false;
                GameLogic.character.setXacc(0);
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                gameLogic.downButtonPressed = false;
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent k) {
    }
}
