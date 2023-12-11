package util

import kotlin.math.abs

// combination of shoelace and picks theorem
fun List<Point>.area(): Int {

  fun det2(mat: List<List<Int>>): Int {
    require(mat.size == 2)
    require(mat.all { it.size == 2})
    return mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0]
  }

  // shoelace formula interior area
  val area = abs((this + first())
    .windowed(2)
    .map { it.map { it.toList() } }
    .sumOf { det2(it) } / 2)

  // picks theorem
  return area + 1 - size / 2
}