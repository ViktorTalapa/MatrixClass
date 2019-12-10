import kotlin.random.Random

/**
 * Vector class constructed from an array of Doubles.
 * It also represents a row or column of a matrix.
 */
data class MathVector(private val data: DoubleArray) : Collection<Double> {

    override val size: Int
        get() = data.size

    constructor(values: Collection<Number>) : this(DoubleArray(values.size) { i -> values.elementAt(i).toDouble() })

    constructor(vararg values: Number) : this(values.asList())

    operator fun get(index: Int) = data[index]

    operator fun set(index: Int, element: Number) {
        data[index] = element.toDouble()
    }

    operator fun plus(other: MathVector) = MathVector(DoubleArray(size) { i -> this[i] + other[i] })

    operator fun times(scalar: Number) = MathVector(DoubleArray(size) { i -> this[i] * scalar.toDouble() })

    operator fun times(other: MathVector) = (DoubleArray(size) { i -> this[i] * other[i] }).reduce { acc, it -> acc + it }

    operator fun unaryPlus() = this.times(1)

    operator fun unaryMinus() = this.times(-1)

    operator fun minus(other: MathVector) = this.plus(other.unaryMinus())

    operator fun div(scalar: Number) = this.times(1 / scalar.toDouble())

    fun product() = data.reduce { acc, it -> acc * it }

    fun sum() = data.reduce { acc, it -> acc + it }

    fun subVector(indexes: Set<Int>) = MathVector(DoubleArray(indexes.size) { i -> data[indexes.elementAt(i)] })

    fun subVector(indexes: IntRange) = this.subVector(indexes.toSortedSet())

    fun subVector(fromIndex: Int, toIndex: Int) = this.subVector(fromIndex..toIndex)

    fun swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }

    fun toList() = data.toList()

    override fun toString(): String {
        val result = StringBuilder("| ")
        for (i in 0 until size)
            result.append(this[i]).append(' ')
        return result.append('|').toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (javaClass != other?.javaClass)
            return false

        other as MathVector
        if (!data.contentEquals(other.data))
            return false
        return true
    }

    override fun hashCode() = data.contentHashCode()

    override fun contains(element: Double) = data.contains(element)

    override fun containsAll(elements: Collection<Double>): Boolean {
        for (element in elements)
            if (!data.contains(element))
                return false
        return true
    }

    override fun isEmpty() = data.isEmpty()

    override fun iterator() = data.iterator()

    companion object {

        /**
         * Constructs a Vector of the same value (default is 0) with a given size.
         */
        fun generate(size: Int, value: Number = 0.0) = MathVector(DoubleArray(size) { value.toDouble() })

        /**
         * Constructs a Vector of random values with a given size.
         */
        fun random(size: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE) =
            MathVector(DoubleArray(size) { Random.nextDouble(minValue.toDouble(), maxValue.toDouble()) })
    }
}