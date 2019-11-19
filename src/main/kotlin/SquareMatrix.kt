class SquareMatrix(values: Collection<Vector>) : Matrix(values) {

    init {
        require(height == width) { "Square Matrix rows and columns must have equal sizes." }
    }

    constructor(order: Int, value: Number = 0.0) : this(Array(order) { Vector(order, value) }.asList())


    /*
    fun determinant(): Double {
        if (height == 1)
            return this[0, 0]
        var sum = 0.0
        for (i in 0 until height) {
            val range = (0 until height).toSortedSet() - i
            sum += sign(i.toDouble()) * this[0, i] * SquareMatrix(subMatrix(range, range).toList()).determinant()
        }
        return sum
    }
    */
}