package neuralnet

import kotlin.math.exp

class NeuralNetwork(private val inputN: Int, private val hiddenN: Int, private val outputN: Int) {

    private var weightsIH = Matrix(hiddenN, inputN)
    private var weightsHO = Matrix(outputN, hiddenN)
    private var biasH = Matrix(hiddenN, 1)
    private var biasO = Matrix(outputN, 1)

    init {
        weightsHO.randomize()
        weightsIH.randomize()
        biasH.randomize()
        biasO.randomize()
    }

    fun feedforward(inputArr: FloatArray): FloatArray {
        val input = Matrix(inputArr)
        //hidden layer output
        var hidden = weightsIH dot input
        hidden += biasH

        //activation func
        hidden = hidden.map { sigmoid(it) }

        //ouput layer output
        var output = weightsHO dot hidden
        output += biasO
        output = output.map { sigmoid(it) }

        return output.flatten()
    }

    fun backprop(inp: FloatArray, targs: FloatArray) {

        val lr = 0.1f

        val inputs = Matrix(inp)
        //hidden layer output
        var hidden = weightsIH dot inputs
        hidden += biasH

        //activation func
        hidden = hidden.map { sigmoid(it) }

        //ouput layer output
        var outputs = weightsHO dot hidden
        outputs += biasO
        outputs = outputs.map { sigmoid(it) }

        //Calculate output layer error
        val targets = Matrix(targs)
        val errorsO = targets - outputs

        //Calculate output layer gradient
        var gradients = outputs.map { dsigmoid(it) }
        gradients *= errorsO
        gradients *= lr

        //Calculate deltas
        val deltaWeightsHO = gradients dot hidden.transposed

        //adjust weights & bias
        weightsHO += deltaWeightsHO
        biasO += gradients

        //hidden layer errors
        val errorsH = weightsHO.transposed dot errorsO

        //Calculate hidden layer gradient
        var hiddenGradient = hidden.map { dsigmoid(it) }
        hiddenGradient *= errorsH
        hiddenGradient *= lr

        //Calculate hidden layer deltas
        val deltaWeightsIH = hiddenGradient dot inputs.transposed

        //adjust weights & bias
        weightsIH += deltaWeightsIH
        biasH += hiddenGradient
    }
}

fun sigmoid(x: Float) = 1 / (1 + exp(-x))
fun dsigmoid(y: Float) = y * (1-y)
