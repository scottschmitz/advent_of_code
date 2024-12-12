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

  val seenBorders = mutableSetOf<Point>()
  val uniqueBorders = mutableListOf<List<Point>>()
  var totalEdges = 0

  //while loop until seenBorders contains everything in border
  while (seenBorders.size < border.size) {
    val firstNotSeen = border.first { it !in seenBorders }

    val thisBorder = mutableSetOf<Point>()
    thisBorder.add(firstNotSeen)
    val outsideStartingPoint = firstNotSeen.neighbors().first { it !in pointsSet }
    val initialDirection = when {
      outsideStartingPoint.plus(Direction.NORTH.delta) in border -> Direction.WEST
      outsideStartingPoint.plus(Direction.SOUTH.delta) in border -> Direction.EAST
      outsideStartingPoint.plus(Direction.EAST.delta) in border -> Direction.NORTH
      else -> Direction.SOUTH
    }

    var changesOfDirection = 0
    var currentPoint = outsideStartingPoint
    var currentDirection = initialDirection
    while (changesOfDirection < 4 || currentPoint != outsideStartingPoint) {
      val nextPoint = currentPoint.plus(currentDirection.delta)
      if (nextPoint in border) {
        currentDirection = currentDirection.turn90CounterClockwise()
        changesOfDirection += 1
        thisBorder.add(nextPoint)
      } else if (nextPoint.plus(currentDirection.turn90Clockwise().delta) !in border) {
        // we are at a corner and need to turn and move forward to catch the side again
        currentDirection = currentDirection.turn90Clockwise()
        currentPoint = nextPoint.plus(currentDirection.delta)
        changesOfDirection += 1
      } else {
        currentPoint = nextPoint
        thisBorder.add(nextPoint.plus(currentDirection.turn90Clockwise().delta))
      }
    }

    uniqueBorders.add(thisBorder.toList())
    seenBorders.addAll(thisBorder)
    totalEdges += changesOfDirection
  }

  uniqueBorders.forEach { println(it) }
  println("---")
  return totalEdges
}

fun List<Point>.display() {
  val minX = minBy { it.first }.first
  val maxX = maxBy { it.first }.first
  val minY = minBy { it.second }.second
  val maxY = maxBy { it.second }.second

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
