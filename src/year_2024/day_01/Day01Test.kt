package year_2024.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val sampleText = listOf(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day01.solutionOne(sampleText)

        assertEquals(11, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day01.solutionTwo(sampleText)

        assertEquals(31, solutionTwo)
    }
}