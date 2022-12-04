package year_2021.day_03

import readInput

object Day03 {

    /**
     * @return gamma * epsilon
     */
    fun solutionOne(reports: List<String>): Int {
        val gammaText = calculateGammaBinary(reports)
        val epsilonText = calculateEpsilonBinary(reports)

        val gamma = Integer.parseInt(gammaText, 2)
        val epsilon = Integer.parseInt(epsilonText, 2)

        return gamma * epsilon
    }

    /**
     * @return the life support value
     */
    fun solutionTwo(reports: List<String>): Int {
        var gammaText = ""
        var potentialOxygenValues = reports
        while (potentialOxygenValues.size > 1) {
            val nextBits = potentialOxygenValues.map { it[gammaText.length] }
            gammaText += calculateMostCommon(nextBits)

            potentialOxygenValues = potentialOxygenValues.filter { report -> report.startsWith(gammaText) }
        }
        val oxygenBinary = potentialOxygenValues.first()
        val oxygenRating = Integer.parseInt(oxygenBinary, 2)


        var epsilonText = ""
        var potentialC02ScubberValues = reports
        while (potentialC02ScubberValues.size > 1) {
            val nextBits = potentialC02ScubberValues.map { it[epsilonText.length] }
            epsilonText += calculateLeastCommon(nextBits)

            potentialC02ScubberValues = potentialC02ScubberValues.filter { report -> report.startsWith(epsilonText) }
        }
        val c02Binary = potentialC02ScubberValues.first()
        val c02ScrubberRating = Integer.parseInt(c02Binary, 2)

        return oxygenRating * c02ScrubberRating
    }

    private fun calculateGammaBinary(report: List<String>): String {
        var gamma = ""

        for (i in 0 until report.first().length) {
            val bits = report.map { it[i] }
            gamma += calculateMostCommon(bits)
        }

        return gamma
    }

    private fun calculateEpsilonBinary(report: List<String>): String {
        var epsilon = ""

        for (i in 0 until report.first().length) {
            val bits = report.map { it[i] }
            epsilon += calculateLeastCommon(bits)
        }

        return epsilon
    }

    private fun calculateMostCommon(bits: List<Char>): Char {
        return bits
            .groupBy { it }
            .toList()
            .sortedByDescending { (char, _) -> Integer.parseInt("$char") }
            .maxByOrNull { (_, list) -> list.size }!!
            .first
    }

    private fun calculateLeastCommon(bits: List<Char>): Char {
        return bits
            .groupBy { it }
            .toList()
            .sortedBy { (char, _) -> Integer.parseInt("$char") }
            .minByOrNull { (_, list) -> list.size }!!
            .first
    }
}

fun main() {
    val inputText = readInput("year_2021/day_03/Day03.txt")

    val solutionOne = Day03.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day03.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}