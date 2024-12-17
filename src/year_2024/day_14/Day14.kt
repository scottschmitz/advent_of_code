package year_2024.day_14

import readInput
import util.*
import java.io.File

class Day14(text: List<String>) {

  private val robots = parseText(text)

  fun solutionOne(): Long {
    val width = 101
    val height = 103
    val moves = 100

    val finalLocations = robots.map { robot ->
      move(robot, moves, width, height)
    }

    val upperLeft = finalLocations.filter { point ->
      point.x() < (width / 2) && point.y() < (height / 2)
    }.size.toLong()

    val upperRight = finalLocations.filter { point ->
      point.x() > (width / 2) && point.y() < (height / 2)
    }.size

    val lowerLeft = finalLocations.filter { point ->
      point.x() < (width / 2) && point.y() > (height / 2)
    }.size

    val lowerRight = finalLocations.filter { point ->
      point.x() > (width / 2) && point.y() > (height / 2)
    }.size

    return upperLeft * upperRight * lowerLeft * lowerRight
  }

  fun solutionTwo(): Int {
    val width = 101
    val height = 103

    val file = File("output.txt")
    file.bufferedWriter().use { writer ->
      for (i in 1..width * height) {
        val finalLocations = robots.map { robot ->
          move(robot, i, width, height)
        }


        val someNeighbors = finalLocations.map { point ->
          point.neighbors().filter { it in finalLocations }.size
        }.filter { it > 2 }
          .size > 10

        if (someNeighbors) {
          writer.write("Time: $i")
          writer.newLine()
          for (y in 0 until height) {
            for (x in 0 until width) {
              val point = Point(x, y)
              if (finalLocations.contains(point)) {
                writer.write("X")
              } else {
                writer.write(".")
              }
            }
            writer.newLine()
          }

          writer.newLine()
          writer.write("----------------")
          writer.newLine()
        }
      }
    }

    return -1
  }

  private fun parseText(text: List<String>): List<Robot> {
    val regex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()

    return text.mapIndexed { index, line ->
      val matchResult = regex.find(line)!!

      val (x1, y1, x2, y2) = matchResult.destructured
      val point1 = Point(x1.toInt(), y1.toInt())
      val point2 = Point(x2.toInt(), y2.toInt())
      Robot(point1, point2)
    }
  }

  private fun move(robot: Robot, times: Int, width: Int, height: Int): Point {
    var x = (robot.position.x() + (robot.velocity.x() * times)) % width
    if (x < 0) {
      x += width
    }

    var y = (robot.position.y() + (robot.velocity.y() * times)) % height
    if (y < 0) {
      y += height
    }

    return Point(x, y)
  }

  data class Robot(val position: Point, val velocity: Point)
}

fun main() {
  val text = readInput("year_2024/day_14/Day14.txt")
  val day14 = Day14(text)

  val solutionOne = day14.solutionOne()
  println("Solution 1: $solutionOne")

  val solutionTwo = day14.solutionTwo()
  println("Solution 2: $solutionTwo")
}
