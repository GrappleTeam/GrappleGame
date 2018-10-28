import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

class MouseMotionHandler : MouseMotionListener {
    override fun mouseDragged(e: MouseEvent) {
    }

    override fun mouseMoved(e: MouseEvent) {
        GameLogic.mouseX = e.x
        GameLogic.mouseY = e.y
        e.consume()
    }
}
