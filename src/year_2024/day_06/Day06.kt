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
    val (startingPosition, startingDirection) = startingPosition(text)

    val traveledPoints = mutableSetOf<Point>()
    traveledPoints.add(startingPosition)

    var currentPoint = startingPosition
    var currentDirection = startingDirection

    try {
      while (true) {
        val nextPoint = when (currentDirection) {
          WalkDirection.Up -> currentPoint.plus(Direction.NORTH.delta)
          WalkDirection.Down -> currentPoint.plus(Direction.SOUTH.delta)
          WalkDirection.Left -> currentPoint.plus(Direction.WEST.delta)
          WalkDirection.Right -> currentPoint.plus(Direction.EAST.delta)
        }

        if (text[nextPoint.y()][nextPoint.x()] == '#') {
          currentDirection = currentDirection.turnRight()
        } else {
          currentPoint = nextPoint
          traveledPoints.add(currentPoint)
        }
      }
    } catch (e: IndexOutOfBoundsException) {
      println("Escaped")
    }

    return traveledPoints.count()
  }

  fun solutionTwo(text: List<String>): Int {
    val (startingPosition, startingDirection) = startingPosition(text)
    val obstacles = mutableSetOf<Pair<Point, WalkDirection>>()

    var currentPoint = startingPosition
    var currentDirection = startingDirection

    try {
      while (true) {
        val nextPoint = when (currentDirection) {
          WalkDirection.Up -> currentPoint.plus(Direction.NORTH.delta)
          WalkDirection.Down -> currentPoint.plus(Direction.SOUTH.delta)
          WalkDirection.Left -> currentPoint.plus(Direction.WEST.delta)
          WalkDirection.Right -> currentPoint.plus(Direction.EAST.delta)
        }

        if (text[nextPoint.y()][nextPoint.x()] == '#') {
          obstacles.add(nextPoint to currentDirection)
          currentDirection = currentDirection.turnRight()
        } else {
          currentPoint = nextPoint
        }
      }
    } catch (e: IndexOutOfBoundsException) {

    }


    return obstacles.sumOf { (obstaclePoint, walkingDirection) ->
      val currentPosition = when (walkingDirection) {
        WalkDirection.Up -> obstaclePoint.plus(Direction.SOUTH.delta)
        WalkDirection.Down -> obstaclePoint.plus(Direction.NORTH.delta)
        WalkDirection.Left -> obstaclePoint.plus(Direction.EAST.delta)
        WalkDirection.Right -> obstaclePoint.plus(Direction.WEST.delta)
      }
      when (walkingDirection) {
        WalkDirection.Up -> {
          (currentPosition.x() until text[currentPosition.y()].length).count { column ->
            when (text[currentPosition.y()][column]) {
              '#' -> false
              '^' -> false
              else -> {
                val copy = text.toMutableList()
                // replace the character at the current position with the walking direction
                val stringBuilder = StringBuilder(text[currentPosition.y()])
                stringBuilder.setCharAt(column, '#' )
                copy[currentPosition.y()] = stringBuilder.toString()

                detectLoop(currentPosition, WalkDirection.Up, obstacles)
              }
            }
          }
        }
        WalkDirection.Down -> {
          (currentPosition.y() until text.size).count { row ->
            when (text[row][currentPosition.x()]) {
              '#' -> false
              '^' -> false
              else -> {
                val copy = text.toMutableList()
                // replace the character at the current position with the walking direction
                val stringBuilder = StringBuilder(text[row])
                stringBuilder.setCharAt(currentPosition.x(), '#' )
                copy[row] = stringBuilder.toString()

                detectLoop(currentPosition, WalkDirection.Up, obstacles)
              }
            }
          }
        }
        WalkDirection.Left -> {
          (currentPosition.x() downTo  0).count { column ->
            when (text[currentPosition.y()][column]) {
              '#' -> false
              '^' -> false
              else -> {
                val copy = text.toMutableList()
                // replace the character at the current position with the walking direction
                val stringBuilder = StringBuilder(text[currentPosition.y()])
                stringBuilder.setCharAt(column, '#' )
                copy[currentPosition.y()] = stringBuilder.toString()

                detectLoop(currentPosition, WalkDirection.Up, obstacles)
              }
            }
          }
        }
        WalkDirection.Right -> {
          (currentPosition.y() until text.size).count { row ->
            when (text[row][currentPosition.x()]) {
              '#' -> false
              '^' -> false
              else -> {
                val copy = text.toMutableList()
                // replace the character at the current position with the walking direction
                val stringBuilder = StringBuilder(text[row])
                stringBuilder.setCharAt(currentPosition.x(), '#' )
                copy[row] = stringBuilder.toString()

                detectLoop(currentPosition, WalkDirection.Up, obstacles)
              }
            }
          }
        }
      }
    }
  }


  fun detectLoop(obstaclePoint: Point, walkingDirection: WalkDirection, obstacles: Set<Pair<Point, WalkDirection>>): Boolean {
    return try {
      var currentPosition = when (walkingDirection) {
        WalkDirection.Up -> obstaclePoint.plus(Direction.SOUTH.delta)
        WalkDirection.Down -> obstaclePoint.plus(Direction.NORTH.delta)
        WalkDirection.Left -> obstaclePoint.plus(Direction.EAST.delta)
        WalkDirection.Right -> obstaclePoint.plus(Direction.WEST.delta)
      }
      var currentDirection = walkingDirection
      var directionChanges = 0

      while (directionChanges < 4) {
        val nextPoint = when (currentDirection) {
          WalkDirection.Up -> currentPosition.plus(Direction.NORTH.delta)
          WalkDirection.Down -> currentPosition.plus(Direction.SOUTH.delta)
          WalkDirection.Left -> currentPosition.plus(Direction.WEST.delta)
          WalkDirection.Right -> currentPosition.plus(Direction.EAST.delta)
        }

        if (obstacles.contains(nextPoint to currentDirection)) {
          currentDirection = currentDirection.turnRight()
          directionChanges++
        } else {
          currentPosition = nextPoint
          if (currentPosition == obstaclePoint && directionChanges == 3) {
            return true
          }
        }
      }
      true
    } catch (e: IndexOutOfBoundsException) {
      false
    }
  }








  private fun startingPosition(text: List<String>): Pair<Point, WalkDirection> {
    // find the first position where the character is one of the WalkDirection
    val row = text.indexOfFirst { it.contains(WalkDirection.Up.char) }
    val col = text[row].indexOf(WalkDirection.Up.char)

    val direction = when (text[row][col]) {
      WalkDirection.Up.char -> WalkDirection.Up
      WalkDirection.Down.char -> WalkDirection.Down
      WalkDirection.Left.char -> WalkDirection.Left
      WalkDirection.Right.char -> WalkDirection.Right
      else -> throw IllegalArgumentException("Invalid character")
    }

    return Point(col, row) to direction
  }

  private fun printMap(text: List<String>, traveledPoints: Set<Point>) {
    text.forEachIndexed { y, row ->
      row.forEachIndexed { x, char ->
        val point = Point(x, y)
        if (point in traveledPoints) {
          print('X')
        } else {
          print(char)
        }
      }
      println()
    }
  }

  private fun rotateMap(currentPosition: Point, text: List<String>, clockwise: Boolean): Pair<Point, List<String>> {
    val chars = text.map { it.toCharArray().toList() }

    return when (clockwise) {
      true -> {
        val map = chars[0].indices.map { i -> chars.map { it[i] } }
          .map { it.reversed().joinToString("") }
        val newPoint = Point(currentPosition.y(), text.size - 1 - currentPosition.x())
        newPoint to map
      }
      else -> {
        val map = chars[0].indices.map { i -> chars.map { it[i] } }
          .map { it.joinToString("") }
        val newPoint = Point(text[0].length - 1 - currentPosition.y(), currentPosition.x())
        newPoint to map
      }
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
