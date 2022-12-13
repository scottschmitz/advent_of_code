package year_2022.day_13

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {

    private val sampleText = listOf(
        "[1,1,3,1,1]",
        "[1,1,5,1,1]",
        "",
        "[[1],[2,3,4]]",
        "[[1],4]",
        "",
        "[9]",
        "[[8,7,6]]",
        "",
        "[[4,4],4,4]",
        "[[4,4],4,4,4]",
        "",
        "[7,7,7,7]",
        "[7,7,7]",
        "",
        "[]",
        "[3]",
        "",
        "[[[]]]",
        "[[]]",
        "",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day13.solutionOne(sampleText)

        assertEquals(13, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day13.solutionTwo(sampleText)

        assertEquals(140, solutionTwo)
    }
}