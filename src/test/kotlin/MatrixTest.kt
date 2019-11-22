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

    private val C = SquareMatrix(listOf(2, 4, 6, 8, 10, 12, 14, 16, 18), 3)

    @Test
    fun get() {
        assertEquals(B[2, 1], 8.0, delta)
        assertEquals(B[2, 1], C[1, 0], delta)
        assertEquals(B.row(1), Vector(listOf(4, 5, 6)))
        assertEquals(A.column(2), Vector(listOf(9.1, 6.1, 3.7)))
    }

    @Test
    fun set() {
        B[0, 2] = 15
        A[1, 0] = B[2, 1]
        assertEquals(A[1, 0], 8.0, delta)
        assertEquals(B[0, 2], 15.0, delta)
    }

    @Test
    fun plus() {
        val D = Matrix(
            listOf(3, 6, 9),
            listOf(12, 15, 18),
            listOf(21, 24, 27)
        )
        assertEquals(B + C, D)
        assertEquals(B + C, C + B)
    }

    @Test
    fun times() {
        val D = Matrix(
            listOf(5, 10, 15),
            listOf(20, 25, 30),
            listOf(35, 40, 45)
        )
        val E = Matrix(
            listOf(19.36, 25.98, 32.4, 42.6),
            listOf(57.82, 73.56, 89.1, 104.7),
            listOf(96.28, 121.14, 145.8, 166.8)
        )
        assertEquals(B * 5, D)
        assertEquals(A * 2, 2.0 * A)
        assertEquals(B * A, E)
    }

    @Test
    fun div() {
        assertEquals(A / 2, A * 0.5)
    }

    @Test
    fun unary() {
        assertEquals(+A, A)
        assertEquals(-B, B * -1)
        assertEquals(B - C, -C + B)
    }

    @Test
    fun subMatrix() {
        val D = Matrix(
            listOf(1, 2),
            listOf(4, 5)
        )
        val E = Matrix(
            listOf(7.5, 9.1),
            listOf(1.22, 3.7)
        )
        assertEquals(B.subMatrix(0, 0, 1, 1), D)
        assertEquals(A.subMatrix(sortedSetOf(0, 2), sortedSetOf(2, 0)), E)
    }

    @Test
    fun transpose() {
        val tA = Matrix(
            listOf(7.5, 4.1, 1.22),
            listOf(8.3, 5.0, 2.56),
            listOf(9.1, 6.1, 3.7),
            listOf(8.0, 3.5, 9.2)
        )
        assertEquals(A.transpose(), tA)
        assertEquals(B.transpose().transpose(), B)
    }

    @Test
    fun identity() {
        val I = Matrix.identity(5)
        val R = Matrix.generate(5, 5)
        assertEquals(R, R * I)
        assertEquals(R * I, R)
        assertEquals(I.transpose(), I)
    }

    @Test
    fun trace() {
        assertEquals(B.trace(), 15.0, delta)
        assertEquals(C.trace(), C.transpose().trace(), delta)
        assertEquals((B * C).trace(), (C * B).trace(), delta)
    }

    @Test
    fun determinant() {
        val D = SquareMatrix(B.subMatrix(0..3, 0..3))
        assertEquals(D.determinant(), -2.5088, delta)
        assertEquals((B * D).determinant(), (D * B).determinant(), delta)
    }
}