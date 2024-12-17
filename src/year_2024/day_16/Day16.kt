package year_2024.day_16

import readInput
import util.*

class Day16(val text: List<String>) {
  private val turn = 1_000
  private val step = 1

  private val grid = text.toGrid()
  private val startingLocation = grid.findAll('S').first()
  private val endingLocation = grid.findAll('E').first()

  private val memory = mutableMapOf<Pair<Point, Direction>, Int>()
  private val pathMemory = mutableMapOf<Point, MutableList<List<Point>>>()

  init {
    explore(startingLocation, 0, Direction.EAST, listOf(startingLocation), grid)
  }

  fun solutionOne(): Int {
    return memory.toList()
      .first { it.first.first == endingLocation }
      .second
  }

  fun solutionTwo(): Int {
    return pathMemory[endingLocation]!!
      .flatten()
      .toSet()
      .size
  }

  private fun explore(
    currentPoint: Point,
    currentCost: Int,
    currentDirection: Direction,
    currentPath: List<Point>,
    grid: List<List<Char>>
  ) {
    if (grid.get(currentPoint) == 'E') {
      val cheapestToE = listOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST).minOfOrNull { direction ->
        memory.getOrDefault(currentPoint to direction, Int.MAX_VALUE)
      } ?: Int.MAX_VALUE
      when {
        currentCost < cheapestToE -> {
          memory[currentPoint to currentDirection] = currentCost
          pathMemory[currentPoint] = mutableListOf(currentPath)
        }
        currentCost == cheapestToE -> {
          pathMemory[currentPoint] = pathMemory.getOrDefault(currentPoint, mutableListOf()).apply {
            add(currentPath)
          }
        }
        else -> { /* do nothing - not shortest distance*/ }
      }
      return
    }

    val previousCheapest = memory.getOrDefault(currentPoint to currentDirection, Int.MAX_VALUE)

    if (currentCost > previousCheapest) {
      // there was a cheaper route
      return
    }
    if (currentCost < previousCheapest) {
      // this is the new cheapest - forget about the rest
      memory[currentPoint to currentDirection] = currentCost
      pathMemory[currentPoint] = mutableListOf()
    }

    pathMemory[currentPoint] = pathMemory.getOrDefault(currentPoint, mutableListOf()).apply {
      add(currentPath)
    }

    listOf(
        currentPoint.plus(currentDirection.delta) to currentDirection,
        currentPoint.plus(currentDirection.turn90Clockwise().delta) to currentDirection.turn90Clockwise(),
        currentPoint.plus(currentDirection.turn90CounterClockwise().delta) to currentDirection.turn90CounterClockwise(),
    ).filter { (nextPoint, _) ->
      grid.get(nextPoint) != '#' && !currentPath.contains(nextPoint)
    }.forEach { (nextLocation, nextDirection) ->
      var nextCost = currentCost + step
      if (currentDirection != nextDirection) {
        nextCost += turn
      }

      val updatedPath = currentPath.toMutableList().apply {
        add(nextLocation)
      }

      explore(nextLocation, nextCost, nextDirection, updatedPath, grid)
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_16/Day16.txt")

  val day16 = Day16(text)
  val solutionOne = day16.solutionOne()
  println("Solution 1: $solutionOne")

  val solutionTwo = day16.solutionTwo()
  println("Solution 2: $solutionTwo")
}
