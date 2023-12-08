package year_2021.day_07

import readInput
import kotlin.math.abs

object Day07 {

    /**
     * @return
     */
    fun solutionOne(text: String): Int {
        val positions = parsePositions(text)
        val minPosition = positions.min()
        val maxPosition = positions.max()

        var minFuel = Integer.MAX_VALUE
        for (i in minPosition..maxPosition) {
            val fuel = positions.sumOf { abs(it - i) }
            if (fuel < minFuel) {
                minFuel = fuel
            }
        }

        return minFuel
    }

    /**
     * @return
     */
    fun solutionTwo(text: String): Int {
        val positions = parsePositions(text)
        val minPosition = positions.min()
        val maxPosition = positions.max()

        var minFuel = Integer.MAX_VALUE
        for (i in minPosition..maxPosition) {
            val fuel = positions.sumOf {
                val n = abs(it - i)
                var sum = 0
                for (i in 1..n) {
                    sum += i
                }
                sum
            }
            if (fuel < minFuel) {
                minFuel = fuel
            }
        }

        return minFuel
    }

    private fun parsePositions(text: String): List<Int> {
        return text.split(",").map { Integer.parseInt(it) }.sorted()
    }
}

fun main() {
    val inputText = readInput("year_2021/day_07/Day08.txt").first()

    val solutionOne = Day07.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day07.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}