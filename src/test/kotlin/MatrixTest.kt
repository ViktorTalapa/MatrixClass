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
    //private val C = Matrix(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 5)

    @Test
    fun get() {
        assertEquals(A[2, 1], 8.0, delta)
        assertEquals(A[1, 1], B[1, 1], delta)
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
        assertEquals(A + B, D, delta)
        assertEquals(D, B + A, delta)
    }

    @Test
    fun times() {


    }

    @Test
    fun div() {
    }

    @Test
    fun unary() {
    }

    @Test
    fun minus() {
    }

    @Test
    fun subMatrix() {
    }

    @Test
    fun swap() {
    }

    @Test
    fun swapColumns() {
    }

    @Test
    fun swapRows() {
    }

    @Test
    fun transpose() {
        val tA = Matrix(
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(3, 6, 9)
        )
        assertEquals(A.transpose(), tA, delta)
    }

    @Test
    fun identity() {
        val I = Matrix.identity(5)
        val R = Matrix.generate(5, 5)
        assertEquals(R, R * I, delta)
        assertEquals(I.transpose(), I, delta)
    }
}