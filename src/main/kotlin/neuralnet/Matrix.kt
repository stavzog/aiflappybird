package neuralnet

import kotlin.random.Random

class Matrix(val rows: Int, val cols: Int) {
    var data: Array<FloatArray>

    val isEmpty get() = data.isEmpty()
    val transposed get() = transpose()

    init {
        data = Array(rows) {FloatArray(cols) {0f} }
    }
    constructor(array: FloatArray): this(array.size,1) {
        loopThrough { i, _ ->
            data[i][0] = array[i]
        }
    }

    operator fun plus(n: Float) = add(n)
    operator fun plus(m: Matrix) = add(m)
    operator fun minus(m: Matrix) = subtract(m)
    operator fun minus(n: Float) = subtract(n)
    operator fun times(n: Float) = multiply(n)
    operator fun times(m: Matrix) = multiply(m)

    fun print() {
        println("------")
        data.forEach {
            println(it.contentToString())
        }
        println("------")
    }

    fun flatten(): FloatArray {
        var arr = floatArrayOf()
        loopThrough {r, c ->
            arr += data[r][c]
        }
        return arr
    }

    fun loopThrough(action: (i: Int, j: Int) -> Unit) {
        data.forEachIndexed { row, it -> it.forEachIndexed { col, el ->
            action(row, col)
        }}
    }

    fun map(action: (Float) -> Float): Matrix {
        val result = Matrix(rows,cols)
        loopThrough {r,c ->
            result.data[r][c] = action(data[r][c])
        }
        return result
    }

    infix fun dot(m: Matrix): Matrix {
        if(cols != m.rows) return empty()
        val result = Matrix(rows, m.cols)
        val a = this
        val b = m
        for(i in 0 until result.rows) {
            for (j in 0 until result.cols) {
                result.data[i][j] = 0f
                for(k in 0 until a.cols) {
                    result.data[i][j] +=a.data[i][k] * b.data[k][j]
                }
            }
        }
        return result
    }

    private fun transpose(): Matrix {
        val result = Matrix(cols, rows)
        loopThrough { r, c ->
            result.data[c][r] = data[r][c]
        }
        return result
    }

    fun randomize() = loopThrough { r,c ->
        data[r][c] = Random.nextFloat() * 2 - 1
    }

    companion object {
        fun empty() = Matrix(0,0)
    }
}

//scalar func
infix fun Matrix.add(n: Float): Matrix {
    return map { it + n }
}

infix fun Matrix.subtract(n: Float): Matrix {
    return map { it - n }
}

infix fun Matrix.multiply(n: Float): Matrix {
    return map { it * n }
}

//element-wise func
infix fun Matrix.add(m: Matrix): Matrix {
    val result = Matrix(rows,cols)
    loopThrough { r, c ->
       result.data[r][c] = data[r][c] + m.data[r][c]
    }
    return result
}

infix fun Matrix.subtract(m: Matrix): Matrix {
    val result = Matrix(rows,cols)
    loopThrough { r, c ->
        result.data[r][c] = data[r][c] - m.data[r][c]
    }
    return result
}

infix fun Matrix.multiply(m: Matrix): Matrix {
    val result = Matrix(rows, cols)
    loopThrough { r, c ->
        result.data[r][c] = data[r][c] * m.data[r][c]
    }
    return result
}
