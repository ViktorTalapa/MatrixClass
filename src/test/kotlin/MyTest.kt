import org.junit.Assert

open class MyTest {

    val delta = 0.0001

    fun assertEquals(expected: Vector, actual: Vector, epsilon: Double = delta) {
        Assert.assertEquals(expected.size, actual.size)
        for(i in 0 until expected.size)
            Assert.assertEquals(expected[i], actual[i], epsilon)
    }

    fun assertEquals(expected: Matrix, actual: Matrix, epsilon: Double = delta) {
        Assert.assertEquals(expected.height, actual.height)
        Assert.assertEquals(expected.width, actual.width)
        for(i in 0 until expected.height)
            assertEquals(expected.row(i), actual.row(i), epsilon)
    }
}