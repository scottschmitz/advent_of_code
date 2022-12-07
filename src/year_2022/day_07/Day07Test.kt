package year_2022.day_07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val sampleText = listOf(
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day07.solutionOne(sampleText)

        assertEquals(95437, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day07.solutionTwo(sampleText)

        assertEquals(24933642, solutionTwo)
    }
}