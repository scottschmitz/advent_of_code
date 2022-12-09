package year_2021.day_07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val sampleText = "16,1,2,0,4,2,7,1,2,14"

    @Test
    fun testSolutionOne() {
        val solutionOne = Day07.solutionOne(sampleText)

        assertEquals(37, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day07.solutionTwo(sampleText)

        assertEquals(168, solutionTwo)
    }
}