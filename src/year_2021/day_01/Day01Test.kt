package year_2021.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val sampleText = listOf(
        "199",
        "200",
        "208",
        "210",
        "200",
        "207",
        "240",
        "269",
        "260",
        "263",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day01.solutionOne(sampleText)

        assertEquals(7, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day01.solutionTwo(sampleText)

        assertEquals(5, solutionTwo)
    }
}