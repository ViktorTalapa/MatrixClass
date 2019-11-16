import java.lang.StringBuilder
import kotlin.random.Random

data class Vector(private val data: DoubleArray) {

    val size: Int = data.size

    constructor(size: Int, value: Number = 0.0) : this(DoubleArray(size) { value.toDouble() } )

    operator fun get(i: Int): Double {
        return data[i]
    }

    operator fun set(i: Int, x: Number) {
        data[i] = x.toDouble()
    }

    operator fun plus(b: Vector): Vector {
        require(size == b.size) { "Vector sizes must be the same." }
        val result = Vector(size)
        for (i in 0 until size)
            result[i] = this[i] + b[i]
        return result
    }

    operator fun times(s: Number): Vector {
        val result = Vector(size)
        for (i in 0 until size)
            result[i] = this[i] * s.toDouble()
        return result
    }

    operator fun Number.times(v: Vector): Vector {
        return v.times(this)
    }

    operator fun times(v: Vector) : Double {
        var result = 0.0
        for (i in 0 until size)
            result += this[i] + v[i]
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

    fun swap(index1: Int, index2: Int) {
        val temp = data[index1]
        data[index1] = data[index2]
        data[index2] = temp
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector
        if (size != other.size) return false
        if (!data.contentEquals(other.data)) return false
        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder()
        for (i in 0 until size) {
            if (trim)
                result.append(data[i].toInt())
            else
                result.append(data[i])
            if (i < size - 1)
                result.append(' ')
        }
        return result.toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    companion object {

        fun generate(size: Int, minValue: Number = 0.0, maxValue: Number = 9.0): Vector {
            val result = Vector(size)
            for (i in 0 until size)
                    result[i] = Random.nextDouble(minValue.toDouble(), maxValue.toDouble())
            return result
        }
    }
}