package util

import kotlin.math.pow

fun Int.pow(exponent: Int): Int {
  return this.toDouble().pow(exponent).toInt()
}