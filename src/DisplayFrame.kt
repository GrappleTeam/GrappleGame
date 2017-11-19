import java.awt.Graphics

import javax.swing.JFrame
import javax.swing.JPanel

class DisplayFrame {
    internal var j = JFrame()
    internal var dbGraphics: Graphics? = null
     var g: GameLogic
    internal var d: JPanel? = null

    init {
        //		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
        j.isUndecorated = true

        g = GameLogic()
        j.add(g.jPanel)

        j.addMouseListener(g)
        j.addMouseMotionListener(g)
        j.addKeyListener(g)

        j.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        j.pack()

        j.isVisible = true
        //System.out.println(j.getWidth()+" "+j.getHeight());
    }

    companion object {
        @JvmStatic
         var levelChanged = false
    }
}
