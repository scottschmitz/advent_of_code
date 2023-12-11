package year_2023.day_09

import readInput

object Day09 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        return  text.map { line ->
            val differences = mutableListOf<List<Int>>()
            differences.add(parseHistory(line))

            while (true) {
                val difference = getLineDifferences(differences.last())
                differences.add(difference)

                if (difference.all { it == 0 }) {
                    break
                }
            }

            var previousRowAddition = 0
            for (i in differences.size - 1 downTo  0) {
                previousRowAddition += differences[i].last()
            }
            previousRowAddition
        }
            .sum()
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        return  text.map { line ->
            val differences = mutableListOf<List<Int>>()
            differences.add(parseHistory(line))

            while (true) {
                val difference = getLineDifferences(differences.last())
                differences.add(difference)

                if (difference.all { it == 0 }) {
                    break
                }
            }

            var earlierHistory = 0
            for (i in differences.size - 1 downTo  0) {
                earlierHistory = differences[i].first() - earlierHistory
            }
            earlierHistory
        }
            .sum()
    }

    private fun parseHistory(line: String): List<Int> {
        return line.split(" ").map { it.toInt() }
    }

    private fun getLineDifferences(history: List<Int>): List<Int> {
        val differences = mutableListOf<Int>()
        for (i in 0 until history.size - 1) {
            differences.add(history[i + 1] - history[i])
        }

        return differences
    }
}

fun main() {
    val text = readInput("year_2023/day_09/Day09.txt")

    val solutionOne = Day09.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day09.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
