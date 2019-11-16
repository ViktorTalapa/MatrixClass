data class Matrix(protected val data: Array<Vector>) {

    protected val height: Int = data.size
    protected val width: Int = data.first().size

    constructor(rows: Int, columns: Int, value: Number = 0.0) : this(Array(rows) { Vector(columns, value) })

    operator fun get(i: Int): Vector {
        return data[i]
    }

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    operator fun set(i: Int, v: Vector) {
        require(width == v.size) { "Vector sizes must be the same." }
        data[i] = v
    }

    operator fun set(i: Int, j: Int, x: Number) {
        data[i][j] = x.toDouble()
    }

    fun getColumn(columnIndex: Int): Vector {
        val column = Vector(height)
        for (i in 0 until height)
            column[i] = this[i, columnIndex]
        return column
    }

    fun getRow(rowIndex: Int): Vector {
        return this[rowIndex]
    }

    operator fun plus(b: Matrix): Matrix {
        require(height == b.height && width == b.width) { "Matrices have incompatible sizes for addition." }
        val result = Matrix(height, width)
        for (i in 0 until height)
            result[i] = this[i] + b[i]
        return result
    }

    operator fun minus(b: Matrix): Matrix {
        return this.plus(b.unaryMinus())
    }

    operator fun times(s: Number): Matrix {
        val result = Matrix(height, width)
        for (i in 0 until height)
                result[i] = this[i] * s.toDouble()
        return result
    }

    operator fun Double.times(m: Matrix): Matrix {
        return m.times(this)
    }

    operator fun times(m: Matrix): Matrix {
        require(width == m.height) { "Matrices have incompatible sizes for multiplication." }
        val result = Matrix(height, m.width)
        for(i in 0 until height)
            for (j in 0 until m.width)
                result[i, j] = this.getRow(i) * m.getColumn(j)
        return result
    }

    operator fun div(s: Number): Matrix {
        return this.times(1.0 / s.toDouble())
    }

    operator fun Number.div(m: Matrix): Matrix {
        return m.div(this)
    }

    operator fun unaryPlus(): Matrix {
        return this.times(1)
    }

    operator fun unaryMinus(): Matrix {
        return this.times(-1)
    }

    fun swap(rowIndex1: Int, columnIndex1: Int, rowIndex2: Int, columnIndex2: Int) {
        val temp = this[rowIndex1, columnIndex1]
        this[rowIndex1, columnIndex1] = this[rowIndex2, columnIndex2]
        this[rowIndex2, columnIndex2] = temp
    }

    fun swapColumns(columnIndex1: Int, columnIndex2: Int) {
        for (i in 0 until height)
            swap(i, columnIndex1, i, columnIndex2)
    }

    fun swapRows(rowIndex1: Int, rowIndex2: Int) {
        for (i in 0 until width)
            swap(rowIndex1, i, rowIndex2, i)
    }

    fun transpose(): Matrix {
        val trans = Matrix(width, height)
        for (i in 0 until width)
            for (j in 0 until height)
                trans[i, j] = this[j, i]
        return trans
    }

    fun toString(trim: Boolean): String {
        val result = StringBuilder()
        for (row in data)
            result.append(row.toString(trim)).append(System.lineSeparator())
        return result.toString()
    }

    override fun toString(): String {
        return this.toString(false)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix
        if (height != other.height) return false
        if (width != other.width) return false
        if (!data.contentEquals(other.data)) return false
        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    companion object {

        fun identity(order: Int): Matrix {
            val id = Matrix(order, order)
            for (i in 0 until order)
                id.data[i][i] = 1.0
            return id
        }

        fun generate(rows: Int, columns: Int, minValue: Number = 0.0, maxValue: Number = 9.0): Matrix {
            val result = Matrix(rows, columns)
            for (i in 0 until rows)
                result[i] = Vector.generate(columns, minValue, maxValue)
            return result
        }
    }
}