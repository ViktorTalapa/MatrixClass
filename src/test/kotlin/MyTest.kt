import org.junit.Assert

open class MyTest {

    val epsilon = 1e-10

    fun assertEquals(expected: Vector, actual: Vector, epsilon: Double = this.epsilon) {
        Assert.assertEquals(expected.size, actual.size)
        for(i in 0 until expected.size)
            Assert.assertEquals(expected[i].toDouble(), actual[i].toDouble(), epsilon)
    }

    fun assertEquals(expected: Matrix, actual: Matrix, epsilon: Double = this.epsilon) {
        Assert.assertEquals(expected.height, actual.height)
        Assert.assertEquals(expected.width, actual.width)
        for(i in 0 until expected.height)
            assertEquals(expected.row(i), actual.row(i), epsilon)
    }
}