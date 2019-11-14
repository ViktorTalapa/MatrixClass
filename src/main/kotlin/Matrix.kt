import kotlin.random.Random

open class Matrix {

    protected val data: Array<DoubleArray>
    protected val m: Int
    protected val n: Int

    constructor(values: Array<DoubleArray>) {
        data = values
        m = values.size
        n = values[0].size
    }

    constructor(rows: Int, columns: Int, value: Double = 0.0) {
        require(rows >= 1) { "Row parameter must be at least 1." }
        require(columns >= 1) { "Column parameter must be at least 1." }
        m = rows
        n = columns
        data = Array(m) { DoubleArray(n) { value } }
    }

    constructor(A: Matrix) : this(A.data)

    /*
    constructor(values: DoubleArray, rows: Int) {
        TODO
    }
    */

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    operator fun set(i: Int, j: Int, x: Double) {
        data[i][j] = x
    }

    fun getColumn(columnIndex: Int): DoubleArray {
        val column = DoubleArray(m)
        for (i in 0 until m)
            column[i] = data[i][columnIndex]
        return column
    }

    fun getRow(rowIndex: Int): DoubleArray {
        return data[rowIndex]
    }

    fun swapColumns(columnIndex1: Int, columnIndex2: Int) {
        for (i in 0 until m) {
            val temp = data[i][columnIndex1]
            data[i][columnIndex1] = data[i][columnIndex2]
            data[i][columnIndex2] = temp
        }
    }

    fun swapRows(rowIndex1: Int, rowIndex2: Int) {
        val temp = data[rowIndex1]
        data[rowIndex1] = data[rowIndex2]
        data[rowIndex2] = temp
    }

    fun transpose(): Matrix {
        val A = Matrix(n, m)
        for (i in 0 until n)
            for (j in 0 until m)
                A.data[i][j] = data[j][i]
        return A
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (row in data)
            for (i in 0 until n) {
                result.append((row[i]))
                if (i < n - 1)
                    result.append(',')
                else
                    result.append(';')
            }
        return result.toString()
    }

    companion object {

        fun identity(order: Int): Matrix {
            val I = Matrix(order, order)
            for (i in 0 until order)
                I.data[i][i] = 1.0
            return I
        }

        fun generate(rows: Int, columns: Int, minValue: Double, maxValue: Double): Matrix {
            val A = Matrix(rows, columns)
            for (i in 0 until A.m)
                for (j in 0 until A.n)
                    A[i, j] = Random.nextDouble(minValue, maxValue)
            return A
        }

        fun generate(rows: Int, columns: Int, minValue: Int = 0, maxValue: Int = 9): Matrix {
            val A = Matrix(rows, columns)
            for (i in 0 until A.m)
                for (j in 0 until A.n)
                    A[i, j] = Random.nextInt(minValue, maxValue).toDouble()
            return A
        }
    }
}