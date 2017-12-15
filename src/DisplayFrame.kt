import javax.swing.JFrame

class DisplayFrame {
    private var j = JFrame()
    var g: GameLogic = GameLogic()

    init {
        j.apply {
            //		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isUndecorated = true
            add(g.jPanel)
            addMouseListener(MouseHandler())
            addMouseMotionListener(MouseMotionHandler())
            addKeyListener(KeyHandler(g))
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            pack()
            isVisible = true
        }
    }

    companion object {
        @JvmStatic
         var levelChanged = false
    }
}
