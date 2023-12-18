package year_2023.day_16

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day16Test {

    private val sampleText = listOf(
        ".|...\\....",
        "|.-.\\.....",
        ".....|-...",
        "........|.",
        "..........",
        ".........\\",
        "..../.\\\\..",
        ".-.-/..|..",
        ".|....-|.\\",
        "..//.|....",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day16.solutionOne(sampleText)

        assertEquals(46, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day16.solutionTwo(sampleText)

        assertEquals(51, solutionTwo)
    }
}