package year_2023.day_12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day12Test {

    private val sampleText = listOf(
        "???.### 1,1,3",
        ".??..??...?##. 1,1,3",
        "?#?#?#?#?#?#?#? 1,3,1,6",
        "????.#...#... 4,1,1",
        "????.######..#####. 1,6,5",
        "?###???????? 3,2,1",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day12.solutionOne(sampleText)

        assertEquals(21, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day12.solutionTwo(sampleText)

        assertEquals(525_152, solutionTwo)
    }
}