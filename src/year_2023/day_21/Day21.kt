package year_2023.day_21

import readInput
import util.Point
import util.neighbors
import util.x
import util.y
import kotlin.math.abs

data class Board(
    val height: Int,
    val width: Int,
    val startingLocation: Point,
    val rocks: List<Point>
)


object Day21 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val board = parseBoard(text)

        var possibleLocations = setOf(board.startingLocation)
        for (step in 0 until 64) {
            possibleLocations = possibleLocations
                .flatMap { it.neighbors() }
                .filterNot { it in board.rocks }
                .toSet()
        }

        return possibleLocations.size
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>, numberSteps: Int): Int {
        val board = parseBoard(text)

        var possibleLocations = setOf(board.startingLocation)
        for (step in 0 until numberSteps) {
            possibleLocations = possibleLocations
                .flatMap { it.neighbors() }
                .filterNot {
                    val translatedX = if (it.x() >= 0) {
                        it.x() % board.width
                    } else {
                        board.width - (abs(it.x()) % board.width)
                    }

                    val translatedY = if (it.y() >= 0) {
                        it.y() % board.height
                    } else {
                        board.height - (abs(it.y()) % board.height)
                    }

                    translatedX to translatedY in board.rocks

                }
                .toSet()
        }

        return possibleLocations.size
    }

    private fun parseBoard(text: List<String>): Board {
        var startingLocation = 0 to 0
        val rocks = mutableListOf<Point>()

        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    rocks.add(x to y)
                } else if (char == 'S') {
                    startingLocation = x to y
                }
            }
        }

        return Board(text.size, text[0].length, startingLocation, rocks)
    }
}

fun main() {
    val text = readInput("year_2023/day_21/Day21.txt")

    val solutionOne = Day21.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day21.solutionTwo(text, 26501365)
    println("Solution 2: $solutionTwo")
}
