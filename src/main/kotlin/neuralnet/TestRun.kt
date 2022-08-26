package neuralnet

import kotlin.random.Random

val trainingData = mapOf<FloatArray,FloatArray>(
    floatArrayOf(1f,0f) to floatArrayOf(1f),
    floatArrayOf(1f,1f) to floatArrayOf(0f),
    floatArrayOf(0f,0f) to floatArrayOf(0f),
    floatArrayOf(0f,1f) to floatArrayOf(1f)
)

fun main() {

    val nn = NeuralNetwork(2,2,1)
    for (i in 1..50000) {
        val rand = trainingData.entries.elementAt(Random.nextInt(trainingData.size))
        nn.backprop(rand.key,rand.value)
    }

    println(nn.feedforward(floatArrayOf(1f,0f)).contentToString())
    println(nn.feedforward(floatArrayOf(0f,0f)).contentToString())
    println(nn.feedforward(floatArrayOf(1f,1f)).contentToString())
    println(nn.feedforward(floatArrayOf(0f,1f)).contentToString())

}