package year_2024.day_08

import readInput
import util.*

object Day08 {
  fun solutionOne(text: List<String>): Int {
    val antennas = findAntennaLocations(text)
    val antinodes = mutableSetOf<Point>()

    antennas.forEach { (_, points) ->
      // make a combination of every pair of points
      for (i in points.indices) {
        for (j in i + 1 until points.size) {
          val point1 = points[i]
          val point2 = points[j]
          val slope = Point(point2.x() - point1.x(), point2.y() - point1.y())

          val antinode1 = point1.minus(slope)
          if (isWithinMap(antinode1, text)) {
            antinodes.add(antinode1)
          }

          val antinode2 = point2.plus(slope)
          if (isWithinMap(antinode2, text)) {
            antinodes.add(antinode2)
          }
        }
      }
    }

    return antinodes.size
  }

  fun solutionTwo(text: List<String>): Int {
    val antennas = findAntennaLocations(text)
    val antinodes = mutableSetOf<Point>()

    antennas.forEach { (_, points) ->
      // make a combination of every pair of points
      for (i in points.indices) {
        for (j in i + 1 until points.size) {
          val point1 = points[i]
          val point2 = points[j]
          val slope = Point(point2.x() - point1.x(), point2.y() - point1.y())

          antinodes.add(point1)
          antinodes.add(point2)

          var antinodeMinus = point1.minus(slope)
          while (isWithinMap(antinodeMinus, text)) {
            antinodes.add(antinodeMinus)
            antinodeMinus = antinodeMinus.minus(slope)
          }

          var antinodePlus = point2.plus(slope)
          while (isWithinMap(antinodePlus, text)) {
            antinodes.add(antinodePlus)
            antinodePlus = antinodePlus.plus(slope)
          }
        }
      }
    }
    return antinodes.size
  }

  private fun findAntennaLocations(text: List<String>): Map<Char, List<Point>> {
    val antennaLocations = mutableMapOf<Char, List<Point>>()
    for (y in text.indices) {
      for (x in text[y].indices) {
        val char = text[y][x]
        if (char != '.'){
          antennaLocations[char] = antennaLocations.getOrDefault(char, emptyList()) + Point(x, y)
        }
      }
    }
    return antennaLocations
  }

  private fun isWithinMap(point: Point, text: List<String>): Boolean {
    return point.x() >= 0 && point.x() < text[0].length && point.y() >= 0 && point.y() < text.size
  }

  private fun printMap(text: List<String>, antinodes: Set<Point>) {
    text.forEachIndexed { y, row ->
      row.forEachIndexed { x, char ->
        val point = Point(x, y)
        if (char != '.') {
          print(char)
        } else if (point in antinodes) {
          print('#')
        } else {
          print(char)
        }
      }
      println()
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_08/Day08.txt")

  val solutionOne = Day08.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day08.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
