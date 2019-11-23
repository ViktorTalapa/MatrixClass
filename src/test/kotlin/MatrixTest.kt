import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixTest : MyTest() {

    private val A = Matrix(
        listOf(
            Vector(listOf(7.5, 8.3, 9.1, 8.0)),
            Vector(listOf(4.1, 5.0, 6.1, 3.5)),
            Vector(listOf(1.22, 2.56, 3.7, 9.2))
        )
    )

    private val B = SquareMatrix(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    private val C = SquareMatrix(listOf(2, 4, 6, 8, 10, 12, 14, 16, 18), 3)

    @Test
    fun index() {
        assertEquals(8.0, B[2, 1], epsilon)
        assertEquals(Vector(listOf(4, 5, 6)), B.row(1))
        assertEquals(Vector(listOf(9.1, 6.1, 3.7)), A.column(2))
        B[0, 2] = 15
        A[1, 0] = B[2, 1]
        assertEquals(A[1, 0], 8.0, epsilon)
        assertEquals(B[0, 2], 15.0, epsilon)
    }

    @Test
    fun plus() {
        val D = Matrix(
            listOf(3, 6, 9),
            listOf(12, 15, 18),
            listOf(21, 24, 27)
        )
        assertEquals(D, B + C)
        assertEquals(B + C, C + B)
    }

    @Test
    fun times() {
        val E = Matrix(listOf(5, 10, 15, 20, 25, 30, 35, 40, 45), 3)
        val F = Matrix(listOf(19.36, 25.98, 32.4, 42.6, 57.82, 73.56, 89.1, 104.7, 96.28, 121.14, 145.8, 166.8), 3)
        assertEquals(E, B * 5)
        assertEquals(A * 2, 2.0 * A)
        assertEquals(F, B * A)
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
        val G = Matrix(listOf(1, 2, 4, 5), 2)
        val H = Matrix(
            listOf(7.5, 9.1),
            listOf(1.22, 3.7)
        )
        assertEquals(G, B.subMatrix(0, 0, 1, 1))
        assertEquals(H, A.subMatrix(sortedSetOf(0, 2), sortedSetOf(2, 0)))
    }

    @Test
    fun transpose() {
        val T = Matrix(
            listOf(7.5, 4.1, 1.22),
            listOf(8.3, 5.0, 2.56),
            listOf(9.1, 6.1, 3.7),
            listOf(8.0, 3.5, 9.2)
        )
        assertEquals(T, A.transpose())
        assertEquals(B, B.transpose().transpose())
    }

    @Test
    fun identity() {
        val I = Matrices.identity(5)
        val R = Matrices.random(5, 5)
        assertEquals(R, R * I)
        assertEquals(R, I * R)
        assertEquals(I, I.transpose())
    }

    @Test
    fun determinant() {
        val K = SquareMatrix(listOf(2, 4, 8, 5), 2)
        assertEquals(-22.0, K.determinant(), epsilon)
        assertEquals(0.0, B.determinant(), epsilon)
        assertEquals(-2.5088, SquareMatrix(A.subMatrix(0..2, 0..2)).determinant(), epsilon)
        assertEquals(1.0, Matrices.identity(5).determinant(), epsilon)
    }

    @Test
    fun trace() {
        assertEquals(B.trace(), 15.0, epsilon)
        assertEquals(C.trace(), C.transpose().trace(), epsilon)
        assertEquals((B * C).trace(), (C * B).trace(), epsilon)
    }
}