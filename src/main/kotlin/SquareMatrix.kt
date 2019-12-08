class SquareMatrix(values: Collection<MathVector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must be in equal sizes." }
    }

    constructor(values: Array<MathVector>) : this(values.asList())

    constructor(matrix: Matrix) : this(Array(matrix.height) { i -> matrix.row(i) })

    constructor(values: Array<DoubleArray>) : this(Matrix(values))

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Matrix(values, rows))

    constructor(order: Int, value: Number = 0.0) : this(Matrix(order, order, value))

    operator fun plus(m: SquareMatrix) = SquareMatrix(super.plus(m))

    operator fun minus(m: SquareMatrix) = SquareMatrix(super.minus(m))

    operator fun times(m: SquareMatrix) = SquareMatrix(super.times(m))

    override operator fun times(scalar: Number) = SquareMatrix(super.times(scalar))

    override operator fun div(scalar: Number) = SquareMatrix(super.times(scalar))

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
        val sign = Matrices.formRowEchelon(copy)
        return sign * copy.diagonalValues().product()
    }

    fun trace() = diagonalValues().sum()
}
