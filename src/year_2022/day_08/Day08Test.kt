package year_2022.day_08

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test {

    private val sampleText = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day08.solutionOne(sampleText)

        assertEquals(21, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day08.solutionTwo(sampleText)

        assertEquals(8, solutionTwo)
    }
}