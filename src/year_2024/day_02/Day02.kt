package year_2024.day_02

import readInput
import kotlin.math.abs

object Day02 {
    fun solutionOne(reportsStr: List<String>): Int {
        val reports = parseReports(reportsStr)

        return reports.count { report ->
            reportIsTollerablePattern(report) && reportIsTollerableRanges(report)
        }
    }

    fun solutionTwo(reportsStr: List<String>): Int {
        val reports = parseReports(reportsStr)

        return reports.count { report ->
            tryEveryPermuation(report)
        }
    }

    private fun parseReports(reports: List<String>): List<List<Int>> {
        return reports.map { str ->
            str.split(" ").map { it.toInt() }
        }
    }

    private fun reportIsTollerablePattern(report: List<Int>): Boolean {
        val isAllIncreasing = report.zipWithNext().all { (a, b) -> a < b }
        val isAllDecreasing = report.zipWithNext().all { (a, b) -> a > b }

        return isAllIncreasing || isAllDecreasing
    }

    private fun reportIsTollerableRanges(report: List<Int>): Boolean {
        return report.zipWithNext().none { (a, b) ->
            abs(a - b) !in listOf(1, 2, 3)
        }
    }

    private fun tryEveryPermuation(report: List<Int>): Boolean {
        if (reportIsTollerablePattern(report) && reportIsTollerableRanges(report)) {
            return true
        }

        report.indices.forEach { i ->
            val updated = report.toMutableList()
            updated.removeAt(i)
            if (reportIsTollerablePattern(updated) && reportIsTollerableRanges(updated)) {
                return true
            }
        }
        return false
    }
}

fun main() {
    val reportsStr = readInput("year_2024/day_02/Day02.txt")

    val solutionOne = Day02.solutionOne(reportsStr)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day02.solutionTwo(reportsStr)
    println("Solution 2: $solutionTwo")
}
