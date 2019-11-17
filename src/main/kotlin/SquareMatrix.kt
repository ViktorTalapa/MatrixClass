open class SquareMatrix(values: List<Vector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must have equal sizes." }
    }

    constructor(values: Array<Vector>) : this (values.toList())

    constructor(order: Int, value: Number = 0.0) : this(Array(order) { Vector(order, value) })

}