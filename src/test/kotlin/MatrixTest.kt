import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixTest : MyTest() {

    private val A = Matrix(
        listOf(7.5, 8.3, 9.1, 8.0),
        listOf(4.1, 5.0, 6.1, 3.5),
        listOf(1.22, 2.56, 3.7, 9.2)
    )

    private val B = SquareMatrix(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    private val C = SquareMatrix(listOf(2, -1, 0, -1, 2, -1, 0, -1, 2), 3)

    @Test
    fun index() {
        assertEquals(8.0, B[2, 1], epsilon)
        assertEquals(MathVector(listOf(4, 5, 6)), B.row(1))
        assertEquals(MathVector(listOf(9.1, 6.1, 3.7)), A.column(2))
        B[0, 2] = 15
        A[1, 0] = B[0, 2]
        assertEquals(15.0, A[1, 0], epsilon)
    }

    @Test
    fun iterator() {
        val list = ArrayList<Double>()
        for (element in B)
            list.add(element)
        assertEquals(B.toList(), list)
    }

    @Test
    fun plus() {
        assertEquals(Matrix(listOf(3, 1, 3, 3, 7, 5, 7, 7, 11), 3), B + C)
        assertEquals(B + C, C + B)
    }

    @Test
    fun times() {
        assertEquals(Matrix(listOf(5, 10, 15, 20, 25, 30, 35, 40, 45), 3), B * 5)
        assertEquals(A * 2, 2.0 * A)
        assertEquals(
            Matrix(listOf(19.36, 25.98, 32.4, 42.6, 57.82, 73.56, 89.1, 104.7, 96.28, 121.14, 145.8, 166.8), 3),
            B * A
        )
    }

    @Test
    fun div() {
        assertEquals(A * 0.5, A / 2)
    }

    @Test
    fun unary() {
        assertEquals(A, +A)
        assertEquals(B * -1, -B)
        assertEquals(B - C, -C + B)
    }

    @Test
    fun subMatrix() {
        assertEquals(Matrix(listOf(1, 2, 4, 5), 2), B.subMatrix(0, 0, 1, 1))
        assertEquals(Matrix(listOf(7.5, 9.1), listOf(1.22, 3.7)), A.subMatrix(sortedSetOf(0, 2), sortedSetOf(2, 0)))
    }

    @Test
    fun swap() {
        A.swapRows(0, 2)
        A.swapColumns(1, 2)
        assertEquals(Matrix(listOf(1.22, 3.7, 2.56, 9.2, 4.1, 6.1, 5.0, 3.5, 7.5, 9.1, 8.3, 8.0), 3), A)
    }

    @Test
    fun transpose() {
        assertEquals(
            Matrix(
                listOf(7.5, 4.1, 1.22),
                listOf(8.3, 5.0, 2.56),
                listOf(9.1, 6.1, 3.7),
                listOf(8.0, 3.5, 9.2)
            ),
            A.transpose()
        )
        assertEquals(B, B.transpose().transpose())
    }

    @Test
    fun identity() {
        val I = Matrices.identity(5)
        val R = Matrix.random(5, 5)
        assertEquals(R, R * I)
        assertEquals(R, I * R)
        assertEquals(I, I.transpose())
    }

    @Test
    fun determinant() {
        assertEquals(0.0, B.det(), epsilon)
        assertEquals(4.0, C.det(), epsilon)
        assertEquals(-22.0, SquareMatrix(listOf(2, 4, 8, 5), 2).det(), epsilon)
        assertEquals(-2.5088, SquareMatrix(A.subMatrix(0..2, 0..2)).det(), epsilon)
        assertEquals(1.0, Matrices.identity(5).det(), epsilon)
    }

    @Test
    fun inverse() {
        assertEquals(SquareMatrix(listOf(0.75, 0.5, 0.25, 0.5, 1.0, 0.5, 0.25, 0.5, 0.75), 3), !C)
        assertEquals(Matrices.identity(C.height), C * !C)
    }

    @Test
    fun trace() {
        assertEquals(15.0, B.trace(), epsilon)
        assertEquals(C.trace(), C.transpose().trace(), epsilon)
        assertEquals((B * C).trace(), (C * B).trace(), epsilon)
    }

    @Test
    fun power() {
        assertEquals(Matrices.identity(B.height), B.pow(0))
        assertEquals(B, B.pow(1))
        assertEquals(B * B, B.pow(2))
        assertEquals(!C * !C * !C, C.pow(-3))
        assertEquals(C, C.pow(3) * C.pow(-2))
        assertEquals(C.pow(4), C.pow(2).pow(2))
    }

    @Test
    fun linearEquations() {
        assertEquals(
            Matrices.solveLinearEquations(
                SquareMatrix(listOf(2, 1, -1, -3, -1, 2, -2, 1, 2), 3),
                MathVector(8, -11, -3)
            )!!,
            MathVector(2, 3, -1)
        )
    }
}