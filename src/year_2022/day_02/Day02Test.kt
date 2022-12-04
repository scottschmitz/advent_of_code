package year_2022.day_02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val sampleText = listOf(
        "A Y",
        "B X",
        "C Z",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day02.solutionOne(sampleText)

        assertEquals(15, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day02.solutionTwo(sampleText)

        assertEquals(12, solutionTwo)
    }
}