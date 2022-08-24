package neuralnet

import kotlin.random.Random

class Matrix(val rows: Int, val cols: Int) {
    var data: Array<IntArray>

    init {
        data = Array(rows) {IntArray(cols) {0} }
    }

    operator fun plus(n: Int): Matrix = add(n)
    operator fun plus(m: Matrix) = add(m)
    operator fun times(n: Int) = multiply(n)
    operator fun times(m: Matrix) = multiply(m)

    fun print() {
        println("------")
        data.forEach {
            println(it.contentToString())
        }
        println("------")
    }

    fun loopThrough(action: (i: Int, j: Int) -> Unit) {
        data.forEachIndexed { row, it -> it.forEachIndexed { col, el ->
            action(row, col)
        }}
    }

    infix fun dot(m: Matrix): Matrix {
        if(cols != m.rows) return Matrix(0,0)
        val result = Matrix(rows, m.cols)
        val a = this
        val b = m
        for(i in 0 until result.rows) {
            for (j in 0 until result.cols) {
                result.data[i][j] = 0
                for(k in 0 until a.cols) {
                    result.data[i][j] +=a.data[i][k] * b.data[k][j]
                }
            }
        }
        return result
    }

    fun transpose(): Matrix {
        val result = Matrix(cols, rows)
        loopThrough { r, c ->
            result.data[c][r] = data[r][c]
        }
        return result
    }

    fun randomize() = loopThrough { r,c ->
        data[r][c] = Random.nextInt(0,10)
    }
}

//scalar func
infix fun Matrix.add(n: Int): Matrix {
    loopThrough { row, col ->
        data[row][col] += n
    }
    return this
}

infix fun Matrix.multiply(n: Int): Matrix {
    loopThrough { row, col ->
        data[row][col] *= n
    }
    return this
}

//element-wise func
infix fun Matrix.add(m: Matrix): Matrix {
    loopThrough { r, c ->
       data[r][c] += m.data[r][c]
    }
    return this
}

infix fun Matrix.multiply(m: Matrix): Matrix {
    loopThrough { r, c ->
        data[r][c] *= m.data[r][c]
    }
    return this
}
