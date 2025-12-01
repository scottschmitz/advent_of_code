package year_2025.day_01

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import kotlin.math.abs
import kotlin.math.sign

class Day01(): BaseDay<List<Int>, Int, Int>("year_2025/day_01/Day01.txt") {
    override fun processFile(input: List<String>): List<Int> {
        return input.map {
            var value = it.substring(1).toInt()
            if (it.first().equals('L', ignoreCase = true)) {
                 value *= -1
            }
            value
        }
    }

    override fun solutionOne(input: List<Int>): Int {
        var timesAtZero = 0

        var current = 50
        input.forEach {
            current += it
            if (current % 100 == 0) {
                timesAtZero += 1
            }
        }
        return timesAtZero
    }

    override fun solutionTwo(input: List<Int>): Int {
        var timesAtZero = 0

        var current = 50
        input.forEach { step ->

            val dist = abs(step)
            val dir = step.sign

            val fullRotations = dist / 100
            val remainder = dist % 100

            var extra = 0
            if (dir > 0) {
                if (current > 0 && current + remainder >= 100) {
                    // wrap around
                    extra = 1
                }
            } else {
                if (current in 1..remainder) {
                    // backwards past 0
                    extra = 1
                }
            }

            timesAtZero += fullRotations + extra

            current = (current + step) % 100
            if (current < 0) {
                current += 100
            }
        }
        return timesAtZero
    }

    //<editor-fold desc="Tests">
    private val sampleText = listOf<String>(
        "L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R14",
        "L82",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(3, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: ${solutionOne(parsedInput)}")
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(6, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: ${solutionTwo(parsedInput)}")
    }
    //</editor-fold>
}
