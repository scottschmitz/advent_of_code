package year_2021.day_06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {

    private val sampleText = "3,4,3,1,2"

    @Test
    fun testSolutionOne() {
        val solutionOne = Day06.solutionOne(sampleText)

        assertEquals(5934.toBigInteger(), solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day06.solutionTwo(sampleText)

        assertEquals(26984457539.toBigInteger(), solutionTwo)
    }
}