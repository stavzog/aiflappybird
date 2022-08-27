import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random
import kotlin.math.abs
import kotlin.random.Random

class Pipe {

    //empty space between
    val spacing = 125
    //center of empty space between
    val centery = Random.nextInt(spacing, height - spacing)

    //top and bottom of pipe
    val top = centery.toDouble() - spacing/2
    val bottom = height.toDouble() - (centery + spacing/2)
    //starts at edge
    var x = width.toDouble()
    //width of pipe
    val w = 80.0

    val speed = 6

    //highlight when hit
    var highlight = false

    fun show() {
        withDrawer {
            stroke = ColorRGBa.WHITE
            fill = ColorRGBa.WHITE
            if(highlight) fill = ColorRGBa.RED
            rectangle(x,0.0, w,top)
            rectangle(x,height - bottom,w,bottom)
        }
    }

    fun update() {
        x -= speed

        show()
    }

    fun hits(bird: Bird): Boolean {
        if(bird.y < top || bird.y > height - bottom) {
            if(bird.x > x && bird.x < x + w) return true
        }
        return false
    }
}

fun Pipe.offscreen(): Boolean {
    return x < -w
}

fun closestPipe(b: Bird): Pipe {
    var closest = pipes[0]
    var closestD = Double.MAX_VALUE

    for(i in 0 until pipes.size) {
        val d = pipes[i].x - b.x
        if(d < closestD && d > 0) {
            closest = pipes[i]
            closestD = d
        }
    }
    return closest
}