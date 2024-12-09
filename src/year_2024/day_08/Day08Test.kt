package year_2024.day_08

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test {

  private val sampleText = listOf(
    "............",
    "........0...",
    ".....0......",
    ".......0....",
    "....0.......",
    "......A.....",
    "............",
    "............",
    "........A...",
    ".........A..",
    "............",
    "............",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day08.solutionOne(sampleText)

    assertEquals(14, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day08.solutionTwo(sampleText)

    assertEquals(34, solutionTwo)
  }
}