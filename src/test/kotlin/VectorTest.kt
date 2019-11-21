import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class VectorTest : MyTest() {

    private val u = Vector(arrayOf(1, 2, 3, 4, 5).asList())
    private val v = Vector(listOf(5.0, 5.8, 6.1, 5.87, 2.3))

    @Test
    fun get() {
        assertEquals(u[3], 4.0, delta)
        assertEquals(v[4], 2.3, delta)
    }

    @Test
    fun set() {
        u[3] = 22
        v[2] = u[3]
        assertEquals(u[3], 22.0, delta)
        assertEquals(v[2], 22.0, delta)
    }

    @Test
    fun equals() {
        assertEquals(u, Vector(listOf(1.0, 2.0, 3.0, 4.0, 5.0)))
    }

    @Test
    fun plus() {
        assertEquals(u + v, Vector(listOf(6.0, 7.8, 9.1, 9.87, 7.3)), delta)
    }

    @Test
    fun times() {
        val x = v * 5.0
        val y = 5 * v
        assertEquals(x, Vector(listOf(25.0, 29.0, 30.5, 29.35, 11.5)))
        assertEquals(x, y)
        assertEquals(u * v, 69.88, delta)
    }

    @Test
    fun div() {
        assertEquals(u / 5, u * 0.2)
        assertEquals(5 / u, Vector(listOf(5.0, 2.5, 5 / 3.0, 1.25, 1.0)))
    }

    @Test
    fun unary() {
        assertEquals(+u, u)
        assertEquals(-u, Vector(listOf(-1.0, -2.0, -3.0, -4.0, -5.0)))
        assertEquals(u - v, -v + u)
    }

    @Test
    fun round() {
        assertArrayEquals(u.toIntArray(), intArrayOf(1, 2, 3, 4, 5))
        assertArrayEquals(v.toLongArray(), longArrayOf(5, 6, 6, 6, 2))
    }

    @Test
    fun subVector() {
        assertEquals(u.subVector(2, 4), Vector(listOf(3.0, 4.0, 5.0)), delta)
        assertEquals(u.subVector(1..3), Vector(listOf(2.0, 3.0, 4.0)), delta)
        assertEquals(u.subVector(sortedSetOf(0, 4, 3)), Vector(listOf(1.0, 4.0, 5.0)), delta)
    }

    @Test
    fun swap() {
        u.swap(0, 4)
        assertEquals(u, Vector(listOf(5.0, 2.0, 3.0, 4.0, 1.0)), delta)
    }
}
