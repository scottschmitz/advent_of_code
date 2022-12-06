package year_2022.day_06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun testSolutionOne() {
        assertEquals(7, Day06.solutionOne("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, Day06.solutionOne("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, Day06.solutionOne("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, Day06.solutionOne("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, Day06.solutionOne("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun testSolutionTwo() {
        assertEquals(19, Day06.solutionTwo("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, Day06.solutionTwo("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, Day06.solutionTwo("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, Day06.solutionTwo("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, Day06.solutionTwo("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}