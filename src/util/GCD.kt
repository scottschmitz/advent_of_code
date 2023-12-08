package util

import java.math.BigInteger

fun gcd(a: Int, b: Int): Int {
  if (b == 0) {
    return a
  }
  return gcd(b, a % b)
}

fun gcd(a: Long, b: Long): Long {
  if (b == 0L) {
    return a
  }
  return gcd(b, a % b)
}

fun gcd(a: BigInteger, b: BigInteger): BigInteger {
  if (b == (0).toBigInteger()) {
    return a
  }
  return gcd(b, a % b)
}