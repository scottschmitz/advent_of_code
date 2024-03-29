package util

import kotlin.math.abs


fun List<Point>.calculateArea(includePerimeter: Boolean = false): Long {
  fun gaussArea(points: List<Point>): Long {
    val last = points.lastIndex
    val area = (0 until last).fold(0L) { acc, i ->
      acc + points[i].first * points[i + 1].second - points[i + 1].first * points[i].second
    } + points[last].first * points[0].second - points[0].first * points[last].second

    return abs(area) / 2
  }

  var permimeter = 0
  var currentPoint = 0 to 0
  this.forEach { point ->
    permimeter += currentPoint.manhattanDistance(point)
    currentPoint = point
  }

  val innerArea = gaussArea(this) + 1 - permimeter / 2

  return when (includePerimeter) {
    true -> innerArea + permimeter
    else -> innerArea
  }
}

fun List<Point>.display() {
  val minX = minBy { it.first }.first
  val maxX = maxBy { it.first }.first
  val minY = minBy { it.second }.second
  val maxY = maxBy { it.second }.second

  for (y in minY .. maxY) {
    for (x in minX .. maxX) {
      if (x to y in this) {
        print("#")
      } else {
        print(".")
      }
    }
    println()
  }
}
