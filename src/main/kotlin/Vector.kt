import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToLong
import kotlin.random.Random

class Vector(values: Collection<Number>) {

    private val data: ArrayList<Double> = ArrayList()
    val length: Int = values.size

    init {
        for (cell in values)
            data.add(cell.toDouble())
    }

    constructor(values: Array<Number>) : this(values.asList())

    constructor(size: Int, value: Number = 0.0) : this(Array<Number>(size) { value })

    operator fun get(i: Int): Double {
        return data[i]
    }

    operator fun set(i: Int, x: Number) {
        data[i] = x.toDouble()
    }

    operator fun plus(v: Vector): Vector {
        require(length == v.length) { "Vector sizes must be the same." }
        val result = Vector(length)
        for (i in data.indices)
            result.data[i] = data[i] + v.data[i]
        return result
    }

    operator fun times(s: Number): Vector {
        val result = Vector(length)
        for (i in data.indices)
            result.data[i] = data[i] * s.toDouble()
        return result
    }

    operator fun times(v: Vector): Double {
        require(length == v.length) { "Vector sizes must be the same." }
        var result = 0.0
        for (i in data.indices)
            result += data[i] * v.data[i]
        return result
    }

    operator fun unaryPlus(): Vector {
        return this.times(1)
    }

    operator fun unaryMinus(): Vector {
        return this.times(-1)
    }

    operator fun minus(v: Vector): Vector {
        return this.plus(v.unaryMinus())
    }

    operator fun div(s: Number): Vector {
        return this.times(1.0 / s.toDouble())
    }

    fun clone(): Vector {
        return Vector(data.toList())
    }

    fun roundValues(): List<Long> {
        val round = ArrayList<Long>()
        for (i in data.indices) {
            val n = data[i].roundToLong()
            data[i] = n.toDouble()
            round.add(n)
        }
        return round
    }

    fun subVector(cellIndexes: SortedSet<Int>): Vector {
        require(cellIndexes.first() in 0..cellIndexes.last() && cellIndexes.last() < length) {
            "Subvector boundaries are invalid."
        }
        val result = ArrayList<Double>()
        for (index in cellIndexes)
            result.add(data[index])
        return Vector(result)
    }

    fun subVector(cells: IntRange): Vector {
        return subVector(cells.toSortedSet())
    }

    fun subVector(fromIndex: Int, toIndex: Int): Vector {
        return subVector(fromIndex..toIndex)
    }

    fun swap(index1: Int, index2: Int) {
        val temp = data[index1]
        data[index1] = data[index2]
        data[index2] = temp
    }

    fun toList(): List<Double> {
        return data.toList()
    }

    fun toDoubleArray(): DoubleArray {
        return data.toDoubleArray()
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder("| ")
        for (i in 0 until length) {
            if (trim)
                result.append(data[i].toString().split('.').first())
            else
                result.append(data[i])
            result.append(' ')
        }
        return result.append('|').toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    companion object {

        fun generateRandom(
            size: Int,
            minValue: Number = Double.MIN_VALUE,
            maxValue: Number = Double.MAX_VALUE
        ): Vector {
            val result = ArrayList<Double>()
            for (i in 0 until size)
                result.add(Random.nextDouble(minValue.toDouble(), maxValue.toDouble()))
            return Vector(result)
        }
    }
}

operator fun Number.times(v: Vector): Vector {
    return v.times(this)
}