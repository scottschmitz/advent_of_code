package year_2022.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val sampleText = listOf(
        "1000",
        "2000",
        "3000",
        "",
        "4000",
        "",
        "5000",
        "6000",
        "",
        "7000",
        "8000",
        "9000",
        "",
        "10000"
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day01.solutionOne(sampleText)

        assertEquals(4, solutionOne.first)
        assertEquals(24_000, solutionOne.second)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day01.solutionTwo(sampleText)

        assertEquals(45000, solutionTwo)
    }
}