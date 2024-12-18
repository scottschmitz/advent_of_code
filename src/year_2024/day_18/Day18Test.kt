package year_2024.day_18

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.Point

internal class Day18Test {

  private val sampleText = listOf(
    "5,4",
    "4,2",
    "4,5",
    "3,0",
    "2,1",
    "6,3",
    "2,4",
    "1,5",
    "0,6",
    "3,3",
    "2,6",
    "5,1",
    "1,2",
    "5,5",
    "2,5",
    "6,5",
    "1,4",
    "0,4",
    "6,4",
    "1,1",
    "6,1",
    "1,0",
    "0,5",
    "1,6",
    "2,0",
    )

  @Test
  fun testSolutionOne() {
    val day18 = Day18(sampleText)
    val solutionOne = day18.solutionOne(12 , 7)

    assertEquals(22, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val day18 = Day18(sampleText)
    val solutionTwo = day18.solutionTwo(1, 7)

    assertEquals(Point(6, 1), solutionTwo)
  }
}