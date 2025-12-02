package year_2025.day_02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import kotlin.math.exp

class Day02 : BaseDay<List<Day02.IdRange>, Long, Long>("year_2025/day_02/Day02.txt") {

//    val invalidIds = mutableListOf<Long>()

    override fun processFile(input: List<String>): List<Day02.IdRange> {
        return input.flatMap { line ->
            val ranges = line.split(",")
            ranges.mapNotNull { range ->
                when (range.isEmpty()) {
                    true -> null
                    else -> {
                        val splitRange = range.split("-")
                        IdRange(first = splitRange.first().toLong(), last = splitRange.last().toLong())
                    }
                }
            }
        }
    }

    /* Sum of all the invalid ids within the range */
    override fun solutionOne(input: List<IdRange>): Long {
        return input.sumOf { idRange ->
            val invalidInRange = mutableListOf<Long>()
            for (i in idRange.first .. idRange.last) {
                val string = i.toString()
                val mid = string.length / 2
                if (string.take(mid) == string.substring(mid)) {
                    invalidInRange.add(i)
                }
            }
            invalidInRange.sumOf { it }
        }
    }

    override fun solutionTwo(input: List<IdRange>): Long {
        return input.sumOf { idRange ->
            val invalidInRange = mutableListOf<Long>()
            for (i in idRange.first .. idRange.last) {
                val string = i.toString()
                val stringLength = string.length

                for (length in 1..(stringLength / 2)) {
                    if (stringLength % length > 0) continue

                    val pattern = string.take(length)
                    val expected = buildString {
                        repeat(stringLength / length) {
                            append(pattern)
                        }
                    }

                    if (string == expected) {
                        invalidInRange.add(i)
                        break
                    }
                }
            }
            invalidInRange.sumOf { it }
        }
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "11-22,",
        "95-115,",
        "998-1012,",
        "1188511880-1188511890,",
        "222220-222224,",
        "1698522-1698528,",
        "446443-446449,",
        "38593856-38593862,",
        "565653-565659,",
        "824824821-824824827,",
        "2121212118-2121212124",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(1227775554, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(4174379265, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>

    data class IdRange(val first: Long, val last: Long)
}