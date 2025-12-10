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

fun List<Point>.calculateFencePerimeter(): Int {
  val pointsSet = this.toSet()
  var perimeter = 0

  forEach { point ->
    // Each point starts with 4 sides
    var sides = 4

    // Check for adjacent points and subtract shared sides
    if (pointsSet.contains(point.up())) {
      sides -= 1
    }
    if (pointsSet.contains(point.down())) {
      sides -= 1
    }
    if (pointsSet.contains(point.left())) {
      sides -= 1
    }
    if (pointsSet.contains(point.right())) {
      sides -= 1
    }
    perimeter += sides
  }
  return perimeter
}

fun List<Point>.calculateNumberOfSides(): Int {
  val pointsSet = this.toSet()

  // create the border
  val border = mutableListOf<Point>()
  forEach { point ->
    if (point.neighbors().any { !pointsSet.contains(it) }) {
      border.add(point)
    }
  }

  val requiredOutside = border.flatMap { it.neighbors().filter { !pointsSet.contains(it) } }.toSet()
  val seenOutside = mutableSetOf<Point>()
  var totalEdges = 0

  //while loop until we have seen all of the points touching but not within our polygon
  while (seenOutside.size < requiredOutside.size) {
    val outsideStartingPoint = requiredOutside.first { it !in seenOutside }
    val initialDirection = when {
      outsideStartingPoint.plus(Direction.NORTH.delta) in border -> Direction.WEST
      outsideStartingPoint.plus(Direction.SOUTH.delta) in border -> Direction.EAST
      outsideStartingPoint.plus(Direction.EAST.delta) in border -> Direction.NORTH
      else -> Direction.SOUTH
    }

    var changesOfDirection = 0
    var currentPoint = outsideStartingPoint
    var currentDirection = initialDirection
    seenOutside.add(currentPoint)

    val thisThing = mutableListOf<Point>()
    while (changesOfDirection < 4 || !(currentPoint == outsideStartingPoint && currentDirection == initialDirection)) {
      val nextPoint = currentPoint.plus(currentDirection.delta)
      if (nextPoint in border) {
        currentDirection = currentDirection.turn90CounterClockwise()
        changesOfDirection += 1
      } else if (nextPoint.plus(currentDirection.turn90Clockwise().delta) !in border) {
        // we are at a corner and need to turn and move forward to catch the side again
        currentDirection = currentDirection.turn90Clockwise()
        currentPoint = nextPoint.plus(currentDirection.delta)
        changesOfDirection += 1
      } else {
        currentPoint = nextPoint
      }

      thisThing.add(currentPoint)
      seenOutside.add(currentPoint)
    }
    totalEdges += changesOfDirection
  }

  return totalEdges
}

fun List<Point>.display() {
  val minX = minBy { it.first }.first - 1
  val maxX = maxBy { it.first }.first + 1
  val minY = minBy { it.second }.second - 1
  val maxY = maxBy { it.second }.second + 1

  for (y in minY..maxY) {
    for (x in minX..maxX) {
      if (x to y in this) {
        print("#")
      } else {
        print(".")
      }
    }
    println()
  }
}

fun List<Point>.containsPolygon(polygon: List<Point>): Boolean {
    // 1. All vertices inside (or on boundary if non-strict)
    for (p in this) {
        val inside = this.hasPoint(p)
        if (!inside) return false
    }

//    // 2. No edges intersect outer boundary
//    for (i in indices) {
//        val a1 = polygon[i]
//        val a2 = polygon[(i + 1) % polygon.size]
//
//        for (j in indices) {
//            val b1 = get(j)
//            val b2 = get((j + 1) % size)
//
//            if (segmentsIntersect(a1, a2, b1, b2)) {
//                return false
//            }
//        }
//    }

    return true
}

fun List<Point>.hasPoint(point: Point): Boolean {
    var inside = false

    for (i in 0 until size) {
        val a = get(i)
        val b = get((i + 1) % size)

        val intersects = ((a.y() > point.y()) != (b.y() > point.y()))
            && (point.x() < (b.x() - a.x()) * (point.y() - a.y()) / (b.y() - a.y()) + a.x())

        // If boundary is hit exactly: treat as inside
        if (isPointOnSegment(a, b, point)) return true

        if (intersects) inside = !inside
    }
    return inside
}

fun List<Point>.floodFill(): Set<Point> {
    val walls: Set<Point> = this
        .zip(drop(1) + first())
        .flatMap { (a, b) -> a.connect(b) }
        .toSet()

    // 2. Bounding box (expanded by 1 so we have a guaranteed outside point)
    val minX = walls.minOf { it.x() } - 1
    val maxX = walls.maxOf { it.x() } + 1
    val minY = walls.minOf { it.y() } - 1
    val maxY = walls.maxOf { it.y() } + 1

    // 3. Flood-fill from outside
    val outside = mutableSetOf<Point>()
    val queue = ArrayDeque<Point>()

    val start = Point(minX, minY)
    queue += start
    outside += start

    fun neighbors(p: Point): List<Point> = listOf(
        Point(p.x() + 1, p.y()),
        Point(p.x() - 1, p.y()),
        Point(p.x(), p.y() + 1),
        Point(p.x(), p.y() - 1),
    )

    while (queue.isNotEmpty()) {
        val p = queue.removeFirst()
        for (n in neighbors(p)) {
            if (n.x() !in minX..maxX || n.y() !in minY..maxY) continue
            if (n in walls) continue          // can't go through walls
            if (n in outside) continue
            outside += n
            queue += n
        }
    }

    // 4. Everything not outside and not a wall is inside (green interior)
    val inside = mutableSetOf<Point>()
    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val p = Point(x, y)
            if (p !in outside && p !in walls) {
                inside += p
            }
        }
    }

    return walls + inside
}

fun Set<Point>.isInside(shape: Set<Point>): Boolean {
    return this.all { it in shape}
}

private fun isPointOnSegment(a: Point, b: Point, p: Point): Boolean {
    if (orientation(a, b, p) != 0) return false
    return onSegment(a, b, p)
}

private fun orientation(a: Point, b: Point, c: Point): Int {
    val v = (b.y() - a.y()) * (c.x() - b.x()) - (b.x() - a.x()) * (c.y() - b.y())
    return when {
        v > 0 -> 1     // clockwise
        v < 0 -> 2     // counterclockwise
        else -> 0      // collinear
    }
}

private fun onSegment(a: Point, b: Point, p: Point): Boolean {
    return p.x() >= minOf(a.x(), b.x()) && p.x() <= maxOf(a.x(), b.x()) &&
            p.y() >= minOf(a.y(), b.y()) && p.y() <= maxOf(a.y(), b.y())
}

fun segmentsIntersect(a1: Point, a2: Point, b1: Point, b2: Point): Boolean {
    val o1 = orientation(a1, a2, b1)
    val o2 = orientation(a1, a2, b2)
    val o3 = orientation(b1, b2, a1)
    val o4 = orientation(b1, b2, a2)

    // General case
    if (o1 != o2 && o3 != o4) return true
    if (o1 == 0 && onSegment(a1, a2, b1)) return true
    if (o2 == 0 && onSegment(a1, a2, b2)) return true
    if (o3 == 0 && onSegment(b1, b2, a1)) return true
    if (o4 == 0 && onSegment(b1, b2, a2)) return true

    return false
}