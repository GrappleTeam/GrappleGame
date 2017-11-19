import blocks.Block;
import blocks.IBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class DisplayPanel extends JPanel {
    ArrayList<Level> levelArray = GameLogic.levelArray;
    int currentLevel = GameLogic.currentLevel;
    BufferedImage[] BackgroundImages = GameLogic.BackgroundImages;
    BufferedImage[] blockImages = GameLogic.blockImages;
    BufferedImage[] tileImages = GameLogic.tileImages;
    Mob character =  GameLogic.character;

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        switch (GameLogic.current_gamestate) {
            case main_screen:
                drawMainScreen(g);
                break;
            case gameplay_screen:
                drawGameScreen(g);
                break;
        }
    }

    public void drawGameScreen(Graphics g) {
        drawingBackground(g);
        drawingTiles(g);
        drawingBlocks(g);
        drawingCharacter(g);
        drawingMobs(g);
        drawingGrapple(g);
        drawingAimingCircle(g);
        //g.drawImage(testanimation[cycle%testanimation.length], 40, 40, this);
    }

    private void drawingAimingCircle(Graphics g) {
        int charCenterX = character.getX() + (character.getWidth() / 2);
        int charCenterY = character.getY() + (character.getHeight() / 2);

        int r = -28;

        int vectorx = character.getCircleProjection(charCenterX, charCenterY,GameLogic. mouseX, GameLogic.mouseY, r);
        int vectory = character.getCircleProjection(charCenterY, charCenterX,GameLogic. mouseY, GameLogic.mouseX, r);

        //create a vector by b-a for points
        g.setColor(Color.RED);
        g.fillOval(vectorx - 5, vectory - 5, 10, 10);
    }

    private void drawingBackground(Graphics g) {
        if (levelArray.get(currentLevel).getBackground().getImage().equals("graphics/sword-and-sworcery.png")) {
            g.drawImage(BackgroundImages[0],
                    levelArray.get(currentLevel).getBackground().getX(),
                    levelArray.get(currentLevel).getBackground().getY(),
                    null);
        }
        if (levelArray.get(currentLevel).getBackground().getImage().equals("graphics/windows_xp_bliss-wide.jpg"))
            g.drawImage(BackgroundImages[1],
                    levelArray.get(currentLevel).getBackground().getX(),
                    levelArray.get(currentLevel).getBackground().getY(),
                    null);
        if (levelArray.get(currentLevel).getBackground().getImage().equals("graphics/whiteBackground.png"))
            g.drawImage(BackgroundImages[2],
                    levelArray.get(currentLevel).getBackground().getX(),
                    levelArray.get(currentLevel).getBackground().getY(),
                    null);
    }

    private void drawingMobs(Graphics g) {
        for (Mob m : levelArray.get(currentLevel).getLevelMobs())
            g.drawImage(m.getSprite(), m.getX(), m.getY(), this);
    }

    private void drawingCharacter(Graphics g) {
        if (character.getJumpable() == 0) {
            if (character.getXspeed() < 0) character.setState(1);
            if (character.getXspeed() == 0) character.setState(0);
            if (character.getXspeed() < 0) character.setState(2);
        } else character.setState(3);

        g.drawImage(character.getSprite(), character.getX(), character.getY(), this);
    }
    private void drawingBlocks(Graphics g) {
        for (IBlock b : levelArray.get(currentLevel).getLevelBlocks()) {
            if (b.getType() == Block.Type.PLAIN)
                g.drawImage(blockImages[0], b.getX(), b.getY(), this);
            if (b.getType()== Block.Type.DEATH)
                g.drawImage(blockImages[1], b.getX(), b.getY(), this);
            if (b.getType()== Block.Type.SLOW)
                g.drawImage(blockImages[2], b.getX(), b.getY(), this);
            if (b.getType()== Block.Type.PLATFORM)
                g.drawImage(blockImages[3], b.getX(), b.getY(), this);
            if (b.getType()== Block.Type.PORTAL)
                g.drawImage(blockImages[4], b.getX(), b.getY(), this);
        }
    }

    private void drawingTiles(Graphics g) {
        for (Tile t : levelArray.get(currentLevel).getLevelTiles()) {
            if (t.getImage().equals("door"))
                g.drawImage(tileImages[0], t.getX(), t.getY(), this);
            if (t.getImage().equals("grass"))
                g.drawImage(tileImages[1], t.getX(), t.getY(), this);
            if (t.getImage().equals("topright"))
                g.drawImage(tileImages[2], t.getX(), t.getY(), this);
            if (t.getImage().equals("topleft"))
                g.drawImage(tileImages[3], t.getX(), t.getY(), this);
            if (t.getImage().equals("horizontal"))
                g.drawImage(tileImages[4], t.getX(), t.getY(), this);
            if (t.getImage().equals("bottomright"))
                g.drawImage(tileImages[5], t.getX(), t.getY(), this);
            if (t.getImage().equals("bottomleft"))
                g.drawImage(tileImages[6], t.getX(), t.getY(), this);
            if (t.getImage().equals("bottomhorizontal"))
                g.drawImage(tileImages[7], t.getX(), t.getY(), this);
        }
    }

    public void drawMainScreen(Graphics g) {
        g.drawImage(BackgroundImages[0],
                levelArray.get(currentLevel).getBackground().getX(),
                levelArray.get(currentLevel).getBackground().getY(),
                null);
    }

    private void drawingGrapple(Graphics g) {
        if (character.getTethered()) {
            g.setColor(Color.black);
            g.drawLine(character.getX() + character.getWidth() / 2,
                    character.getY() + character.getHeight() / 2,
                    character.getWeaponArray().get(0).getHitX(),
                    character.getWeaponArray().get(0).getHitY());
        }
    }
}