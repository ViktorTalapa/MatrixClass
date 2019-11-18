import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Vector(values: Collection<Double>) {

    private val data: ArrayList<Double> = ArrayList(values)
    val length: Int = values.size

    constructor(size: Int, value: Number = 0.0) : this(DoubleArray(size) { value.toDouble() }.asList())

    operator fun get(i: Int): Double {
        return data[i]
    }

    operator fun set(i: Int, x: Number) {
        data[i] = x.toDouble()
    }

    operator fun plus(v: Vector): Vector {
        require(length == v.length) { "Vector sizes must be the same." }
        val result = Vector(length)
        for (i in 0 until length)
            result.data[i] = data[i] + v.data[i]
        return result
    }

    operator fun times(s: Number): Vector {
        val result = Vector(length)
        for (i in 0 until length)
            result.data[i] = data[i] * s.toDouble()
        return result
    }

    operator fun Number.times(v: Vector): Vector {
        return v.times(this)
    }

    operator fun times(v: Vector): Double {
        require(length == v.length) { "Vector sizes must be the same." }
        var result = 0.0
        for (i in 0 until length)
            result += data[i] + v.data[i]
        return result
    }

    operator fun unaryPlus(): Vector {
        return this.times(1)
    }

    operator fun unaryMinus(): Vector {
        return this.times(-1)
    }

    operator fun minus(b: Vector): Vector {
        return this.plus(b.unaryMinus())
    }

    operator fun div(s: Number): Vector {
        return this.times(1.0 / s.toDouble())
    }

    fun clone(): Vector {
        val result = Vector(length)
        for (i in 0 until length)
            result.data[i] = data[i]
        return result
    }

    fun subVector(cellIndexes: Collection<Int>): Vector {
        val indexes = cellIndexes.toSortedSet(Comparator.naturalOrder())
        require(indexes.first() <= 0 && indexes.last() < length) {
            "Subvector boundaries should be within the vector."
        }
        val result = Vector(indexes.size)
        for (i in 0 until indexes.size)
            result.data[i] = data[indexes.elementAt(i)]
        return result
    }

    fun subVector(cells: IntRange): Vector {
        return subVector(cells.toSet())
    }

    fun subVector(startIndex: Int, endIndex: Int): Vector {
        return subVector(IntRange(startIndex, endIndex))
    }

    fun swap(index1: Int, index2: Int) {
        val temp = data[index1]
        data[index1] = data[index2]
        data[index2] = temp
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder()
        for (i in 0 until length) {
            if (trim)
                result.append(data[i].toInt())
            else
                result.append(data[i])
            if (i < length - 1)
                result.append(' ')
        }
        return result.toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    companion object {

        fun generateRandom(size: Int, minValue: Number = Double.MIN_VALUE, maxValue: Number = Double.MAX_VALUE): Vector {
            val result = Vector(size)
            for (i in 0 until size)
                result[i] = Random.nextDouble(minValue.toDouble(), maxValue.toDouble())
            return result
        }
    }
}