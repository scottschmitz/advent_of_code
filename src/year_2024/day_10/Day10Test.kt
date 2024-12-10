package year_2024.day_10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {

  private val sampleText = listOf(
    "89010123",
    "78121874",
    "87430965",
    "96549874",
    "45678903",
    "32019012",
    "01329801",
    "10456732",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day10.solutionOne(sampleText)

    assertEquals(36, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day10.solutionTwo(sampleText)

    assertEquals(81, solutionTwo)
  }
}