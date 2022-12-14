package year_2021.day_11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val sampleText = listOf(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day11.solutionOne(sampleText)

        assertEquals(1656, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day11.solutionTwo(sampleText)

        assertEquals(195, solutionTwo)
    }
}