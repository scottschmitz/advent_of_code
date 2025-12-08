package year_2023.day_06

import readInput
import util.product

object Day06 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Long {
        val times = text[0].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toLong() }
        val distances = text[1].split(" ").filter { it.isNotEmpty() }.drop(1).map { it.toLong() }

        return times.indices.map { round ->
            val totalTime = times[round]
            val currentDistanceRecord = distances[round]

            winCount(totalTime, currentDistanceRecord)
        }.product()
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Long {
        val times = text[0].filter { it != ' ' }.split(":").drop(1).map { it.toLong() }
        val distances = text[1].filter { it != ' ' }.split(":").drop(1).map { it.toLong() }

        return winCount(times[0], distances[0])
    }

    private fun winCount(totalTime: Long, distance: Long): Long {
        var winCount = 0L
        for (windUpTime in 1 until totalTime) {
            if (windUpTime * (totalTime-windUpTime) > distance) {
                winCount += 1
            } else if (winCount > 0) {
                break
            }
        }
        return winCount
    }
}

fun main() {
    val text = readInput("year_2023/day_06/Day06.txt")

    val solutionOne = Day06.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day06.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
