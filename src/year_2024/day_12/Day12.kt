package year_2024.day_12

import readInput
import util.*

object Day12 {
  fun solutionOne(text: List<String>): Int {
    val gardens = mutableListOf<Set<Point>>()
    val allIncludedPoints = mutableListOf<Point>()

    val grid = text.toGrid()

    grid.indices.forEach { y ->
      grid[y].indices.forEach { x ->
        val point = Point(x, y)
        if (point !in allIncludedPoints) {
          val garden = findTheGarden(point, mutableSetOf(), grid)
          gardens.add(garden)
          allIncludedPoints.addAll(garden)
        }
      }
    }

    return gardens
      .map { garden ->
        val area = garden.size
        val fence = garden.toList().calculateFencePerimeter()
        area to fence
      }
      .sumOf { (area, fence) -> area * fence }
  }

  fun solutionTwo(text: List<String>): Int {
    val gardens = mutableListOf<Set<Point>>()
    val allIncludedPoints = mutableListOf<Point>()

    val grid = text.toGrid()

    grid.indices.forEach { y ->
      grid[y].indices.forEach { x ->
        val point = Point(x, y)
        if (point !in allIncludedPoints) {
          val garden = findTheGarden(point, mutableSetOf(), grid)
          gardens.add(garden)
          allIncludedPoints.addAll(garden)
        }
      }
    }

    return gardens
      .map { garden ->
        val area = garden.size
        val sides = garden.toList().calculateNumberOfSides()

        println("Area: $area, Sides: $sides")
        area to sides
      }
      .sumOf { (area, externalEdges) -> area * externalEdges }
  }

  private fun findTheGarden(point: Point, alreadyAdded: MutableSet<Point>, grid: List<List<Char>>): Set<Point> {
    alreadyAdded.add(point)
    val garden = grid.get(point)

    return point.neighbors(includeDiagonals = false).flatMap { neighbor ->
      if (grid.containsPoint(neighbor) && grid.get(neighbor) == garden && neighbor !in alreadyAdded) {
        findTheGarden(neighbor, alreadyAdded, grid)
      } else {
        alreadyAdded
      }
    }.toSet()
  }
}

fun main() {
  val text = readInput("year_2024/day_12/Day12.txt")

  val solutionOne = Day12.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day12.solutionTwo(text)
  println("Solution 2: $solutionTwo")
  println("842155 is too high")
}
