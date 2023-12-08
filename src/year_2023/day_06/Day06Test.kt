package year_2023.day_06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {

    private val sampleText = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day06.solutionOne(sampleText)

        assertEquals(288, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day06.solutionTwo(sampleText)

        assertEquals(71503, solutionTwo)
    }
}