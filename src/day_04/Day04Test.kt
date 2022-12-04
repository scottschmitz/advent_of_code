package day_04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day04Test {

    private val sampleText = listOf(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day04.solutionOne(sampleText)

        assertEquals(2, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day04.solutionTwo(sampleText)

        assertEquals(4, solutionTwo)
    }
}