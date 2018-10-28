import javax.swing.JFrame

object Main {
    private var j = JFrame()
    var gameLogic: GameLogic = GameLogic()

    @JvmStatic
    fun main(args: Array<String>) {
        val running = true
        j.apply {
            //		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isUndecorated = true
            add(gameLogic.jPanel)
            addMouseListener(MouseHandler())
            addMouseMotionListener(MouseMotionHandler())
            addKeyListener(KeyHandler(gameLogic))
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            pack()
            isVisible = true
        }
        Thread(gameLogic).start()
        while (running) {
            gameLogic.run()
        }
    }

    @JvmStatic
    var levelChanged = false
}
