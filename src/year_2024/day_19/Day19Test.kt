package year_2024.day_19

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day19Test {

  private val sampleText = listOf(
    "r, wr, b, g, bwu, rb, gb, br",
    "",
    "brwrr",
    "bggr",
    "gbbr",
    "rrbgbr",
    "ubwu",
    "bwurrg",
    "brgr",
    "bbrgwb",
  )

  @Test
  fun testSolutionOne() {
    val day19Pt1 = Day19(sampleText)
    val solutionOne = day19Pt1.solutionOne()

    assertEquals(6, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val day19Pt2 = Day19(sampleText)
    val solutionTwo = day19Pt2.solutionTwo()

    assertEquals(16L, solutionTwo)
  }
}