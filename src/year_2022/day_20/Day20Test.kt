package year_2022.day_20

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day20Test {

    private val sampleText = listOf(
        "1",
        "2",
        "-3",
        "3",
        "-2",
        "0",
        "4",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day20(sampleText).solutionOne()

        assertEquals(3, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day20(sampleText).solutionTwo()

        assertEquals(1623178306, solutionTwo)
    }
}