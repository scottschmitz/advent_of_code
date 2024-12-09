package year_2024.day_07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day07Test {

  private val sampleText = listOf(
    "190: 10 19",
    "3267: 81 40 27",
    "83: 17 5",
    "156: 15 6",
    "7290: 6 8 6 15",
    "161011: 16 10 13",
    "192: 17 8 14",
    "21037: 9 7 18 13",
    "292: 11 6 16 20",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day07.solutionOne(sampleText)

    assertEquals(BigInteger.valueOf(3749), solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day07.solutionTwo(sampleText)

    assertEquals(BigInteger.valueOf(11387), solutionTwo)
  }
}