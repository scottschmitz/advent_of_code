package year_2022.day_14

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {

    private val sampleText = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day14.solutionOne(sampleText)

        assertEquals(24, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day14.solutionTwo(sampleText)

        assertEquals(93, solutionTwo)
    }
}