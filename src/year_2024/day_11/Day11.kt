package year_2024.day_11

import readInput

object Day11 {

  // <stone, iterations> = resulting stone count
  val brains = mutableMapOf<Pair<Long, Long>, Long>()

  fun solution(iterations: Long, text: String): Long {
    val stones = parseInput(text)

    return stones.sumOf { stone ->
      calculateStones(iterations, stone)
    }
  }

  private fun parseInput(text: String): List<Long> {
    return text.split(" ").map { it.toLong() }
  }

  private fun calculateStones(iterations: Long, stone: Long): Long {
    // if last iteration
    if (iterations == 0L) {
      return 1
    }

    // if we have already seen this stone/iteration combo no need to evaluate further
    brains[stone to iterations]?.let { return it }

    // recursively call through ourselves and update our brain with the knowledge... because knowledge is power.
    return blink(stone).sumOf { resultingStones ->
      calculateStones(iterations - 1, resultingStones)
    }.also { brains[stone to iterations] = it }
  }

  private fun blink(stone: Long): List<Long> {
    return when {
      stone == 0L -> listOf(1)
      stone.toString().length % 2 == 0 -> listOf(
        stone.toString().take(stone.toString().length / 2).toLong(),
        stone.toString().drop(stone.toString().length / 2).toLong()
      )
      else -> listOf(stone * 2024)
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_11/Day11.txt").first()

  val solutionOne = Day11.solution(25, text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day11.solution(75, text)
  println("Solution 2: $solutionTwo")
}
