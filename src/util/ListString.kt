package util


fun List<String>.positionOfFirst(char: Char): Point {
  val row = indexOfFirst { it.contains(char) }
  val col = this[row].indexOf(char)

  return Point(col, row)
}

fun List<String>.containsPoint(point: Point): Boolean {
  return point.y() in this.indices && point.x() in this[point.y()].indices
}

fun List<String>.get(point: Point): Char {
  return this[point.y()][point.x()]
}