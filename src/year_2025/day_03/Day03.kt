package year_2025.day_03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay

class Day03 : BaseDay<List<String>, Int, Long>("year_2025/day_03/Day03.txt") {

    override fun processFile(input: List<String>): List<String> = input

    override fun solutionOne(input: List<String>): Int {
        return input.sumOf { line ->
            val nums = line.map { it.toString().toInt() }
            largestNumOfXDigits(2, nums, emptyList()).joinToString(separator = "").toInt()
        }
    }

    override fun solutionTwo(input: List<String>): Long {
        return input.sumOf { line ->
            val nums = line.map { it.toString().toInt() }
            largestNumOfXDigits(12, nums, emptyList()).joinToString(separator = "").toLong()
        }
    }

    private fun largestNumOfXDigits(
        numDigits: Int,
        availableNumbers: List<Int>,
        currentNumbers: List<Int>,
    ): List<Int> {
        return if (numDigits == 0) {
          currentNumbers
        } else if (availableNumbers.size <= numDigits) {
            currentNumbers + availableNumbers
        } else {
            val (max, maxIndex) = largestInList(availableNumbers.dropLast(numDigits - 1))
            largestNumOfXDigits(
                numDigits = numDigits - 1,
                availableNumbers = availableNumbers.drop(maxIndex + 1),
                currentNumbers = currentNumbers + max
            )
        }
    }

    private fun largestInList(nums: List<Int>): Pair<Int, Int> {
        val max = nums.max()
        return max to nums.indexOfFirst { it == max }
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "987654321111111",
        "811111111111119",
        "234234234234278",
        "818181911112111",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(357, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(3121910778619L, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}