package util

import kotlin.math.pow

fun Long.pow(exp: Long): Long {
  return this.toDouble().pow(exp.toDouble()).toLong()
}