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

        val mat1 = Matrix(3,2)
        mat1.randomize()
        val mat2 = mat1.transpose()
        mat1.print()
        mat2.print()
        var mat3 = mat2 dot mat1
        mat3.print()
        mat3 += 1
        mat3.print()

        extend {
            if(frameCount % 100 == 0) {
                pipes.add(Pipe())
            }
            with(drawer) {
                clear(ColorRGBa.BLACK)
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

        keyboard.keyDown.listen {
            if(it.key == KEY_SPACEBAR) bird.up()
        }
    }
}

fun withDrawer(task: Drawer.() -> Unit) = drwr?.runCatching(task)

fun Pipe.offscreen(): Boolean {
    return x < -20
}
