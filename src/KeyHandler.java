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
                gameLogic.setUpButtonPressed(true);
                break;

            case KeyEvent.VK_A:        //-------------
            case KeyEvent.VK_LEFT:
                gameLogic.setLeftButtonPressed(true);
                break;

            case KeyEvent.VK_D:        //-------------
            case KeyEvent.VK_RIGHT:
                gameLogic.setRightButtonPressed(true);
                break;

            case KeyEvent.VK_S:        //-------------
            case KeyEvent.VK_DOWN:
                gameLogic.setDownButtonPressed(true);
                break;

            case KeyEvent.VK_SPACE:
                GameLogic.Companion.getCharacter().setX(GameLogic.Companion.getCharacter().getWeaponArray().get(0).getHitX());
                GameLogic.Companion.getCharacter().setY(GameLogic.Companion.getCharacter().getWeaponArray().get(0).getHitY());
                GameLogic.Companion.getCharacter().setYspeed(0);
                break;

            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);

            case KeyEvent.VK_T:
                if (GameLogic.Companion.getCurrent_gamestate() == GameLogic.Gamestate.MAIN_SCREEN)
                    GameLogic.Companion.setCurrent_gamestate(GameLogic.Gamestate.GAMEPLAY_SCREEN);
                else if (GameLogic.Companion.getCurrent_gamestate() == GameLogic.Gamestate.GAMEPLAY_SCREEN)
                    GameLogic.Companion.setCurrent_gamestate(GameLogic.Gamestate.MAIN_SCREEN);
                break;

            case KeyEvent.VK_L:
                Main.setLevelChanged(true);
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
                gameLogic.setUpButtonPressed(false);
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                gameLogic.setLeftButtonPressed(false);
                GameLogic.Companion.getCharacter().setXacc(0);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                gameLogic.setRightButtonPressed(false);
                GameLogic.Companion.getCharacter().setXacc(0);
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                gameLogic.setDownButtonPressed(false);
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent k) {
    }
}
