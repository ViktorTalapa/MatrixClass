import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.min

open class Matrix(private val data: ArrayList<MathVector> = ArrayList()) : Collection<Double> {

    val height: Int
        get() = data.size

    val width: Int
        get() = if (data.isEmpty()) 0 else data.first().size

    override val size: Int
        get() = height * width

    init {
        for (row in data)
            require(width == row.size) { "Matrix rows must be in equal sizes." }
    }

    constructor(values: Collection<MathVector>) : this(ArrayList(values))

    constructor(values: Array<MathVector>) : this(values.asList())

    constructor(values: Array<DoubleArray>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Array(rows) { i ->
        MathVector(values.subList(i * values.size / rows, (i + 1) * values.size / rows))
    })

    constructor(values: DoubleArray, rows: Int) : this(values.asList(), rows)

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { MathVector(columns, value) })

    fun column(columnIndex: Int) = MathVector(DoubleArray(height) { i -> this[i, columnIndex] })

    fun row(rowIndex: Int): MathVector = data[rowIndex]

    operator fun get(rowIndex: Int, columnIndex: Int) = data[rowIndex][columnIndex]

    operator fun set(rowIndex: Int, columnIndex: Int, element: Number) {
        data[rowIndex][columnIndex] = element
    }

    open operator fun plus(other: Matrix) = Matrix(Array(height) { i -> row(i) + other.row(i) })

    open operator fun times(scalar: Number) = Matrix(Array(height) { i -> row(i) * scalar })

    open operator fun times(other: Matrix): Matrix {
        require(width == other.height) { "Matrices have incompatible sizes for multiplication." }
        return Matrix(Array(height) { i -> DoubleArray(other.width) { j -> row(i) * other.column(j) } })
    }

    open operator fun unaryPlus() = this.times(1)

    open operator fun unaryMinus() = this.times(-1)

    open operator fun minus(other: Matrix) = this.plus(other.unaryMinus())

    open operator fun div(scalar: Number) = this.times(1 / scalar.toDouble())

    open fun copy() = Matrix(Array(height) { i -> data[i].copy() })

    open fun transpose() = Matrix(Array(width) { i -> DoubleArray(height) { j -> data[j][i] } })

    fun subMatrix(rowIndexes: Set<Int>, columnIndexes: Set<Int>) =
        Matrix(Array(rowIndexes.size) { i -> this.row(rowIndexes.elementAt(i)).subVector(columnIndexes) })

    fun subMatrix(rowIndexes: IntRange, columnIndexes: IntRange) = subMatrix(rowIndexes.toSet(), columnIndexes.toSet())

    fun subMatrix(fromRowIndex: Int, fromColumnIndex: Int, toRowIndex: Int, toColumnIndex: Int) =
        subMatrix(fromRowIndex..toRowIndex, fromColumnIndex..toColumnIndex)


    fun swap(rowIndex1: Int, columnIndex1: Int, rowIndex2: Int, columnIndex2: Int) {
        val temp = this[rowIndex1, columnIndex1]
        this[rowIndex1, columnIndex1] = this[rowIndex2, columnIndex2]
        this[rowIndex2, columnIndex2] = temp
    }

    fun swapColumns(columnIndex1: Int, columnIndex2: Int) {
        for (i in 0 until height)
            this.swap(i, columnIndex1, i, columnIndex2)
    }

    fun swapRows(rowIndex1: Int, rowIndex2: Int) {
        for (j in 0 until width)
            this.swap(rowIndex1, j, rowIndex2, j)
    }

    fun toList(): List<Double> {
        val list = ArrayList<Double>()
        for (row in data)
            list.addAll(row)
        return list
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (row in data)
            result.append(row.toString()).append(System.lineSeparator())
        return result.toString()
    }

    override fun contains(element: Double): Boolean {
        for (row in data)
            if (row.contains(element))
                return true
        return false
    }

    override fun containsAll(elements: Collection<Double>) = this.toList().containsAll(elements)

    override fun isEmpty() = if (data.isEmpty()) true else data.first().isEmpty()

    override fun iterator(): Iterator<Double> = this.toList().iterator()

    companion object {

        /**
         * Transforms the given matrix to row echelon format.
         * @param a:        The input Matrix
         * @param epsilon:  Precision of double value comparison to 0 (default value is 1e-10)
         *
         * @return 0 if the matrix is singular (and therefore the determinant is 0) and 1 or -1 representing the change
         * of the sign of the determinant.
         */
        fun formRowEchelon(a: Matrix, epsilon: Double = 1e-10): Int {
            var sign = 1
            for (p in 0 until min(a.height, a.width)) {
                var max = p
                for (i in p + 1 until a.height)
                    if (abs(a[i, p]) > abs(a[max, p]))
                        max = i
                if (max != p) {
                    a.swapRows(p, max)
                    sign = -sign
                }
                if (abs(a[p, p]) <= epsilon)
                    return 0
                for (i in p + 1 until a.height) {
                    val factor = a[i, p] / a[p, p]
                    for (j in p until a.width)
                        a[i, j] -= a[p, j] * factor
                }
            }
            return sign
        }

        fun diagonal(values: Collection<Number>): Matrix {
            val result = Matrix(values.size, values.size, 0)
            for (i in values.indices)
                result[i, i] = values.elementAt(i)
            return result
        }

        fun identity(order: Int): Matrix {
            return diagonal(Array(order) { 1 }.asList())
        }

        fun random(rows: Int, columns: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
            Matrix(Array(rows) { i -> MathVector.random(columns, minValue, maxValue) })
    }
}