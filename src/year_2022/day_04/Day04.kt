package year_2022.day_04

import readInput

data class Assignment(
        val elfOneSectionIds: List<Int>,
        val elfTwoSectionIds: List<Int>
) {
    val assignmentFullyContained: Boolean get() {
        return elfOneSectionIds.all { it in elfTwoSectionIds }
            || elfTwoSectionIds.all { it in elfOneSectionIds }
    }

    val assignmentsOverlap: Boolean get() {
        return elfOneSectionIds.any { it in elfTwoSectionIds }
    }
}

object Day04 {

    /**
     * @return the number of assignments fully contained inside another
     */
    fun solutionOne(text: List<String>): Int {
        return calculateAssignments(text).count { it.assignmentFullyContained }
    }

    /**
     * @return the number of assignments with any overlap
     */
    fun solutionTwo(text: List<String>): Int {
        return calculateAssignments(text).count { it.assignmentsOverlap }
    }

    private fun calculateAssignments(text: List<String>): List<Assignment> {
        return text.map { textLine ->
            val assignments = textLine.split(",")
            Assignment(
                    elfOneSectionIds = convertRangeToList(assignments[0]),
                    elfTwoSectionIds = convertRangeToList(assignments[1])
            )
        }
    }

    private fun convertRangeToList(range: String): List<Int> {
        val split = range.split("-")
        val start = split[0].toInt()
        val end = split[1].toInt()

        return (start..end).toList()
    }
}

fun main() {
    val inputText = readInput("year_2022/day_04/Day04.txt")

    val solutionOne = Day04.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day04.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}