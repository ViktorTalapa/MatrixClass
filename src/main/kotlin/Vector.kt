import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Vector(values: Array<Double>) {

    private val data = ArrayList(values.asList())

    val size: Int
        get() = data.size

    constructor(values: Collection<Number>) : this(Array(values.size) { i -> values.elementAt(i).toDouble() })

    constructor(length: Int, value: Number = 0.0) : this(Array(length) { value.toDouble() })

    operator fun get(i: Int): Double = data[i]

    operator fun set(i: Int, x: Number) {
        data[i] = x.toDouble()
    }

    operator fun plus(v: Vector): Vector {
        require(size == v.size) { "Vector sizes must be the same." }
        return Vector(Array(size) { i -> this[i] + v[i] })
    }

    operator fun times(s: Number) = Vector(Array(size) { i -> this[i] * s.toDouble() })

    operator fun times(v: Vector): Double {
        require(size == v.size) { "Vector sizes must be the same." }
        var result = 0.0
        for (i in 0 until size)
            result += this[i] * v[i]
        return result
    }

    operator fun unaryPlus() = this.times(1)

    operator fun unaryMinus() = this.times(-1)

    operator fun minus(v: Vector) = this.plus(v.unaryMinus())

    operator fun div(s: Number) = this.times(1.0 / s.toDouble())

    override fun equals(other: Any?): Boolean {
        if (other !is Vector || size != other.size)
            return false
        for (i in 0 until size)
            if (this[i] != other[i])
                return false
        return true
    }

    override fun hashCode() = data.hashCode()

    fun product(): Double = data.reduce { acc, it -> acc * it }

    fun subVector(indexes: SortedSet<Int>): Vector {
        require(indexes.first() in 0..indexes.last() && indexes.last() < size) {
            "Sub-vector boundaries are invalid."
        }
        return Vector(Array(indexes.size) { i -> data[indexes.elementAt(i)] })
    }

    fun subVector(indexes: IntRange) = subVector(indexes.toSortedSet())

    fun subVector(fromIndex: Int, toIndex: Int) = subVector(fromIndex..toIndex)

    fun sum(): Double = data.sum()

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

    companion object {

        fun generate(
            size: Int,
            minValue: Number = Double.MIN_VALUE,
            maxValue: Number = Double.MAX_VALUE,
            integers: Boolean = false
        ): Vector {
            val result = ArrayList<Number>()
            for (i in 0 until size)
                if (integers)
                    result.add(Random.nextInt(minValue.toInt(), maxValue.toInt()))
                else
                    result.add(Random.nextDouble(minValue.toDouble(), maxValue.toDouble()))
            return Vector(result)
        }
    }
}