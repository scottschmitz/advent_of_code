package year_2021.day_03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val sampleText = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day03.solutionOne(sampleText)

        assertEquals(198, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day03.solutionTwo(sampleText)

        assertEquals(230, solutionTwo)
    }
}