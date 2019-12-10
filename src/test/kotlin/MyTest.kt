import org.junit.Assert

open class MyTest {

    val epsilon = 1e-10

   fun assertEquals(expected: MathVector, actual: MathVector, epsilon: Double = this.epsilon) {
        Assert.assertEquals(expected.size, actual.size)
        for(i in expected.indices)
            Assert.assertEquals(expected[i], actual[i], epsilon)
    }

    fun assertEquals(expected: Matrix, actual: Matrix, epsilon: Double = this.epsilon) {
        Assert.assertEquals(expected.height, actual.height)
        Assert.assertEquals(expected.width, actual.width)
        for(i in 0 until expected.height)
            assertEquals(expected.row(i), actual.row(i), epsilon)
    }
}