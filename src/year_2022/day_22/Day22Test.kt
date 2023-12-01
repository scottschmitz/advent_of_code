package year_2022.day_22

import readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day22Test {

    @Test
    fun testSolutionOne() {
        val sampleText = listOf(
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
        val solutionOne = Day22(sampleText).solutionOne()

        assertEquals(6032, solutionOne)
    }

    @Test
    // This test intentionally uses ACTUAL data as the sample text had cube faces in different locations...
    fun testSolutionTwo() {
        val inputText = readInput("year_2022/day_22/Day22.txt_exception")
        val solutionOne = Day22(inputText).solutionTwo(50)

        assertEquals(144012, solutionOne)
    }
}