package template

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DayXXTest {

    private val sampleText = listOf(
        "---",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = DayXX.solutionOne(sampleText)

        assertEquals(-1, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = DayXX.solutionTwo(sampleText)

        assertEquals(-1, solutionTwo)
    }
}