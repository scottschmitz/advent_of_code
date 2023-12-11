package year_2023.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val sampleText = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day09.solutionOne(sampleText)

        assertEquals(114, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day09.solutionTwo(sampleText)

        assertEquals(2, solutionTwo)
    }
}