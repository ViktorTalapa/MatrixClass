import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random

class Vector(values: Array<Double>) {

    private val data: ArrayList<Double> = ArrayList(values.asList())

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
        return Vector(Array(size) { i -> data[i] + v.data[i] })
    }

    operator fun times(s: Number): Vector {
        val result = Vector(size)
        for (i in 0 until size)
            result.data[i] = data[i] * s.toDouble()
        return result
    }

    operator fun times(v: Vector): Double {
        require(size == v.size) { "Vector sizes must be the same." }
        var result = 0.0
        for (i in 0 until size)
            result += data[i] * v.data[i]
        return result
    }

    operator fun unaryPlus(): Vector = this.times(1)

    operator fun unaryMinus(): Vector = this.times(-1)

    operator fun minus(v: Vector): Vector = this.plus(v.unaryMinus())

    operator fun div(s: Number): Vector = this.times(1.0 / s.toDouble())

    fun subVector(cellIndexes: SortedSet<Int>): Vector {
        require(cellIndexes.first() in 0..cellIndexes.last() && cellIndexes.last() < size) {
            "Subvector boundaries are invalid."
        }
        val result = ArrayList<Double>()
        for (index in cellIndexes)
            result.add(data[index])
        return Vector(result)
    }

    fun subVector(cells: IntRange): Vector = subVector(cells.toSortedSet())

    fun subVector(fromIndex: Int, toIndex: Int): Vector = subVector(fromIndex..toIndex)

    fun swap(index1: Int, index2: Int) {
        val temp = data[index1]
        data[index1] = data[index2]
        data[index2] = temp
    }

    fun toList(): List<Double> = data.toList()

    fun toDoubleArray(): DoubleArray = data.toDoubleArray()

    fun toIntArray(): IntArray = IntArray(size) { i -> data[i].roundToInt() }

    fun toLongArray(): LongArray = LongArray(size) { i -> data[i].roundToLong() }

    fun toString(trim: Boolean): String {
        val result = StringBuilder("| ")
        for (i in 0 until size) {
            if (trim)
                result.append(data[i].toString().split('.').first())
            else
                result.append(data[i])
            result.append(' ')
        }
        return result.append('|').toString()
    }

    override fun toString(): String = this.toString(false)

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