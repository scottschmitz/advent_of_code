package template

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day0XTest {

    private val sampleText = listOf(
        "---",
        "---",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day0X.solutionOne(sampleText)

        assertEquals(-1, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day0X.solutionTwo(sampleText)

        assertEquals(-1, solutionTwo)
    }
}