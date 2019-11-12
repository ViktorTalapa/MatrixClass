class SquareMatrix : Matrix {

    @Throws(IllegalArgumentException::class)
    constructor(order: Int, value: Double = 0.0) : super(order, order, value)
}