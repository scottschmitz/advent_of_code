package year_2023.day_20

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day20Test {

    @Test
    fun testSolutionOne_SampleOne() {
        val sampleText = listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a",
        )
        val solutionOne = Day20.solutionOne(sampleText)

        assertEquals(32000000, solutionOne)
    }

    @Test
    fun testSolutionOne_SampleTwo() {
        val sampleText = listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output",
        )
        val solutionOne = Day20.solutionOne(sampleText)

        assertEquals(11687500, solutionOne)
    }
}