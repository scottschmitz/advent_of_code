package year_2024.day_16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16Test {

  private val sampleText = listOf(
    "###############",
    "#.......#....E#",
    "#.#.###.#.###.#",
    "#.....#.#...#.#",
    "#.###.#####.#.#",
    "#.#.#.......#.#",
    "#.#.#####.###.#",
    "#...........#.#",
    "###.#.#####.#.#",
    "#...#.....#.#.#",
    "#.#.#.###.#.#.#",
    "#.....#...#.#.#",
    "#.###.#.#.#.#.#",
    "#S..#.....#...#",
    "###############",
  )

  @Test
  fun testSolutionOne() {
    val solutionOne = Day16(sampleText).solutionOne()

    assertEquals(7036, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day16(sampleText).solutionTwo()

    assertEquals(45, solutionTwo)
  }
}