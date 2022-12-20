package year_2022.day_20

import readInput

class Day20(input: List<String>) {

    private val encryptedData = parseInput(input)

    /**
     * @return
     */
    fun solutionOne(): Long {
        val mutable = encryptedData.toMutableList()
        encryptedData.forEach { (initialIndex, value) ->
            val currentIndex = mutable.indexOfFirst { (originalIndex, _) -> initialIndex == originalIndex }
            mutable.removeAt(currentIndex)

            var newPosition = (currentIndex + value) % (encryptedData.size -1)
            if (newPosition <= 0) {
                newPosition += encryptedData.size - 1
            }

            mutable.add(newPosition.toInt(), initialIndex to value)
        }

        val indexOfZero = mutable.indexOfFirst { (_, value) -> value == 0L }
        val a = mutable[(indexOfZero + 1_000) % encryptedData.size].second
        val b = mutable[(indexOfZero + 2_000) % encryptedData.size].second
        val c = mutable[(indexOfZero + 3_000) % encryptedData.size].second

        return a + b + c
    }

    /**
     * @return
     */
    fun solutionTwo(): Long {
        val mutable = encryptedData
                .map { it.first to it.second * 811589153 }
                .toMutableList()

        for (i in 1..10) {
            encryptedData.forEach { (initialIndex, _) ->
                val currentIndex = mutable.indexOfFirst { (originalIndex, _) -> initialIndex == originalIndex }
                val removed = mutable.removeAt(currentIndex)

                var newPosition = (currentIndex + removed.second) % (encryptedData.size - 1)
                if (newPosition <= 0) {
                    newPosition += encryptedData.size - 1
                }

                mutable.add(newPosition.toInt(), initialIndex to removed.second)
            }
        }

        val indexOfZero = mutable.indexOfFirst { (_, value) -> value == 0L }
        val a = mutable[(indexOfZero + 1_000) % encryptedData.size].second
        val b = mutable[(indexOfZero + 2_000) % encryptedData.size].second
        val c = mutable[(indexOfZero + 3_000) % encryptedData.size].second

        return a + b + c
    }

    private fun parseInput(text: List<String>): List<Pair<Int, Long>> {
        return text.mapIndexed { index, string -> index to Integer.parseInt(string).toLong() }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_20/Day20.txt")
    val day20 = Day20(inputText)

    val solutionOne = day20.solutionOne()
    println("Solution 1: $solutionOne")

    val solutionTwo = day20.solutionTwo()
    println("Solution 2: $solutionTwo")
}