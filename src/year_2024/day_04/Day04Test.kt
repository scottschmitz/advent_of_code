package year_2024.day_04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day04Test {

    private val sampleText = listOf(
        "MMMSXXMASM",
        "MSAMXMSMSA",
        "AMXSXMAAMM",
        "MSAMASMSMX",
        "XMASAMXAMM",
        "XXAMMXXAMA",
        "SMSMSASXSS",
        "SAXAMASAAA",
        "MAMMMXMMMM",
        "MXMXAXMASX",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day04.solutionOne(sampleText)

        assertEquals(18, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day04.solutionTwo(sampleText)

        assertEquals(9, solutionTwo)
    }
}