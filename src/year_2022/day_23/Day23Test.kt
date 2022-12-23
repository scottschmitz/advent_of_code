package year_2022.day_23

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day23Test {

    private val sampleText = listOf(
        "....#..",
        "..###.#",
        "#...#.#",
        ".#...##",
        "#.###..",
        "##.#.##",
        ".#..#..",
    )

    @Test
    fun testSolutionOne() {
        val day = Day23(sampleText)
        assertEquals(110, day.solutionOne())
    }

    @Test
    fun testSolutionTwo() {
        val day = Day23(sampleText)
        assertEquals(20, day.solutionTwo())
    }
}