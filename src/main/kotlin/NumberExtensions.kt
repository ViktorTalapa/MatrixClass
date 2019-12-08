operator fun Number.times(vector: MathVector) = vector.times(this)

operator fun Number.times(matrix: Matrix) = matrix.times(this)

operator fun Number.times(matrix: SquareMatrix) = matrix.times(this)


