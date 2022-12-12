package year_2022.day_12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day12Test {

    private val sampleText = listOf(
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day12.solutionOne(sampleText)

        assertEquals(31, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day12.solutionTwo(sampleText)

        assertEquals(29, solutionTwo)
    }
}