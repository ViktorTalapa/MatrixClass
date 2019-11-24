/**
 * Default implementation of operators to generic Number class - will be overloaded in Int, Double etc.
 */
operator fun Number.plus(x: Number): Number = this.toDouble() + x.toDouble()

operator fun Number.minus(x: Number): Number = this.toDouble() - x.toDouble()

operator fun Number.times(x: Number): Number = this.toDouble() * x.toDouble()

operator fun Number.div(x: Number): Number = this.toDouble() / x.toDouble()

/**
 * Number extensions for Vectors and Matrices
 */
operator fun Number.times(v: Vector) = v.times(this)

operator fun Number.times(m: Matrix) = m.times(this)


