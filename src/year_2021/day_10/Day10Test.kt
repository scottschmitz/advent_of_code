package year_2021.day_10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val sampleText = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day10.solutionOne(sampleText)

        assertEquals(26397, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day10.solutionTwo(sampleText)

        assertEquals(288957, solutionTwo)
    }
}