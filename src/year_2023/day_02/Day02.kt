package year_2023.day_02

import readInput
import kotlin.math.max

data class GameCube(
    val name: String,
    val maxCount: Int,
    var minPossible: Int = 0
) {
  fun reset() { minPossible = 0 }
}

object Day02 {


    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
      val cubes = listOf(
        GameCube("red", 12),
        GameCube("green", 13),
        GameCube("blue", 14),
      )

      var total = 0
      text.forEach { line ->
        val halves = line.split(":")
        val gameNumber = halves[0].split(" ")[1].toInt()

        val pulls = halves[1].replace(";", ",").split(", ")
        val possible = pulls.none { pull ->
          val split = pull.trim().split(" ")
          val count = split[0].toInt()
          val color = split[1]

          cubes.first { it.name == color }.maxCount < count
        }

        if (possible) {
          total += gameNumber
        }
      }

      return total
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
      val cubes = listOf(
        GameCube("red", 0),
        GameCube("green", 0),
        GameCube("blue", 0),
      )

      var total = 0

      text.forEach { line ->
        cubes.forEach { it.reset() }

        val halves = line.split(":")
        val gameNumber = halves[0].split(" ")[1].toInt()

        val pulls = halves[1].replace(";", ",").split(", ")
        pulls.forEach { pull ->
          val split = pull.trim().split(" ")
          val count = split[0].toInt()
          val color = split[1]

          val cube = cubes.first { it.name == color }
          if (cube.minPossible < count) {
            cube.minPossible = count
          }
        }

        var gamePower = 1
        cubes.forEach { cube -> gamePower *= cube.minPossible  }
        total += gamePower
      }

      return total
    }
}

fun main() {
    val text = readInput("year_2023/day_02/Day02.txt")

    val solutionOne = Day02.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day02.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
