import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random

class Pipe {
    val top = random(0.0, height/2.0)
    val bottom = random(0.0,height/2.0)
    var x = width + 0.0
    val w = 20.0
    val speed = 3
    var highlight = false

    fun show() {
        withDrawer {
            strokeWeight = 0.0
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