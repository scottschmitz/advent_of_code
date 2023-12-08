package year_2023.day_07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val sampleText = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day07.solutionOne(sampleText)

        assertEquals(6440, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day07.solutionTwo(sampleText)

        assertEquals(5905, solutionTwo)
    }
}