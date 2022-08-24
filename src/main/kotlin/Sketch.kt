import org.openrndr.KEY_SPACEBAR
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer

val width = 800
val height = 600
var drwr: Drawer? = null

val bird = Bird()
val pipes = mutableListOf<Pipe>()
var over = false
var score = 0

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
                if(!over) {
                    bird.update()
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

        keyboard.keyDown.listen {
            if(it.key == KEY_SPACEBAR) bird.up()
        }
    }
}

fun withDrawer(task: Drawer.() -> Unit) = drwr?.runCatching(task)

fun Pipe.offscreen(): Boolean {
    return x < -20
}
