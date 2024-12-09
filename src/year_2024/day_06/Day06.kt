package year_2024.day_06

import readInput
import util.*

sealed class WalkDirection(val char: Char) {
  object Up : WalkDirection(char = '^')
  object Down : WalkDirection(char = 'v')
  object Left : WalkDirection(char = '<')
  object Right : WalkDirection(char = '>')

  fun turnRight(): WalkDirection {
    return when (this) {
      Up -> return Right
      Down -> return Left
      Left -> return Up
      Right -> return Down
    }
  }
}


object Day06 {

  fun solutionOne(text: List<String>): Int {
    val start = text.positionOfFirst('^')
    val visited = walk(start, text)

    return visited?.size ?: -1
  }

  fun solutionTwo(text: List<String>): Int {
    val start = text.positionOfFirst('^')
    val visited = walk(start, text)
    return visited?.count { point ->
      point != start && walk(start, text, point) == null
    } ?: -1
  }

  private fun walk(start: Point, text: List<String>, obstacle: Point? = null): Set<Point>? {
    var position = start
    var direction = Direction.NORTH
    val visited = mutableSetOf<Pair<Point, Direction>>()

    var loop = false
    while (text.containsPoint(position) && !loop) {
      loop = !visited.add(position to direction)

      val nextStraight = position + direction.delta
      if (text.containsPoint(nextStraight) && (text.get(nextStraight) == '#' || nextStraight == obstacle)) {
        direction = direction.turn90Clockwise()
      } else {
        position = nextStraight
      }
    }

    return when (loop) {
      true -> null
      else -> visited.mapTo(mutableSetOf()) { it.first }
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_06/Day06.txt")

  val solutionOne = Day06.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day06.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
