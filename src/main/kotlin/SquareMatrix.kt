class SquareMatrix(values: Array<MathVector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must be in equal sizes." }
    }

    constructor(matrix: Matrix) : this(Array(matrix.height) { i -> matrix.row(i) })

    constructor(values: Collection<MathVector>) : this(values.toTypedArray())

    constructor(values: Array<DoubleArray>) : this(Matrix(values))

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(values: List<Number>, rows: Int): this(Matrix(values, rows))

    constructor(values: DoubleArray, rows: Int) : this(Matrix(values, rows))

    operator fun plus(other: SquareMatrix) = SquareMatrix(super.plus(other))

    operator fun minus(other: SquareMatrix) = SquareMatrix(super.minus(other))

    operator fun times(other: SquareMatrix) = SquareMatrix(super.times(other))

    override operator fun times(scalar: Number) = SquareMatrix(super.times(scalar))

    override operator fun div(scalar: Number) = SquareMatrix(super.div(scalar))

    override operator fun unaryPlus() = this.times(1)

    override operator fun unaryMinus() = this.times(-1)

    override fun transpose() = SquareMatrix(super.transpose())

    override fun copy() = SquareMatrix(super.copy())

    fun diagonalValues() = MathVector(DoubleArray(height) { i -> this[i, i] })

    fun determinant(): Double {
        if (height == 1)
            return this[0, 0]
        if (height == 2)
            return this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
        val copy = this.copy()
        val sign = formRowEchelon(copy)
        return sign * copy.diagonalValues().product()
    }

    fun trace() = diagonalValues().sum()

    companion object {

        /**
         * Constructs a SquareMatrix of the same value (default is 0) with a given size.
         */
        fun generate(order: Int, value: Number = 0.0) = SquareMatrix(generate(order, order, value))

        fun diagonal(values: Collection<Number>) = SquareMatrix(Matrix.diagonal(values))

        fun identity(order: Int) = SquareMatrix(Matrix.identity(order))

        fun random(order: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
            SquareMatrix(random(order, order, minValue, maxValue))
    }
}
