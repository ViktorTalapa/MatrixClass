import java.util.*
import kotlin.collections.ArrayList

open class Matrix(values: List<Vector>) {

    protected val data: ArrayList<Vector> = ArrayList(values)
    val height: Int = values.size
    val width: Int = values.first().length

    init {
        for (row in values)
            require(width == row.length) { "Matrix rows must be in equal sizes." }
    }

    constructor(values: Array<Vector>) : this(values.toList())

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { Vector(columns, value) })

    constructor(m: Matrix) : this(m.data)

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    operator fun set(i: Int, j: Int, x: Number) {
        data[i][j] = x.toDouble()
    }

    fun getColumn(columnIndex: Int): Vector {
        val column = Vector(height)
        for (i in 0 until height)
            column[i] = data[i][columnIndex]
        return column
    }

    fun getRow(rowIndex: Int): Vector {
        return data[rowIndex]
    }

    operator fun plus(m: Matrix): Matrix {
        require(height == m.height && width == m.width) { "Matrices have incompatible sizes for addition." }
        val result = Matrix(height, width)
        for (i in 0 until height)
            result.data[i] = data[i] + m.data[i]
        return result
    }

    operator fun minus(b: Matrix): Matrix {
        return this.plus(b.unaryMinus())
    }

    operator fun times(s: Number): Matrix {
        val result = Matrix(height, width)
        for (i in 0 until height)
            result.data[i] = data[i] * s.toDouble()
        return result
    }

    operator fun Double.times(m: Matrix): Matrix {
        return m.times(this)
    }

    operator fun times(m: Matrix): Matrix {
        require(width == m.height) { "Matrices have incompatible sizes for multiplication." }
        val result = Matrix(height, m.width)
        for (i in 0 until height)
            for (j in 0 until m.width)
                result.data[i][j] = this.getRow(i) * m.getColumn(j)
        return result
    }

    operator fun div(s: Number): Matrix {
        return this.times(1.0 / s.toDouble())
    }

    operator fun Number.div(m: Matrix): Matrix {
        return m.div(this)
    }

    operator fun unaryPlus(): Matrix {
        return this.times(1)
    }

    operator fun unaryMinus(): Matrix {
        return this.times(-1)
    }

    fun clone(): Matrix {
        val result = Matrix(height, width)
        for (i in 0 until height)
            result.data[i] = data[i].clone()
        return result
    }

    fun subMatrix(rows: SortedSet<Int>, columns: SortedSet<Int>): Matrix {
        require(rows.last() <= height && columns.last() <= width) {
            "Submatrix boundaries should be within the matrix."
        }
        val result = Matrix(rows.size, columns.size)
        for (i in 0 until rows.size)
            for (j in 0 until columns.size)
                result.data[i][j] = data[rows.elementAt(i)][columns.elementAt(j)]
        return result
    }

    fun swap(rowIndex1: Int, columnIndex1: Int, rowIndex2: Int, columnIndex2: Int) {
        val temp = this[rowIndex1, columnIndex1]
        this[rowIndex1, columnIndex1] = this[rowIndex2, columnIndex2]
        this[rowIndex2, columnIndex2] = temp
    }

    fun swapColumns(columnIndex1: Int, columnIndex2: Int) {
        for (i in 0 until height)
            swap(i, columnIndex1, i, columnIndex2)
    }

    fun swapRows(rowIndex1: Int, rowIndex2: Int) {
        for (i in 0 until width)
            swap(rowIndex1, i, rowIndex2, i)
    }

    fun transpose(): Matrix {
        val result = Matrix(width, height)
        for (i in 0 until width)
            for (j in 0 until height)
                result.data[i][j] = data[j][i]
        return result
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder()
        for (row in data)
            result.append(row.toString(trim)).append(System.lineSeparator())
        return result.toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    companion object {

        fun identity(order: Int): SquareMatrix {
            val result = SquareMatrix(order)
            for (i in 0 until order)
                result.data[i][i] = 1.0
            return result
        }

        fun generate(rows: Int, columns: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE): Matrix {
            val result = Matrix(rows, columns)
            for (i in 0 until rows)
                result.data[i] = Vector.generate(columns, minValue, maxValue)
            return result
        }
    }
}