package year_2024.day_03

import readInput

object Day03 {

    val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

    fun solutionOne(corruptedMemory: String): Int {
        return regex.findAll(corruptedMemory)
            .sumOf { matchResult ->
                val (x, y) = matchResult.destructured
                x.toInt() * y.toInt()
            }
    }

    fun solutionTwo(corruptedMemory: String): Int {
        val regex = Regex("""don't\(\).*?do\(\)""")
        val enabledMemory = corruptedMemory.replace(regex, "-")

        return solutionOne(enabledMemory)
    }
}

fun main() {
    val corruptedMemory = readInput("year_2024/day_03/Day03.txt").joinToString("-")

    val solutionOne = Day03.solutionOne(corruptedMemory)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day03.solutionTwo(corruptedMemory)
    println("Solution 2: $solutionTwo")
}
