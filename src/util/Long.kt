package util

import kotlin.math.pow

fun Long.pow(exp: Long): Long {
  return this.toDouble().pow(exp.toDouble()).toLong()
}

fun List<Long>.product(): Long {
    var product = 0L
    this.forEach { value ->
        if (product == 0L) {
            product = value
        } else {
            product *= value
        }
    }
    return product
}