package year_2021.day_02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val sampleText = listOf(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day02.solutionOne(sampleText)

        assertEquals(150, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day02.solutionTwo(sampleText)

        assertEquals(900, solutionTwo)
    }
}