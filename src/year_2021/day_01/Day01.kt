package year_2021.day_01

import readInput

object Day01 {
    /**
     * @return number of increases in depths
     */
    fun solutionOne(sonarDepthsText: List<String>): Int {
        val depths = parseDepths(sonarDepthsText)

        var increases = 0
        for (i in 0..depths.size - 2) {
            if (depths[i] < depths[i + 1]) {
                increases += 1
            }
        }
        return increases
    }

    /**
     * @return number of increases in depths with windows of 3 measurements
     */
    fun solutionTwo(sonarDepthsText: List<String>): Int {
        val depths = parseDepths(sonarDepthsText)

        var increases = 0
        for (i in 0..depths.size - 4) {
            val currentWindow = depths[i] + depths[i + 1] + depths[i + 2]
            val nextWindow = depths[i + 1] + depths[i + 2] + depths[i + 3]


            if (currentWindow < nextWindow) {
                increases += 1
            }
        }
        return increases
    }

    private fun parseDepths(sonarDepthsText: List<String>): List<Int> {
        return sonarDepthsText.map { it.toInt() }
    }
}

fun main() {
    val sonarDepthsText = readInput("year_2021/day_01/Day01.txt")

    val solutionOne = Day01.solutionOne(sonarDepthsText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day01.solutionTwo(sonarDepthsText)
    println("Solution 2: $solutionTwo")
}
