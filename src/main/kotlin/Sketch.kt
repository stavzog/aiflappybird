import neuralnet.Matrix
import org.openrndr.KEY_SPACEBAR
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer

val width = 800
val height = 600
var drwr: Drawer? = null

val bird = Bird()
val pipes = mutableListOf<Pipe>()

fun main() = application {
    configure {
        width = 800
        height = 600
        title = "Flappy Bird"
        windowResizable = false
    }
    program {
        drwr = drawer
        drawer.strokeWeight = 0.0

        extend {
            if(frameCount % 100 == 0) {
                pipes.add(Pipe())
            }
            withDrawer {
                clear(ColorRGBa.BLACK)
                bird.update()
                bird.think()
                pipes.forEach {
                    it.update()
                    if (it.hits(bird)) {
                        it.highlight = true
                    }
                    if (it.offscreen()) pipes.remove(it)
                }
            }
        }
    }
}

fun withDrawer(task: Drawer.() -> Unit) = drwr?.runCatching(task)
