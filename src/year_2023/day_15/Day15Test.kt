package year_2023.day_15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day15Test {

    @Test
    fun testSolutionOneSimple() {
        val sampleText = listOf(
            "HASH",
        )

        val solutionOne = Day15.solutionOne(sampleText)

        assertEquals(52, solutionOne)
    }

    @Test
    fun testSolutionOneMultipleValues() {
        val sampleText = listOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7",
        )

        val solutionOne = Day15.solutionOne(sampleText)

        assertEquals(1320, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val sampleText = listOf(
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7",
        )
        val solutionTwo = Day15.solutionTwo(sampleText)

        assertEquals(145, solutionTwo)
    }
}