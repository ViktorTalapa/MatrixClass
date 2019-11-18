import java.util.*
import kotlin.collections.ArrayList

open class Matrix(values: Collection<Vector>) {

    protected val data: ArrayList<Vector> = ArrayList(values)
    val height: Int = values.size
    val width: Int = values.first().length

    init {
        for (row in values)
            require(width == row.length) { "Matrix rows must be in equal sizes." }
    }

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { Vector(columns, value) }.asList())

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

    operator fun minus(m: Matrix): Matrix {
        return this.plus(m.unaryMinus())
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

    fun subMatrix(rowIndexes: Collection<Int>, columnIndexes: Collection<Int>): Matrix {
        val rIndexes = rowIndexes.toSortedSet(Comparator.naturalOrder())
        val cIndexes = columnIndexes.toSortedSet(Comparator.naturalOrder())
        require(rIndexes.first() >= 0 && cIndexes.first() >= 0 && rIndexes.last() < height && cIndexes.last() < width) {
            "Submatrix boundaries should be within the matrix."
        }
        val result = Matrix(rIndexes.size, cIndexes.size)
        for (i in rIndexes.indices)
            for (j in cIndexes.indices)
                result.data[i][j] = data[rIndexes.elementAt(i)][cIndexes.elementAt(j)]
        return result
    }

    fun subMatrix(rowIndexes: IntRange, columnIndexes: IntRange): Matrix {
        return subMatrix(rowIndexes.toSet(), columnIndexes.toSet())
    }

    fun subMatrix(rowIndex1: Int, columnIndex1: Int, rowIndex2: Int, columnIndex2: Int): Matrix {
        return subMatrix(IntRange(rowIndex1, rowIndex2), IntRange(columnIndex1, columnIndex2))
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
}