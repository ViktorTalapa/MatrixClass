class SquareMatrix(values: Collection<Vector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must be in equal sizes." }
    }

    constructor(values: Array<Vector>) : this(values.asList())

    constructor(vararg values: Collection<Number>) : this(Array(values.size) { i -> Vector(values[i]) })

    constructor(values: List<Number>, rows: Int) : this(Array(rows) { i ->
        Vector(values.subList(i * values.size / rows, (i + 1) * values.size / rows))
    })

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { Vector(columns, value) })

    constructor(m: Matrix) : this(m.toList())

    operator fun plus(m: SquareMatrix) = SquareMatrix(super.plus(m))

    operator fun minus(m: SquareMatrix) = SquareMatrix(super.minus(m))

    operator fun times(m: SquareMatrix) = SquareMatrix(super.times(m))

    override operator fun times(s: Number) = SquareMatrix(super.times(s))

    override operator fun div(s: Number) = SquareMatrix(super.times(s))

    override operator fun unaryPlus() = this.times(1)

    override operator fun unaryMinus() = this.times(-1)

    fun determinant(): Double {
        if (height == 1)
            return data[0][0]
        var result = 0.0
        for (i in 0 until height) {
            val sign = if (i % 2 == 0) 1 else -1
            //result += sign * data[0][i] * SquareMatrix(subMatrix()).determinant()
        }
        return result
    }

    fun trace(): Double {
        var result = 0.0
        for (i in 0 until height)
            result += data[i][i]
        return result
    }

    override fun transpose() = SquareMatrix(super.transpose())
}