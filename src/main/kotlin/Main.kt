object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Here is a 4x4 Identity Matrix:")
        println(Matrix.identity(4).toString())
        println("Here is a 3x4 Random Matrix:")
        println(Matrix.generate(7, 5).toString())
    }
}