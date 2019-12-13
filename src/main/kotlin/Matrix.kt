/**
 * Matrix class constructed from an array of Vectors.
 * It also represents a row or column of a matrix.
 */
open class Matrix(private val data: Array<MathVector>) : Collection<Double> {

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

    constructor(values: Collection<MathVector>) : this(values.toTypedArray())

    constructor(values: Array<DoubleArray>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Array(rows) { i ->
        MathVector(values.subList(i * values.size / rows, (i + 1) * values.size / rows))
    })

    constructor(values: DoubleArray, rows: Int) : this(values.asList(), rows)

    operator fun get(rowIndex: Int, columnIndex: Int) = data[rowIndex][columnIndex]

    operator fun set(rowIndex: Int, columnIndex: Int, element: Number) {
        data[rowIndex][columnIndex] = element
    }

    fun column(columnIndex: Int) = MathVector(DoubleArray(height) { i -> this[i, columnIndex] })

    fun row(rowIndex: Int) = data[rowIndex]

    open operator fun plus(other: Matrix) = Matrix(Array(height) { i -> row(i) + other.row(i) })

    open operator fun times(scalar: Number) = Matrix(Array(height) { i -> row(i) * scalar })

    open operator fun times(other: Matrix): Matrix {
        require(width == other.height) { "Matrices have incompatible sizes for multiplication." }
        return Matrix(Array(height) { i -> DoubleArray(other.width) { j -> row(i) * other.column(j) } })
    }

    open operator fun unaryPlus() = this.times(1)

    open operator fun unaryMinus() = this.times(-1)

    open operator fun minus(other: Matrix) = this.plus(other.unaryMinus())

    open operator fun div(scalar: Number) = this.times(1.0 / scalar.toDouble())

    open fun copy() = Matrix(Array(height) { i -> data[i].copy() })

    open fun transpose() = Matrix(Array(width) { i -> DoubleArray(height) { j -> data[j][i] } })

    fun addRowToRow(addRowIndex: Int, toRowIndex: Int, times: Number = 1.0) {
        data[toRowIndex] = data[toRowIndex] + data[addRowIndex] * times
    }

    fun multiplyRow(rowIndex: Int, scalar: Number) {
        data[rowIndex] = data[rowIndex] * scalar
    }

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

    fun subMatrix(rowIndexes: Set<Int>, columnIndexes: Set<Int>) =
        Matrix(Array(rowIndexes.size) { i -> this.row(rowIndexes.elementAt(i)).subVector(columnIndexes) })

    fun subMatrix(rowIndexes: IntRange, columnIndexes: IntRange) =
        this.subMatrix(rowIndexes.toSortedSet(), columnIndexes.toSortedSet())

    fun subMatrix(fromRowIndex: Int, fromColumnIndex: Int, toRowIndex: Int, toColumnIndex: Int) =
        this.subMatrix(fromRowIndex..toRowIndex, fromColumnIndex..toColumnIndex)

    override fun toString(): String {
        val result = StringBuilder()
        for (row in data)
            result.append(row.toString()).append(System.lineSeparator())
        return result.toString()
    }

    fun toList(): List<Double> {
        val list = ArrayList<Double>()
        for (row in data)
            list.addAll(row)
        return list
    }

    override fun contains(element: Double): Boolean {
        for (row in data)
            if (row.contains(element))
                return true
        return false
    }

    override fun containsAll(elements: Collection<Double>): Boolean {
        for (element in elements)
            if (!this.contains(element))
                return false
        return true
    }

    override fun isEmpty(): Boolean = if (data.isEmpty()) true else data.first().isEmpty()

    private inner class MatrixIterator : Iterator<Double> {
        var posX: Int = 0
        var posY: Int = 0

        override fun hasNext(): Boolean = (posX < height - 1 || posY < width)

        override fun next(): Double {
            if (posY == width) {
                posX++
                posY = 0
            }
            if (posX == height)
                throw NoSuchElementException()
            return data[posX][posY++]
        }
    }

    override fun iterator(): Iterator<Double> = MatrixIterator()

    companion object {

        /**
         * Constructs a Matrix of the same value (default is 0) with a given size.
         */
        fun generate(rows: Int, columns: Int, value: Number = 0.0) =
            Matrix(Array(rows) { MathVector.generate(columns, value) })

        /**
         * Constructs a Matrix of random values with a given size.
         */
        fun random(rows: Int, columns: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
            Matrix(Array(rows) { MathVector.random(columns, minValue, maxValue) })

        /**
         * Constructs a Matrix of random Int values with a given size.
         */
        fun randomInts(rows: Int, columns: Int, minValue: Int = Int.MIN_VALUE, maxValue: Int = Int.MAX_VALUE) =
            Matrix(Array(rows) { MathVector.randomInts(columns, minValue, maxValue) })
    }
}