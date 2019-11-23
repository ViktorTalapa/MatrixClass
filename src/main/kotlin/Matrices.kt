import kotlin.math.abs
import kotlin.math.min

object Matrices {

    fun diagonal(values: Collection<Number>): SquareMatrix {
        val result = SquareMatrix(values.size, 0)
        for (i in values.indices)
            result[i, i] = values.elementAt(i)
        return result
    }

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
                for (j in p until a.height)
                    a[i, j] -= a[p, j] * factor
            }
        }
        return sign
    }

    fun identity(order: Int): SquareMatrix {
        return diagonal(Array(order) { 1 }.asList())
    }

    fun random(
        rows: Int,
        columns: Int,
        minValue: Number = Double.MIN_VALUE,
        maxValue: Number = Double.MAX_VALUE,
        integers: Boolean = false
    ) = Matrix(Array(rows) { i -> Vector.generate(columns, minValue, maxValue, integers) })

    fun randomSquare(
        order: Int,
        minValue: Number = Double.MIN_VALUE,
        maxValue: Number = Double.MAX_VALUE,
        integers: Boolean = false
    ) = SquareMatrix(random(order, order, minValue, maxValue, integers))
}