import org.junit.Assert.assertEquals
import org.junit.Test

class VectorTest : MyTest() {

    private val u = Vector(arrayOf(1, 2, 3, 4, 5).asList())
    private val v = Vector(listOf(5.0, 5.8, 6.1, 5.87, 2.3))

    @Test
    fun index() {
        assertEquals(4, u[3])
        u[3] = 22
        v[2] = u[3]
        assertEquals(22, v[2])
    }

    @Test
    fun plus() {
        assertEquals(Vector(listOf(6.0, 7.8, 9.1, 9.87, 7.3)), u + v)
        assertEquals(u + v, v + u)
    }

    @Test
    fun times() {
        assertEquals(Vector(listOf(25.0, 29.0, 30.5, 29.35, 11.5)), v * 5.0)
        assertEquals(v * 5.0, 5 * v)
        assertEquals(69.88, u * v)
    }

    @Test
    fun div() {
        assertEquals(u * 0.2, u / 5)
    }

    @Test
    fun unary() {
        assertEquals(u, +u)
        assertEquals(Vector(listOf(-1.0, -2.0, -3.0, -4.0, -5.0)), -u)
        assertEquals(u - v, -v + u)
    }

    @Test
    fun subVector() {
        assertEquals(Vector(listOf(3.0, 4.0, 5.0)), u.subVector(2, 4))
        assertEquals(Vector(listOf(2.0, 3.0, 4.0)), u.subVector(1..3))
        assertEquals(Vector(listOf(1.0, 4.0, 5.0)), u.subVector(sortedSetOf(0, 4, 3)))
    }

    @Test
    fun swap() {
        u.swap(0, 4)
        assertEquals(Vector(listOf(5.0, 2.0, 3.0, 4.0, 1.0)), u)
    }
}
