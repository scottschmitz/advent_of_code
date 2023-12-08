package year_2023.day_08

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test {

    @Test
    fun testSolutionOne_ExampleOne() {
        val sampleText = listOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)",
        )
        val solutionOne = Day08.solutionOne(sampleText)

        assertEquals(2, solutionOne)
    }

    @Test
    fun testSolutionOne_ExampleTwo() {
        val sampleText = listOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)",
        )
        val solutionOne = Day08.solutionOne(sampleText)

        assertEquals(6, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val sampleText = listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
        )
        val solutionTwo = Day08.solutionTwo(sampleText)

        assertEquals(6, solutionTwo)
    }
}