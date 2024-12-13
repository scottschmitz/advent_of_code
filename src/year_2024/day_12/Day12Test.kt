package year_2024.day_12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12Test {

  private val sampleText = listOf(
    "RRRRIICCFF",
    "RRRRIICCCF",
    "VVRRRCCFFF",
    "VVRCCCJFFF",
    "VVVVCJJCFE",
    "VVIVCCJJEE",
    "VVIIICJJEE",
    "MIIIIIJJEE",
    "MIIISIJEEE",
    "MMMISSJEEE",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day12.solutionOne(sampleText)

    assertEquals(1930, solutionOne)
  }

  @Test
  fun testSolutionTwoSimple() {
    val text = listOf(
      "AAAA",
      "BBCD",
      "BBCC",
      "EEEC",
    )
    val solutionTwo = Day12.solutionTwo(text)

    assertEquals(80, solutionTwo)
  }

  @Test
  fun testSolutionTwoEShape() {
    val text = listOf(
      "EEEEE",
      "EXXXX",
      "EEEEE",
      "EXXXX",
      "EEEEE",
    )
    val solutionTwo = Day12.solutionTwo(text)

    assertEquals(236, solutionTwo)
  }

  @Test
  fun testSolutionTwoGardensInside() {
    val text = listOf(
      "AAAAAA",
      "AAABBA",
      "AAABBA",
      "ABBAAA",
      "ABBAAA",
      "AAAAAA",
    )
    val solutionTwo = Day12.solutionTwo(text)

    assertEquals(368, solutionTwo)
  }

  @Test
  fun testSolutionTwoGardensInsideTestDiagonals() {
    val text = listOf(
      ".....",
      ".AAA.",
      ".A.A.",
      ".AA..",
      ".A.A.",
      ".AAA.",
      ".....",
    )
    val solutionTwo = Day12.solutionTwo(text)
    assertEquals(452, solutionTwo)
  }

  @Test
  fun testSolutionTwoReturnToInitialLocationAndDirection() {
    val text = listOf(
      "............",
      ".V..........",
      "VVVV........",
      "VVV.........",
      "VVV.........",
      "VVV.........",
      "VVVVVV......",
      "VVVVVV..V...",
      ".VV.VVVVV...",
      ".V..VVVVVV..",
      "VVVVVVVV.V..",
      "VVVVVVV.....",
      "..VVVVVVV...",
      ".VVVVVVV....",
      ".VVVVVVV....",
      "VVVVVVVVV...",
      "VVVVVV......",
      "VVVVVV......",
      "VVVVVVV..V..",
      "VVVVVVVVVVV.",
      ".VVVVVVVVV..",
      "..VVVVVVV...",
      "....VVVVVV..",
      "...VVV......",
      "............",
    )

    val solutionTwo = Day12.solutionTwo(text)
    assertEquals(19226, solutionTwo)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day12.solutionTwo(sampleText)

    assertEquals(1206, solutionTwo)
  }
}