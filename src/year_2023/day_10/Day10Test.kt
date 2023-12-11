package year_2023.day_10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {

    @Test
    fun testSolutionOne_ExampleOne() {
        val sampleText = listOf(
            "-L|F7",
            "7S-7|",
            "L|7||",
            "-L-J|",
            "L|-JF",
        )

        val solutionOne = Day10.solutionOne(sampleText)
        assertEquals(4, solutionOne)
    }

    @Test
    fun testSolutionOne_ExampleTwo() {
        val sampleText = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ...",
        )

        val solutionOne = Day10.solutionOne(sampleText)
        assertEquals(8, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val sampleText = listOf(
            "...........",
            ".S-------7.",
            ".|F-----7|.",
            ".||.....||.",
            ".||.....||.",
            ".|L-7.F-J|.",
            ".|..|.|..|.",
            ".L--J.L--J.",
            "...........",
        )
        val solutionTwo = Day10.solutionTwo(sampleText)

        assertEquals(4, solutionTwo)
    }
}