package year_2024.day_06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {

  private val sampleText = listOf(
      "....#.....",
      ".........#",
      "..........",
      "..#.......",
      ".......#..",
      "..........",
      ".#..^.....",
      "........#.",
      "#.........",
      "......#...",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day06.solutionOne(sampleText)

    assertEquals(41, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day06.solutionTwo(sampleText)

    assertEquals(6, solutionTwo)
  }
}