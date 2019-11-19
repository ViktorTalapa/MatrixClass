import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class VectorTest {

    private val u : Vector = Vector(arrayOf(1, 2, 3, 4, 5).asList())
    private val v: Vector = Vector(listOf(5.0, 5.8, 6.1, 5.87, 2.3))
    private val delta = 0.00000001

    @Test
    fun get() {
        assertEquals(u[3], 4.0, delta)
    }

    @Test
    fun set() {
        u[3] = 22
        v[2] = u[3]
        assertEquals(u[3], 22.0, delta)
        assertEquals(v[2], 22.0, delta)
    }

    @Test
    fun plus() {
        assertArrayEquals((u + v).toDoubleArray(), doubleArrayOf(6.0, 7.8, 9.1, 9.87, 7.3), delta)
    }

    @Test
    fun timesScalar() {
        val w = v * 5
        val x = 5 * v
        assertArrayEquals(w.toDoubleArray(), doubleArrayOf(25.0, 29.0, 30.5, 29.35, 11.5), delta)
        assertArrayEquals(w.toDoubleArray(), x.toDoubleArray(), delta)
    }

    @Test
    fun timesVector() {
        assertEquals(u * v, 69.88, delta)
    }

    @Test
    fun unary() {
        assertArrayEquals((+u).toDoubleArray(), u.toDoubleArray(), delta)
        assertArrayEquals((-u).toDoubleArray(), doubleArrayOf(-1.0, -2.0, -3.0, -4.0, -5.0), delta)
    }

    @Test
    fun minus() {
        val w = u - v
        val x = u + -1 * v
        assertArrayEquals(w.toDoubleArray(), x.toDoubleArray(), delta)
    }

    @Test
    fun div() {
        val w = u / 5
        val x = u * 0.2
        assertArrayEquals(w.toDoubleArray(), x.toDoubleArray(), delta)
    }

    @Test
    fun round() {
        val w = v.roundValues()
        assertArrayEquals(v.toDoubleArray(), doubleArrayOf(5.0, 6.0, 6.0, 6.0, 2.0), delta)
        assertArrayEquals(w.toLongArray(), longArrayOf(5, 6, 6, 6, 2))
    }

    @Test
    fun subVector() {
        val a = u.subVector(2, 4)
        val b = u.subVector(1..3)
        val c = u.subVector(sortedSetOf(0, 4, 3))
        assertArrayEquals(a.toDoubleArray(), doubleArrayOf(3.0, 4.0, 5.0), delta)
        assertArrayEquals(b.toDoubleArray(), doubleArrayOf(2.0, 3.0, 4.0), delta)
        assertArrayEquals(c.toDoubleArray(), doubleArrayOf(1.0, 4.0, 5.0), delta)
    }

    @Test
    fun swap() {
        u.swap(0, 4)
        assertArrayEquals(u.toDoubleArray(), doubleArrayOf(5.0, 2.0, 3.0, 4.0, 1.0), delta)
    }
}
