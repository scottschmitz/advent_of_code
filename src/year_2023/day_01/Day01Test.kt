package year_2023.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val sampleText1 = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet",
    )

    private val sampleText2 = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day01.solutionOne(sampleText1)

        assertEquals(142, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day01.solutionTwo(sampleText2)

        assertEquals(281, solutionTwo)
    }
}