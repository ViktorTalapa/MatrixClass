open class Matrix {

    protected val data: Array<DoubleArray>
    protected val m: Int
    protected val n: Int

    @Throws(IllegalArgumentException::class)
    constructor(rows: Int, columns: Int, value: Double = 0.0) {
        if (rows < 1)
            throw IllegalArgumentException("Row parameter must be at least 1.")
        if (columns < 1)
            throw IllegalArgumentException("Column parameter must be at least 1.")
        m = rows
        n = columns
        data = Array(m) { DoubleArray(n) }
        for (i in 0 until m)
            for (j in 0 until n)
                data[i][j] = value
    }

    @Throws(IndexOutOfBoundsException::class)
    constructor(values: Array<DoubleArray>) : this(values.size, values[0].size) {
        for (i in 0 until m)
            for (j in 0 until n)
                data[i][j] = values[i][j]
    }

    constructor(A: Matrix) : this(A.data)

    /*
    @Throws(IllegalArgumentException::class)
    constructor(values: DoubleArray, rows: Int) {
        TODO
    }
    */

    fun getColumn(columnIndex: Int): DoubleArray {
        val column = DoubleArray(m)
        for (i in 0 until m)
            column[i] = data[i][columnIndex]
        return column
    }

    fun getRow(rowIndex: Int) : DoubleArray {
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

    companion object {

        fun identity(order: Int): SquareMatrix {
            val I = SquareMatrix(order)
            for (i in 0 until order)
                I.data[i][i] = 1.0
            return I
        }

        fun generate(rows: Int, columns: Int, minValue: Double = 0.0, maxValue: Double = 1.0): Matrix {
            val A = Matrix(rows, columns)
            for (i in 0 until A.m)
                for (j in 0 until A.n)
                    A.data[i][j] = minValue + Math.random() * (maxValue - minValue + 1)
            return A
        }
    }
}