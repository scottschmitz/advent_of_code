package year_2024.day_03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day03Test {

    @Test
    fun testSolutionOne() {
        val sampleText = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        val solutionOne = Day03.solutionOne(sampleText)

        assertEquals(161, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val sampleText = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        val solutionTwo = Day03.solutionTwo(sampleText)

        assertEquals(48, solutionTwo)
    }
}