object Matrices {

    fun generateDiagonal(values: Collection<Double>): SquareMatrix {
        val result = SquareMatrix(values.size)
        for (i in values.indices)
            result[i, i] = values.elementAt(i)
        return result
    }

    fun generateIdentity(order: Int): SquareMatrix {
        return generateDiagonal(DoubleArray(order) { 1.0 }.asList())
    }

    fun generateRandom(rows: Int, columns: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE): Matrix {
        val result = ArrayList<Vector>()
        for (i in 0 until rows)
            result.add(Vector.generateRandom(columns, minValue, maxValue))
        return Matrix(result)
    }
}