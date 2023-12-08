package util

import java.math.BigInteger

fun List<Int>.lcm(): Int {
  return stream().reduce(1) { a, b -> (a * b) / gcd(a, b) }
}

fun List<Long>.lcm(): Long {
  return stream().reduce(1) { a, b -> (a * b) / gcd(a, b) }
}

fun List<BigInteger>.lcm(): BigInteger {
  return stream().reduce((1).toBigInteger()) { a, b -> (a * b) / gcd(a, b) }
}