package year_2022.day_06

import readInput

object Day06 {

    /**
     * @return first position with 4 unique characters
     */
    fun solutionOne(text: String): Int {
        return findFirstUniqueRange(4, text)
    }

    /**
     * @return first position with 14 unique characters
     */
    fun solutionTwo(text: String): Int {
        return findFirstUniqueRange(14, text)
    }

    private fun findFirstUniqueRange(numberCharacters: Int, text: String): Int {
        for (i in numberCharacters until text.length) {
            if (text.substring(i - numberCharacters, i).toSet().size == numberCharacters) {
                return i
            }
        }

        return -1
    }
}

fun main() {
    val inputText = readInput("year_2022/day_06/Day06.txt")

    val solutionOne = Day06.solutionOne(inputText.first())
    println("Solution 1: $solutionOne")

    val solutionTwo = Day06.solutionTwo(inputText.first())
    println("Solution 2: $solutionTwo")
}