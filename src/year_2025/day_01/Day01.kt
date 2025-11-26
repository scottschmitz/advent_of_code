package year_2025.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay

class Day01(): BaseDay<List<String>, Int, Int>("year_2025/day_01/Day01.txt") {
    override fun processFile(input: List<String>): List<String> = input

    override fun solutionOne(input: List<String>): Int {
        return -1
    }

    override fun solutionTwo(input: List<String>): Int {
        return -1
    }

    //<editor-fold desc="Tests">
    private val sampleText = listOf<String>(
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(1, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        val realInput = processFile(parsedInput)
        assertEquals(1, solutionOne(realInput))
    }
    //</editor-fold>
}
