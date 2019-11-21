operator fun Number.times(v: Vector): Vector = v.times(this)

operator fun Number.times(m: Matrix): Matrix = m.times(this)

operator fun Number.div(v: Vector): Vector = Vector(Array(v.size) { i -> this.toDouble() / v[i] }.asList())

