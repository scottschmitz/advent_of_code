package year_2021.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val sampleText = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day09.solutionOne(sampleText)

        assertEquals(15, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day09.solutionTwo(sampleText)

        assertEquals(1134, solutionTwo)
    }
}