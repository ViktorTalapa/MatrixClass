class SquareMatrix(values: Collection<Vector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must be in equal sizes." }
    }

    constructor(values: Array<Vector>) : this(values.asList())

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> Vector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Array(rows) { i ->
        Vector(values.subList(i * values.size / rows, (i + 1) * values.size / rows))
    })

    constructor(order: Int, value: Number = 0.0) : this(Array(order) { Vector(order, value) })

    constructor(m: Matrix) : this(m.toList())

    operator fun plus(m: SquareMatrix) = SquareMatrix(super.plus(m))

    operator fun minus(m: SquareMatrix) = SquareMatrix(super.minus(m))

    operator fun times(m: SquareMatrix) = SquareMatrix(super.times(m))

    override operator fun times(s: Number) = SquareMatrix(super.times(s))

    override operator fun div(s: Number) = SquareMatrix(super.times(s))

    override operator fun unaryPlus() = this.times(1)

    override operator fun unaryMinus() = this.times(-1)

    fun diagonalValues() = Vector(Array(height) { i -> this[i, i] })

    fun determinant(): Double {
        if (height == 1)
            return this[0, 0]
        if (height == 2)
            return this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
        val copy = SquareMatrix(this.clone())
        val sign = Matrices.formRowEchelon(copy)
        return sign * copy.diagonalValues().product()
    }

    fun trace() = diagonalValues().sum()

    override fun transpose() = SquareMatrix(super.transpose())
}