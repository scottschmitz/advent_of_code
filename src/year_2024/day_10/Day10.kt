package year_2024.day_10

import readInput
import util.*

object Day10 {
  fun solutionOne(text: List<String>): Int {
    val grid = createGrid(text)

    val trailHeads = grid.findAll(0)
    return trailHeads.sumOf { trailHead ->
      val score = exploreTrailWithMemory(trailHead, mutableSetOf(), grid)
      score
    }
  }

  fun solutionTwo(text: List<String>): Int {
    val grid = createGrid(text)

    val trailHeads = grid.findAll(0)
    return trailHeads.sumOf { trailHead ->
      val score = exploreTrailWithoutMemory(trailHead, setOf(), grid)
      score
    }
  }

  private fun createGrid(text: List<String>): List<List<Int>> {
    return text.map { line ->
      line.map {
        try {
          it.toString().toInt()
        } catch (e: Exception) {
          -1
        }
      }
    }
  }

  private fun exploreTrailWithMemory(
    currentPoint: Point,
    mutablePointsVisited: MutableSet<Point>,
    grid: List<List<Int>>
  ): Int {
    mutablePointsVisited.add(currentPoint)
    return exploreTrail(
      currentPoint,
      grid,
      hasVisitedPoint = { point -> mutablePointsVisited.contains(point) },
      continueTrail = { point -> exploreTrailWithMemory(point, mutablePointsVisited, grid) }
    )
  }

  private fun exploreTrailWithoutMemory(currentPoint: Point, pointsVisited: Set<Point>, grid: List<List<Int>>): Int {
    val mutablePointsVisited = pointsVisited.toMutableSet()
    mutablePointsVisited.add(currentPoint)

    return exploreTrail(
      currentPoint,
      grid,
      hasVisitedPoint = { point -> mutablePointsVisited.contains(point) },
      continueTrail = { point -> exploreTrailWithoutMemory(point, mutablePointsVisited, grid) }
    )
  }

  private fun exploreTrail(
    currentPoint: Point,
    grid: List<List<Int>>,
    hasVisitedPoint: (Point) -> Boolean,
    continueTrail: (Point) -> Int,
  ): Int {
    val height = grid.get(currentPoint)
    if (height == 9) {
      return 1
    }

    return currentPoint.neighbors(includeDiagonals = false)
      .filter { neighborPoint ->
        grid.containsPoint(neighborPoint)
          && grid.get(neighborPoint) == height + 1
          && !hasVisitedPoint(neighborPoint)
      }.sumOf { neighborPoint ->
        continueTrail(neighborPoint)
      }
  }
}

fun main() {
  val text = readInput("year_2024/day_10/Day10.txt")

  val solutionOne = Day10.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day10.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
