package year_2021.day_05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {

    private val sampleText = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day05.solutionOne(sampleText)

        assertEquals(5, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day05.solutionTwo(sampleText)

        assertEquals(12, solutionTwo)
    }
}