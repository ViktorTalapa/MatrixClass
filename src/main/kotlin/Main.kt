object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Here is a 4x4 Identity Matrix:")
        println(Matrix.generateIdentity(4).toString(true))
        println("Here is a 3x4 Random Matrix:")
        println(Matrix.generateRandom(7, 5).toString())
    }
}