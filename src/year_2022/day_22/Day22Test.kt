package year_2022.day_22

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day22Test {

    private val sampleText = listOf(
        "        ...#",
        "        .#..",
        "        #...",
        "        ....",
        "...#.......#",
        "........#...",
        "..#....#....",
        "..........#.",
        "        ...#....",
        "        .....#..",
        "        .#......",
        "        ......#.",
        "",
        "10R5L5R10L4R5L5",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day22(sampleText).solutionOne()

        assertEquals(6032, solutionOne)
    }
}