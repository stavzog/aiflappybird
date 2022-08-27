package neuralnet

import kotlin.math.floor
import kotlin.random.Random

val trainingData = mapOf(
    doubleArrayOf(1.0,0.0) to doubleArrayOf(1.0),
    doubleArrayOf(1.0,1.0) to doubleArrayOf(0.0),
    doubleArrayOf(0.0,0.0) to doubleArrayOf(0.0),
    doubleArrayOf(0.0,1.0) to doubleArrayOf(1.0)
)

fun main() {

    var mat = Matrix(2,2).randomize().print()
    val mat2 = Matrix(2,2).randomize().print()

    mat += 3
    mat.print()

    var mat3 = mat * mat2
    mat3.print()

    testNN()
}

fun testNN() {
    val nn = NeuralNetwork(2,2,1)
    for (i in 1..50000) {
        val rand = trainingData.entries.elementAt(Random.nextInt(trainingData.size))
        nn.backprop(rand.key,rand.value)
    }

    println(nn.feedforward(doubleArrayOf(1.0,0.0)).contentToString())
    println(nn.feedforward(doubleArrayOf(0.0,0.0)).contentToString())
    println(nn.feedforward(doubleArrayOf(1.0,1.0)).contentToString())
    println(nn.feedforward(doubleArrayOf(0.0,1.0)).contentToString())
}