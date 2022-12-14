package year_2022.day_14

import connect
import down
import downLeft
import downRight
import readInput

sealed class DropSandResult {
    object Infinity: DropSandResult()
    data class Stopped(val at: Pair<Int, Int>): DropSandResult()
}

object Day14 {

    private val sandStartingPosition = 500 to 0

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val rockLocations = parseText(text)
        val sandLocations = mutableSetOf<Pair<Int, Int>>()

        while (true) {
            when (val dropSandResult = dropSand(sandStartingPosition, rockLocations, sandLocations)) {
                is DropSandResult.Infinity -> break
                is DropSandResult.Stopped -> sandLocations.add(dropSandResult.at)
            }
        }

        return sandLocations.size
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val rockLocations = parseText(text)
        val sandLocations = mutableSetOf<Pair<Int, Int>>()

        while (true) {
            println("dropping sand ${sandLocations.size + 1}")
            when (val dropSandResult = dropSandToFloor(sandStartingPosition, rockLocations, sandLocations)) {
                is DropSandResult.Infinity -> {
                    sandLocations.add(sandStartingPosition)
                    break
                }
                is DropSandResult.Stopped -> sandLocations.add(dropSandResult.at)
            }
        }

        return sandLocations.size
    }

    private fun parseText(text: List<String>): Set<Pair<Int, Int>> {
        return text.flatMap { line ->
            line
                .split(" -> ")
                .windowed(size = 2, step = 1)
                .flatMap { (left, right) ->
                    stringToIntPair(left).connect(stringToIntPair(right))
                }
        }.toSet()
    }

    private fun stringToIntPair(text: String): Pair<Int, Int> {
        val ints = text.split(",")
            .map { Integer.parseInt(it) }
        return ints[0] to ints[1]
    }

    private fun dropSand(from: Pair<Int, Int>, rockLocations: Set<Pair<Int, Int>>, sandLocations: Set<Pair<Int, Int>>): DropSandResult {
        if (from.down().second > rockLocations.maxOfOrNull { it.second }!!) {
            return DropSandResult.Infinity
        }

        return when {
            from.down() !in rockLocations && from.down() !in sandLocations -> return dropSand(from.down(), rockLocations, sandLocations)
            from.downLeft() !in rockLocations && from.downLeft() !in sandLocations -> return dropSand(from.downLeft(), rockLocations, sandLocations)
            from.downRight() !in rockLocations && from.downRight() !in sandLocations -> return dropSand(from.downRight(), rockLocations, sandLocations)
            else -> DropSandResult.Stopped(from)
        }
    }

    private fun dropSandToFloor(from: Pair<Int, Int>, rockLocations: Set<Pair<Int, Int>>, sandLocations: Set<Pair<Int, Int>>): DropSandResult {
        if (from.down().second > rockLocations.maxOfOrNull { it.second }!! + 1) {
            return DropSandResult.Stopped(from)
        }

        val combinedLocations = rockLocations + sandLocations
        return when {
            from.down() !in combinedLocations -> return dropSandToFloor(from.down(), rockLocations, sandLocations)
            from.downLeft() !in combinedLocations -> return dropSandToFloor(from.downLeft(), rockLocations, sandLocations)
            from.downRight() !in combinedLocations -> return dropSandToFloor(from.downRight(), rockLocations, sandLocations)
            from == sandStartingPosition -> DropSandResult.Infinity
            else -> DropSandResult.Stopped(from)
        }
    }

    private fun printBoard(rockLocations: Set<Pair<Int, Int>>, sandLocations: Set<Pair<Int, Int>>) {
        val maxY = rockLocations.maxOfOrNull { it.second }!! + 1
        val minX = rockLocations.minOfOrNull { it.first }!! - maxY
        val maxX = rockLocations.maxOfOrNull { it.first }!! + maxY

        for (y in  0..maxY) {
            for (x in minX..maxX) {
                if (x to y == sandStartingPosition) {
                    print("+")
                } else if (x to y in rockLocations) {
                    print("#")
                } else if (x to y in sandLocations) {
                    print("o")
                } else {
                    print(".")
                }
            }
            println("")
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_14/Day14.txt")

    val solutionOne = Day14.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day14.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}