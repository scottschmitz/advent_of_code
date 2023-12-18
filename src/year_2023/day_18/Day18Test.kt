package year_2023.day_18

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {

    private val sampleText = listOf(
        "R 6 (#70c710)",
        "D 5 (#0dc571)",
        "L 2 (#5713f0)",
        "D 2 (#d2c081)",
        "R 2 (#59c680)",
        "D 2 (#411b91)",
        "L 5 (#8ceee2)",
        "U 2 (#caa173)",
        "L 1 (#1b58a2)",
        "U 2 (#caa171)",
        "R 2 (#7807d2)",
        "U 3 (#a77fa3)",
        "L 2 (#015232)",
        "U 2 (#7a21e3)",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day18.solutionOne(sampleText)

        assertEquals(62, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day18.solutionTwo(sampleText)

        assertEquals(952408144115, solutionTwo)
    }
}