import kotlin.math.abs
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
     * Transforms the matrix to reduced row echelon format using the Gauss-Jordan algorithm.
     * Can be used for determinant & inverse calculation.
     *
     * @param matrix:   The Matrix to be transformed to canonical form.
     * @param epsilon:  Precision of double value comparison to 0 (default value is 1e-10)
     *
     * @return          The determinant value of the NxN (sub)matrix.
     */
    fun reducedRowEchelonForm(matrix: Matrix, epsilon: Double = 1e-10): Double {
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
}