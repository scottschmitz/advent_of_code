package year_2022.day_05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {

    private val sampleText = listOf(
        "    [D]",
        "[N] [C]",
        "[Z] [M] [P]",
        " 1   2   3",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day05.solutionOne(sampleText)

        assertEquals("CMZ", solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day05.solutionTwo(sampleText)

        assertEquals("MCD", solutionTwo)
    }
}