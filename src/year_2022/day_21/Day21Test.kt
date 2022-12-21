package year_2022.day_21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day21Test {

    private val sampleText = listOf(
        "root: pppw + sjmn",
        "dbpl: 5",
        "cczh: sllz + lgvd",
        "zczc: 2",
        "ptdq: humn - dvpt",
        "dvpt: 3",
        "lfqf: 4",
        "humn: 5",
        "ljgn: 2",
        "sjmn: drzm * dbpl",
        "sllz: 4",
        "pppw: cczh / lfqf",
        "lgvd: ljgn * ptdq",
        "drzm: hmdt - zczc",
        "hmdt: 32",
    )

    @Test
    fun testSolutionOne() {
        val solutionOne = Day21(sampleText).solutionOne()

        assertEquals(152, solutionOne)
    }

    @Test
    fun testSolutionTwo() {
        val solutionTwo = Day21(sampleText).solutionTwo()

        assertEquals(301, solutionTwo)
    }
}