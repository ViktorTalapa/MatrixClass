import java.util.*
import kotlin.collections.ArrayList

open class Matrix(values: Collection<Vector>) {

    protected val data = ArrayList(values)

    val height: Int
        get() = data.size

    val width: Int
        get() = if (data.isEmpty()) 0 else data.first().size

    init {
        for (row in data)
            require(width == row.size) { "Matrix rows must be in equal sizes." }
    }

    constructor(values: Array<Vector>) : this(values.asList())

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> Vector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Array(rows) { i ->
        Vector(values.subList(i * values.size / rows, (i + 1) * values.size / rows))
    })

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { Vector(columns, value) })

    constructor(m: Matrix) : this(m.data)

    operator fun get(i: Int, j: Int) = data[i][j]

    operator fun set(i: Int, j: Int, x: Number) {
        data[i][j] = x
    }

    fun column(columnIndex: Int): Vector {
        val column = ArrayList<Double>()
        for (row in data)
            column.add(row[columnIndex])
        return Vector(column)
    }

    fun row(rowIndex: Int) = data[rowIndex]

    operator fun plus(m: Matrix): Matrix {
        require(height == m.height && width == m.width) { "Matrices have incompatible sizes for addition." }
        return Matrix(Array(height) { i -> data[i] + m.data[i] })
    }

    operator fun times(m: Matrix): Matrix {
        require(width == m.height) { "Matrices have incompatible sizes for multiplication." }
        return Matrix(Array(height) { i -> Vector(Array(m.width) { j -> row(i) * m.column(j) }) })
    }

    open operator fun times(s: Number) = Matrix(Array(height) { i -> data[i] * s.toDouble() })

    open operator fun div(s: Number) = this.times(1 / s.toDouble())

    open operator fun unaryPlus() = this.times(1)

    open operator fun unaryMinus() = this.times(-1)

    operator fun minus(m: Matrix) = this.plus(m.unaryMinus())

    fun subMatrix(rowIndexes: SortedSet<Int>, columnIndexes: SortedSet<Int>): Matrix {
        require(
            rowIndexes.first() in 0..rowIndexes.last() && rowIndexes.last() < height &&
                    columnIndexes.first() in 0..columnIndexes.last() && columnIndexes.last() < width
        ) { "Submatrix boundaries should be within the matrix." }
        return Matrix(Array(rowIndexes.size) { i -> data[rowIndexes.elementAt(i)].subVector(columnIndexes) })
    }

    fun subMatrix(rowIndexes: IntRange, columnIndexes: IntRange) =
        subMatrix(rowIndexes.toSortedSet(), columnIndexes.toSortedSet())

    fun subMatrix(rowIndex1: Int, columnIndex1: Int, rowIndex2: Int, columnIndex2: Int) =
        subMatrix(rowIndex1..rowIndex2, columnIndex1..columnIndex2)


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

    fun toList(): List<Vector> = data.toList()

    open fun transpose() = Matrix(Array(width) { i -> Vector(Array(height) { j -> data[j][i] }) })

    override fun equals(other: Any?): Boolean {
        if (other is Matrix) {
            if (height != other.height || width != other.width)
                return false
            for (i in 0 until height)
                if (!data[i].equals(other.data[i]))
                    return false
            return true
        }
        return false
    }

    override fun hashCode() = data.hashCode()

    override fun toString(): String {
        val result = StringBuilder()
        for (row in data)
            result.append(row.toString()).append(System.lineSeparator())
        return result.toString()
    }

    companion object {

        fun diagonal(values: Collection<Number>): Matrix {
            val result = Matrix(values.size, values.size, 0)
            for (i in values.indices)
                result[i, i] = values.elementAt(i).toDouble()
            return result
        }

        fun identity(order: Int): Matrix {
            return diagonal(Array(order) { 1 }.asList())
        }

        fun generate(
            rows: Int,
            columns: Int,
            minValue: Number = Double.MIN_VALUE,
            maxValue: Number = Double.MAX_VALUE,
            integers: Boolean = false
        ): Matrix {
            val result = ArrayList<Vector>()
            for (i in 0 until rows)
                result.add(Vector.generate(columns, minValue, maxValue, integers))
            return Matrix(result)
        }
    }
}