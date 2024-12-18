package year_2024.day_18

import readInput
import util.*
import java.util.PriorityQueue

class Day18(text: List<String>) {

  private val incomingBytes = parseInputs(text)

  fun solutionOne(numBytes: Int, gridSize: Int): Int {
    val corruptedPoints = incomingBytes.take(numBytes)
    return explore(
      startPoint = Point(0, 0),
      exitPoint = Point(gridSize-1, gridSize-1),
      corruptedPoints = corruptedPoints
    )
  }

  fun solutionTwo(bytesToStartWith: Int, gridSize: Int): Point {
    for (i in bytesToStartWith..incomingBytes.size)  {
      val shortest = explore(Point(0, 0), Point(gridSize-1, gridSize-1), incomingBytes.take(i))
      if (shortest == -1) {
        return incomingBytes[i - 1]
      }
    }
    return Point(-1, -1)
  }

  private fun parseInputs(text: List<String>): List<Point> {
    return text.map {
      val (x, y) = it.split(",").map { it.toInt() }
      Point(x, y)
    }
  }

  data class WalkCost(val point: Point, val cost: Int)
  private fun explore(
    startPoint: Point,
    exitPoint: Point,
    corruptedPoints: List<Point>
  ): Int {
    val memory = mutableMapOf<Point, Int>()
    val queue = PriorityQueue<WalkCost> { a, b -> a.cost - b.cost }

    queue.add(WalkCost(startPoint, 0))
    while (queue.isNotEmpty()) {
      val (to, cost) = queue.poll()
      if (to in memory) {
        continue
      }
      memory[to] = cost
      if (to == exitPoint) {
        break
      }

      to.neighbors()
        .filter { it !in corruptedPoints && it !in memory && it.isWithin(Bounds(Point(0, 0), exitPoint)) }
        .forEach { neighbor ->
          queue.add(WalkCost(neighbor, cost + 1))
        }
    }
    return memory.getOrDefault(exitPoint, -1)
  }
}

fun main() {
  val text = readInput("year_2024/day_18/Day18.txt")

  val day18 = Day18(text)
  val solutionOne = day18.solutionOne(1024, 71)
  println("Solution 1: $solutionOne")

  val solutionTwo = day18.solutionTwo(1025, 71)
  println("Solution 2: $solutionTwo")
}
