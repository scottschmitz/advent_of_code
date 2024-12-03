package year_2024.day_02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val sampleText = listOf(
        "7 6 4 2 1",
        "1 2 7 8 9",
        "9 7 6 2 1",
        "1 3 2 4 5",
        "8 6 4 4 1",
        "1 3 6 7 9",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day02.solutionOne(sampleText)

        assertEquals(2, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day02.solutionTwo(sampleText)

        assertEquals(4, solutionTwo)
    }
}