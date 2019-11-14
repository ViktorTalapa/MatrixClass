object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Here is a 4x4 Identity Matrix:")
        println(Matrix.identity(4).toString())
        println("Here is a 7x5 Random Integer Matrix:")
        println(Matrix.generate(7, 5).toString())
        println("Here is a 3x4 Random Double Matrix:")
        println(Matrix.generate(7, 5, 100.0, 900.0).toString())
    }
}