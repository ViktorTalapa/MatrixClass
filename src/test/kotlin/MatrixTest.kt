import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixTest : MyTest() {

    private val A = Matrix(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )
    private val B = Matrix(
        listOf(7.5, 8.3, 9.1),
        listOf(4.1, 5.0, 6.1),
        listOf(1.22, 2.56, 3.7)
    )
    private val C = Matrix(listOf(2, 4, 6, 8, 10, 12, 14, 16, 18), 3)

    @Test
    fun get() {
        assertEquals(A[2, 1], 8.0, delta)
        assertEquals(A[2, 1], C[1, 0], delta)
        assertEquals(A.row(1), Vector(listOf(4, 5, 6)))
        assertEquals(B.column(2), Vector(listOf(9.1, 6.1, 3.7)))
    }

    @Test
    fun set() {
        A[0, 2] = 15
        B[1, 0] = A[2, 1]
        assertEquals(A[0, 2], 15.0, delta)
        assertEquals(B[1, 0], 8.0, delta)
    }

    @Test
    fun plus() {
        val D = Matrix(
            listOf(8.5, 10.3, 12.1),
            listOf(8.1, 10.0, 12.1),
            listOf(8.22, 10.56, 12.7)
        )
        assertEquals(A + B, D)
        assertEquals(A + C, C + A)
    }

    @Test
    fun times() {
        val D = Matrix(
            listOf(5, 10, 15),
            listOf(20, 25, 30),
            listOf(35, 40, 45)
        )
        val E = Matrix(
            listOf(19.36, 25.98, 32.4),
            listOf(57.82, 73.56, 89.1),
            listOf(96.28, 121.14, 145.8)
        )
        assertEquals(A * 5, D)
        assertEquals(B * 2, 2.0 * B)
        assertEquals(A * B, E)
    }

    @Test
    fun div() {
        assertEquals(A / 2, A * 0.5)
    }

    @Test
    fun unary() {
        assertEquals(+A, A)
        assertEquals(-B, B * -1)
        assertEquals(A - C, -C + A)
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
        assertEquals(A.subMatrix(0, 0, 1, 1), D)
        assertEquals(B.subMatrix(sortedSetOf(0, 2), sortedSetOf(0, 2)), E)
    }

    @Test
    fun transpose() {
        val tA = Matrix(
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(3, 6, 9)
        )
        assertEquals(A.transpose(), tA, delta)
        assertEquals(A.transpose().transpose(), A, delta)
    }

    @Test
    fun identity() {
        val I = Matrix.identity(5)
        val R = Matrix.generate(5, 5)
        assertEquals(R, R * I, delta)
        assertEquals(R * I, I * R, delta)
        assertEquals(I.transpose(), I, delta)
    }
}