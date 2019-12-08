import kotlin.math.abs
import kotlin.math.min

object Matrices {

    /**
     * Transforms the given matrix to row echelon format.
     * @param a:        The input Matrix
     * @param epsilon:  Precision of double value comparison to 0 (default value is 1e-10)
     *
     * @return 0 if the matrix is singular (and therefore the determinant is 0) and 1 or -1 representing the change
     * of the sign of the determinant.
     */
    fun formRowEchelon(a: Matrix, epsilon: Double = 1e-10): Int {
        var sign = 1
        for (p in 0 until min(a.height, a.width)) {
            var max = p
            for (i in p + 1 until a.height)
                if (abs(a[i, p]) > abs(a[max, p]))
                    max = i
            if (max != p) {
                a.swapRows(p, max)
                sign = -sign
            }
            if (abs(a[p, p]) <= epsilon)
                return 0
            for (i in p + 1 until a.height) {
                val factor = a[i, p] / a[p, p]
                for (j in p until a.width)
                    a[i, j] -= a[p, j] * factor
            }
        }
        return sign
    }

    fun diagonal(values: Collection<Number>): SquareMatrix {
        val result = SquareMatrix(values.size, 0)
        for (i in values.indices)
            result[i, i] = values.elementAt(i)
        return result
    }

    fun identity(order: Int): SquareMatrix {
        return diagonal(Array(order) { 1 }.asList())
    }

    fun random(rows: Int, columns: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
        Matrix(Array(rows) { i -> MathVector.random(columns, minValue, maxValue) })

    fun randomSquare(order: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
        SquareMatrix(random(order, order, minValue, maxValue))
}