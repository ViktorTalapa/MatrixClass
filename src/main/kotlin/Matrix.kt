import kotlin.random.Random

data class Matrix(protected val data: Array<DoubleArray>) {

    protected val m: Int = data.size
    protected val n: Int = data.first().size

    constructor(rows: Int, columns: Int, value: Double = 0.0) : this(Array(rows) { DoubleArray(columns) { value } })

    constructor(matrix: Matrix) : this(matrix.data.copyOf())

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    operator fun set(i: Int, j: Int, x: Double) {
        data[i][j] = x
    }

    operator fun unaryPlus(): Matrix {
        return Matrix(this)
    }

    operator fun unaryMinus(): Matrix {
        val result = Matrix(m, n)
        for (i in 0 until m)
            for (j in 0 until n)
                result[i, j] = -data[i][j]
        return result
    }

    operator fun plus(b: Matrix): Matrix {
        val result = Matrix(
            if (m > b.m) m else b.m,
            if (n > b.n) n else b.n
        )
        for (i in 0 until m)
            for (j in 0 until n)
                result[i, j] = this[i, j]
        for (i in 0 until b.m)
            for (j in 0 until b.n)
                result[i, j] += b[i, j]
        return result
    }
    operator fun minus(b: Matrix): Matrix {
        return this.plus(b.unaryMinus())
    }

    operator fun times(s: Double): Matrix {
        val result = Matrix(data)
        for (i in 0 until m)
            for (j in 0 until n)
                result[i, j] *= s
        return result
    }

    operator fun Double.times(m: Matrix): Matrix {
        return m.times(this)
    }

    operator fun div(s: Double): Matrix {
        return this.times(1 / s)
    }

    operator fun Double.div(m: Matrix): Matrix {
        return m.div(this)
    }

    fun getColumn(columnIndex: Int): DoubleArray {
        val column = DoubleArray(m)
        for (i in 0 until m)
            column[i] = this[i, columnIndex]
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
        val trans = Matrix(n, m)
        for (i in 0 until n)
            for (j in 0 until m)
                trans[i, j] = this[j, i]
        return trans
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder()
        for (row in data)
            for (i in 0 until n) {
                if (trim)
                    result.append(row[i].toInt())
                else
                    result.append(row[i])
                if (i < n - 1)
                    result.append(' ')
                else
                    result.append(System.lineSeparator())
            }
        return result.toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!data.contentDeepEquals(other.data)) return false
        if (m != other.m) return false
        if (n != other.n) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentDeepHashCode()
        result = 31 * result + m
        result = 31 * result + n
        return result
    }

    companion object {

        fun identity(order: Int): Matrix {
            val id = Matrix(order, order)
            for (i in 0 until order)
                id.data[i][i] = 1.0
            return id
        }

        fun generate(rows: Int, columns: Int, minValue: Double, maxValue: Double): Matrix {
            val result = Matrix(rows, columns)
            for (i in 0 until rows)
                for (j in 0 until columns)
                    result[i, j] = Random.nextDouble(minValue, maxValue)
            return result
        }

        fun generate(rows: Int, columns: Int, minValue: Int = 0, maxValue: Int = 9): Matrix {
            val result = Matrix(rows, columns)
            for (i in 0 until rows)
                for (j in 0 until columns)
                    result[i, j] = Random.nextInt(minValue, maxValue).toDouble()
            return result
        }
    }
}