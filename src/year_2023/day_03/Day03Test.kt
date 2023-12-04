package year_2023.day_03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val sampleText = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598..",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day03.solutionOne(sampleText)

        assertEquals(4361, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day03.solutionTwo(sampleText)

        assertEquals(467835, solutionTwo)
    }
}