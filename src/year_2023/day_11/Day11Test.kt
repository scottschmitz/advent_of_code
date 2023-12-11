package year_2023.day_11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val sampleText = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#.....",
    )

    @Test
    fun testSolution_OneExpansion() {
        val solutionOne = Day11.distances(sampleText, 2)

        assertEquals(374, solutionOne)
    }

    @Test
    fun testSolution_TenExpansions() {
        val solutionOne = Day11.distances(sampleText, 10)

        assertEquals(1030, solutionOne)
    }

    @Test
    fun testSolution_OneHundredExpansions() {
        val solutionTwo = Day11.distances(sampleText, 100)

        assertEquals(8410, solutionTwo)
    }
}