package year_2024.day_04

import readInput
import util.*

object Day04 {
    fun solutionOne(text: List<String>): Int {
        return Direction.values().flatMap { direction ->
            search(text, direction)
        }.count()
    }

    fun solutionTwo(text: List<String>): Int {
        // find the point for all of the A in text
        val aPoints = text.mapIndexed { y, row ->
            row.mapIndexedNotNull { x, char ->
                if (char == 'A') Point(x, y) else null
            }
        }.flatten()

        val solutions = mutableListOf<List<Point>>()
        aPoints.forEach { aPoint ->
            val upperLeft = aPoint.plus(Direction.NORTH_WEST.delta)
            val upperRight = aPoint.plus(Direction.NORTH_EAST.delta)
            val lowerLeft = aPoint.plus(Direction.SOUTH_WEST.delta)
            val lowerRight = aPoint.plus(Direction.SOUTH_EAST.delta)

            try {
                val upperLeftLetter = text[upperLeft.y()][upperLeft.x()]
                val upperRightLetter = text[upperRight.y()][upperRight.x()]
                val lowerLeftLetter = text[lowerLeft.y()][lowerLeft.x()]
                val lowerRightLetter = text[lowerRight.y()][lowerRight.x()]

                // check
                // M.M
                // .A.
                // S.S
                if (upperLeftLetter == 'M' && upperRightLetter == 'M' && lowerLeftLetter == 'S' && lowerRightLetter == 'S') {
                    solutions.add(listOf(upperLeft, aPoint, upperRight, lowerLeft, lowerRight))
                }

                // check
                // S.S
                // .A.
                // M.M
                if (upperLeftLetter == 'S' && upperRightLetter == 'S' && lowerLeftLetter == 'M' && lowerRightLetter == 'M') {
                    solutions.add(listOf(upperLeft, aPoint, upperRight, lowerLeft, lowerRight))
                }

                // check
                // M.S
                // .A.
                // M.S
                if (upperLeftLetter == 'M' && upperRightLetter == 'S' && lowerLeftLetter == 'M' && lowerRightLetter == 'S') {
                    solutions.add(listOf(upperLeft, aPoint, upperRight, lowerLeft, lowerRight))
                }

                // check
                // S.M
                // .A.
                // S.M
                if (upperLeftLetter == 'S' && upperRightLetter == 'M' && lowerLeftLetter == 'S' && lowerRightLetter == 'M') {
                    solutions.add(listOf(upperLeft, aPoint, upperRight, lowerLeft, lowerRight))
                }
            } catch (e: IndexOutOfBoundsException) {
            }
        }

        return solutions.count()
    }

    private fun search(wordsearch: List<String>, direction: Direction): List<List<Point>> {
        // iterate through the wordsearch looking in the direction to see if it spells the word XMAS,
        // if it does add it to a list of found words
        val solutions = mutableListOf<List<Point>>()
        for (y in wordsearch.indices) {
            for (x in wordsearch[y].indices) {
                try {
                    val xPos = Point(x, y)
                    if (wordsearch[xPos.y()][xPos.x()] != 'X') {
                        continue
                    }

                    val mPos = Point(x, y).plus(direction.delta)
                    if (wordsearch[mPos.y()][mPos.x()] != 'M') {
                        continue
                    }

                    val aPos = mPos.plus(direction.delta)
                    if (wordsearch[aPos.y()][aPos.x()] != 'A') {
                        continue
                    }

                    val sPos = aPos.plus(direction.delta)
                    if (wordsearch[sPos.y()][sPos.x()] != 'S') {
                        continue
                    }

                    solutions.add(listOf(xPos, mPos, aPos, sPos))
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }
            }
        }

        return solutions
    }
}

fun main() {
    val text = readInput("year_2024/day_04/Day04.txt")

    val solutionOne = Day04.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day04.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
