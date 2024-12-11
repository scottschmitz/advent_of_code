package year_2024.day_11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {

  private val sampleText = "125 17"

  @Test
  fun testSolutionOne() {
    val solutionOne = Day11.solution(6, sampleText)

    assertEquals(22L, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day11.solution(75, sampleText)

    assertEquals(65601038650482L, solutionTwo)
  }
}