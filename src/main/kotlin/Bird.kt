import neuralnet.NeuralNetwork
import org.openrndr.color.ColorRGBa

class Bird {

    val brain = NeuralNetwork(4,4,1)

    var y = height / 2.0
    var x = 64.0

    val gravity = 0.6
    val lift = -15.0
    var velocity = 0.0

    fun show() {
        withDrawer {
            fill = ColorRGBa.WHITE
            circle(x,y,32.0)
        }
    }

    fun update() {
        velocity += gravity
        velocity *= 0.9
        y+=velocity

        if(y >= height - 5) {
            y = height - 5.0
            velocity = 0.0
        }
        if(y < 0) {
            y = 0.0
            velocity = 0.0
        }
        show()
    }

    fun up() {
        velocity += lift
    }
}

fun Bird.think(pipes: MutableList<Pipe>) {
    val inputs = doubleArrayOf(
        y / height,
        pipes[0].top / height,
        pipes[0].bottom / height,
        pipes[0].x / width
    )

    val output = brain.feedforward(inputs)
    if (output[0] > 0.5) up()
}