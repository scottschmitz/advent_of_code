package year_2023.day_14

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {

    private val sampleText = listOf(
        "O....#....",
        "O.OO#....#",
        ".....##...",
        "OO.#O....O",
        ".O.....O#.",
        "O.#..O.#.#",
        "..O..#O..O",
        ".......O..",
        "#....###..",
        "#OO..#....",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day14.solutionOne(sampleText)

        assertEquals(136, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day14.solutionTwo(sampleText)

        assertEquals(64, solutionTwo)
    }
}