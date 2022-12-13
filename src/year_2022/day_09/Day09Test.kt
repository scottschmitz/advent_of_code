package year_2022.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    @Test
    fun testSolutionOne() {
        val sampleText = listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2",
        )
        val solutionOne = Day09.solutionOne(sampleText)

        assertEquals(13, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val sampleText = listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20",
        )
        val solutionTwo = Day09.solutionTwo(sampleText)

        assertEquals(36, solutionTwo)
    }
}