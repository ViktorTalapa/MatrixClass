import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Matrices {

    /**
     * Constructs a diagonal Matrix from the given values.
     *
     * @param values:   The collection of values to be inserted into the main diagonal of the matrix
     *
     * @return          A diagonal matrix of the given values
     */
    fun diagonal(values: Collection<Number>): SquareMatrix {
        val result = SquareMatrix.generate(values.size)
        for (i in values.indices)
            result[i, i] = values.elementAt(i)
        return result
    }

    /**
     * Constructs an identity Matrix with a given size
     *
     * @param order:   The size of the matrix
     *
     * @return          An identity matrix of the given size
     */
    fun identity(order: Int) = diagonal(Array(order) { 1.0 }.asList())

    /**
     * Augmented matrix, obtained by appending the columns of two given matrices.
     * If the matrices don't have the same number of rows, the empty space will be filled with zeroes.
     *
     * @param matrix1:  Matrix to be put on the left side of the joined matrix
     * @param matrix2:  Matrix to be put on the right side of the joined matrix
     *
     * @return          The joined matrix
     */
    fun augmented(matrix1: Matrix, matrix2: Matrix): Matrix {
        val result = Matrix.generate(max(matrix1.height, matrix2.height), matrix1.width + matrix2.width)
        for (i in 0 until result.height) {
            for (j in 0 until matrix1.width)
                result[i, j] = matrix1[i, j]
            for (j in 0 until matrix2.width)
                result[i, matrix1.width + j] = matrix2[i, j]
        }
        return result
    }

    /**
     * Transforms the matrix to reduced row echelon format using the Gauss-Jordan algorithm.
     * Can be used for determinant & inverse calculation and solving linear equation systems.
     *
     * @param matrix:   The Matrix to be transformed to canonical form.
     * @param epsilon:  Precision of double value comparison to 0 (default value is 1e-10)
     *
     * @return          The determinant value of the NxN (sub)matrix.
     */
    fun formReducedRowEchelon(matrix: Matrix, epsilon: Double = 1e-10): Double {
        var det = 1.0
        for (pivot in 0 until min(matrix.height, matrix.width)) {
            var max = pivot
            for (i in pivot + 1 until matrix.height)
                if (abs(matrix[i, pivot]) > abs(matrix[max, pivot]))
                    max = i
            if (max != pivot) {
                matrix.swapRows(pivot, max)
                det = -det
            }
            if (abs(matrix[pivot, pivot]) <= epsilon)
                det = 0.0
            else {
                det *= matrix[pivot, pivot]
                matrix.multiplyRow(pivot, 1.0 / matrix[pivot, pivot])
                for (i in 0 until matrix.height)
                    if (i != pivot)
                        matrix.addRowToRow(pivot, i, -matrix[i, pivot])
            }
        }
        return det
    }

    /**
     * Solves linear equation system.
     *
     * @param coefficients:     The Square Matrix of coefficients
     * @param constants:        The Vector of constants on hte right side of the equations.
     *
     * @return                  The Vector of results if there is a definite solution, null otherwise.
     */
    fun solveLinearEquations(coefficients: SquareMatrix, constants: MathVector): MathVector? {
        val result = augmented(coefficients, Matrix(constants.toList(), constants.size))
        if (formReducedRowEchelon(result) == 0.0)
            return null
        return result.column(result.width - 1)
    }
}