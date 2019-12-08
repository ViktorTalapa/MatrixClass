object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Here is a 5 Vector:")
        println(MathVector.random(5).toString())
        println("Here is a 5x5 Identity Matrix:")
        println(Matrix.identity(5).toString())
        println("Here is a 5x5 Random Matrix:")
        println(SquareMatrix.random(5))
        println("Here is a 4x4 Matrix of 3:")
        val a = Matrix(4, 4, 3)
        print(a.toString())
    }
}