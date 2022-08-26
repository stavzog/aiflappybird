package neuralnet

import kotlin.random.Random

class Matrix(val rows: Int, val cols: Int) {
    var data = Array(rows) {FloatArray(cols) {0f} }


    val isEmpty get() = data.isEmpty()
    val transposed get() = transpose()

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

    fun flatten(): FloatArray {
        var arr = floatArrayOf()
        loopThrough {r, c ->
            arr += data[r][c]
        }
        return arr
    }

    fun loopThrough(action: (i: Int, j: Int) -> Unit): Matrix {
        data.forEachIndexed { row, it -> it.forEachIndexed { col, _ ->
            action(row, col)
        }}
        return this
    }

    fun map(action: (Float) -> Float): Matrix {
        loopThrough {r,c ->
            data[r][c] = action(data[r][c])
        }
        return this
    }

    infix fun dot(m: Matrix): Matrix {
        if(cols != m.rows) return empty()
        val result = Matrix(rows,m.cols)
        result.loopThrough { i,j ->
            for(k in 0 until cols) result.data[i][j] += this@Matrix.data[i][k] * m.data[k][j]
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

    fun copy(): Matrix {
        val new = Matrix(rows,cols)
        loopThrough { r,c ->
            new.data[r][c] = data[r][c]
        }
        return new
    }

    companion object {
        fun empty() = Matrix(0,0)

        fun change(m: Matrix,action: (Float) -> Float): Matrix = m.copy().map(action)

    }
}

fun Matrix.print(): Matrix {
    println("------")
    data.forEach {
        println(it.contentToString())
    }
    println("------")
    return this
}

//scalar func
infix fun Matrix.add(n: Float): Matrix {
    return Matrix.change(this) { it + n }
}

infix fun Matrix.subtract(n: Float): Matrix {
    return Matrix.change(this) { it - n }
}

infix fun Matrix.multiply(n: Float): Matrix {
    return Matrix.change(this) { it * n }
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
