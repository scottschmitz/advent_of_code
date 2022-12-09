package year_2022.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val sampleText = listOf(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day09.solutionOne(sampleText)

        assertEquals(13, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day09.solutionTwo(sampleText)

        assertEquals(36, solutionTwo)
    }
}