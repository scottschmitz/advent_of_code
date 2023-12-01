package year_2023.day_01

import readInput

object Day01 {
    /**
     * Calculate the sum of all the calibration values
     */
    fun solutionOne(calibrationValues: List<String>): Int {
        val values = calibrationValues.map { calibration ->
            var first = '0'
            var last = '0'

            for (i in calibration.indices) {
                if (calibration[i].isDigit()) {
                    first = calibration[i]
                    break
                }
            }

            for (i in calibration.length - 1 downTo 0) {
                if (calibration[i].isDigit()) {
                    last = calibration[i]
                    break
                }
            }

            println("$first$last")
            "$first$last"
        }
        return values.sumOf { it.toInt() }
    }

    /**
     * Now strings of the words count
     */
    fun solutionTwo(calibrationValues: List<String>): Int {
        val cleaned = calibrationValues.map { calibration ->
            calibration.replace("one", "on1e")
                .replace("two", "tw2o")
                .replace("three", "thr3ee")
                .replace("four", "fou4r")
                .replace("five", "fiv5e")
                .replace("six", "si6x")
                .replace("seven", "se7ven")
                .replace("eight", "eig8ht")
                .replace("nine", "nin9e")
        }

        println(calibrationValues)
        println(cleaned)

        return solutionOne(cleaned)
    }
}

fun main() {
    val calibrationValues = readInput("year_2023/day_01/Day01.txt")

    val solutionOne = Day01.solutionOne(calibrationValues)
    println("Solution 1: Sum of all calibration: $solutionOne")

    val solutionTwo = Day01.solutionTwo(calibrationValues)
    println("Solution 2: Sum of all calibration: $solutionTwo")
}
