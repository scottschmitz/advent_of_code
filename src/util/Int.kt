package util

import kotlin.math.pow

fun Int.pow(exponent: Int): Int {
  return this.toDouble().pow(exponent).toInt()
}

fun List<Int>.product(): Int {
    var product = 0
    this.forEach { value ->
        if (product == 0) {
            product = value
        } else {
            product *= value
        }
    }
    return product
}