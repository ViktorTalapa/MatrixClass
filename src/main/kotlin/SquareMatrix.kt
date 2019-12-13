import kotlin.math.abs

class SquareMatrix(values: Array<MathVector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must be in equal sizes." }
    }

    constructor(matrix: Matrix) : this(Array(matrix.height) { i -> matrix.row(i) })

    constructor(values: Collection<MathVector>) : this(values.toTypedArray())

    constructor(values: Array<DoubleArray>) : this(Matrix(values))

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> MathVector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Matrix(values, rows))

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

    fun trace() = diagonalValues().sum()

    /**
     * Determinant
     */
    fun det(): Double {
        if (height == 1)
            return this[0, 0]
        if (height == 2)
            return this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
        return Matrices.reducedRowEchelonForm(this.copy())
    }

    /**
     * Inverse
     */
    operator fun not(): SquareMatrix {
        val aug = Matrix.generate(height, 2 * width)
        for (i in 0 until height)
            for (j in 0 until width) {
                aug[i, j] = this[i, j]
                if (i == j)
                    aug[i, width + j] = 1.0
            }
        require(Matrices.reducedRowEchelonForm(aug) != 0.0) { "Inverse does not exist for this matrix." }
        return SquareMatrix(aug.subMatrix(0 until height, width until 2 * width))
    }

    /**
     * Power
     */
    fun pow(p: Int): SquareMatrix {
        val base: SquareMatrix = when {
            p > 0 -> this
            p < 0 -> !this
            else -> Matrices.identity(height)
        }
        var result = base
        for (i in 2..abs(p))
            result *= base
        return result
    }

    companion object {

        /**
         * Constructs a SquareMatrix of the same value (default is 0) with a given size.
         */
        fun generate(order: Int, value: Number = 0.0) = SquareMatrix(generate(order, order, value))

        /**
         * Constructs a SquareMatrix of random values with a given size.
         */
        fun random(order: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
            SquareMatrix(random(order, order, minValue, maxValue))

        /**
         * Constructs a SquareMatrix of random Int values with a given size.
         */
        fun randomInts(order: Int, minValue: Int = Int.MIN_VALUE, maxValue: Int = Int.MAX_VALUE) =
            SquareMatrix(randomInts(order, order, minValue, maxValue))
    }
}
