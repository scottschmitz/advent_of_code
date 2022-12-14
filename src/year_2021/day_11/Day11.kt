package year_2021.day_11

import readInput
import util.neighbors
import java.awt.font.ImageGraphicAttribute

enum class FlashAttemptResult {
    IGNORED,
    INCREASED,
    FLASHED,
    ;
}

data class Octopus(
    var charge: Int
) {
    var hasFlashedThisRound: Boolean = false
        private set

    fun attemptFlash(): FlashAttemptResult {
        if (hasFlashedThisRound) {
            return FlashAttemptResult.IGNORED
        }

        charge += 1
        if (charge > 9) {
            hasFlashedThisRound = true
            charge = 0
            return FlashAttemptResult.FLASHED
        }
        return FlashAttemptResult.INCREASED
    }

    fun resetFlash() {
        hasFlashedThisRound = false
    }
}

object Day11 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val map = parseText(text)
        var flashCount = 0

        for (round in 1..100) {
            val toEvaluate = map.keys.toMutableList()
            map.values.forEach { it.resetFlash() }

            while (toEvaluate.isNotEmpty()) {
                val position = toEvaluate.removeFirst()
                if (map[position]?.attemptFlash() == FlashAttemptResult.FLASHED) {
                    toEvaluate.addAll(position.neighbors(includeDiagonals = true))
                }
            }

            val numFlashes = map.values.count { it.hasFlashedThisRound }
            flashCount += numFlashes
        }

        return flashCount
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val map = parseText(text)
        var round = 1
        while(true) {
            val toEvaluate = map.keys.toMutableList()
            map.values.forEach { it.resetFlash() }

            while (toEvaluate.isNotEmpty()) {
                val position = toEvaluate.removeFirst()
                if (map[position]?.attemptFlash() == FlashAttemptResult.FLASHED) {
                    toEvaluate.addAll(position.neighbors(includeDiagonals = true))
                }
            }

            if (map.values.all { it.hasFlashedThisRound }) {
                return round
            }
            round += 1
        }
    }

    private fun parseText(text: List<String>): Map<Pair<Int, Int>, Octopus> {
        val map = mutableMapOf<Pair<Int, Int>, Octopus>()
        text.forEachIndexed { y, row ->
            row.forEachIndexed { x, num ->
                map[x to y] = Octopus(Integer.parseInt("$num"))
            }
        }
        return map
    }
}

fun main() {
    val inputText = readInput("year_2021/day_11/Day11.txt")

    val solutionOne = Day11.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day11.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}