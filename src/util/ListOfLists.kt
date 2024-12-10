package util

fun<T> List<List<T>>.findAll(value: T): List<Point> {
  val result = mutableListOf<Point>()
  for (row in this.indices) {
    for (column in this[row].indices) {
      if (this[row][column] == value) {
        result.add(Point(column, row))
      }
    }
  }
  return result
}

fun<T> List<List<T>>.get(point: Point): T {
  return this[point.y()][point.x()]
}

fun<T> List<List<T>>.containsPoint(point: Point): Boolean {
  return point.y() in this.indices && point.x() in this[point.y()].indices
}