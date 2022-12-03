package day_03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val sampleText = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day03.solutionOne(sampleText)

        assertEquals(157, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day03.solutionTwo(sampleText)

        assertEquals(70, solutionTwo)
    }
}
