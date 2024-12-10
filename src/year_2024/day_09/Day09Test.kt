package year_2024.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

  private val sampleText = "2333133121414131402"

  @Test
  fun testSolutionOne() {
    val solutionOne = Day09.solutionOne(sampleText)

    assertEquals(1928, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day09.solutionTwo(sampleText)

    assertEquals(2858, solutionTwo)
  }
}