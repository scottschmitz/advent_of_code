package year_2023.day_14

import readInput
import util.*
import javax.xml.transform.Source


data class TheField(
    val verticalSize: Int,
    val horizontalSize: Int,
    val movableRocks: List<Point>,
    val immovableRocks: List<Point>
)

object Day14 {

    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val theField = parseRocks(text)

        var newLocations = theField.movableRocks.map { it }
        while (true) {
            val old = newLocations.map { it }

            newLocations = moveRocks(theField, newLocations, Direction.NORTH)

            if (old == newLocations) {
                break
            }
        }

        return newLocations.sumOf { point ->
            theField.verticalSize - point.second
        }
    }

    val mem = mutableMapOf<List<Point>, Int>()

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val theField = parseRocks(text)

        var newLocations = theField.movableRocks.map { it }

        var cycle = 0
        var total = 1_000_000_000
        var length = 0

        while (cycle < total) {
            println(cycle)
            if (mem.containsKey(newLocations)) {
                println("I remember this.")
                length = cycle - mem[newLocations]!!
                break
            } else {
                mem[newLocations] = cycle

                newLocations = moveRocks(theField, newLocations, Direction.NORTH)
                newLocations = moveRocks(theField, newLocations, Direction.WEST)
                newLocations = moveRocks(theField, newLocations, Direction.SOUTH)
                newLocations = moveRocks(theField, newLocations, Direction.EAST)
                cycle += 1
            }
        }

        if (length > 0) {
            val remainingCycles = (total - cycle) % length
            for (i in 0 until remainingCycles) {
                newLocations = moveRocks(theField, newLocations, Direction.NORTH)
                newLocations = moveRocks(theField, newLocations, Direction.WEST)
                newLocations = moveRocks(theField, newLocations, Direction.SOUTH)
                newLocations = moveRocks(theField, newLocations, Direction.EAST)
            }
        }

        val totalVerticalSize = text.size
        return newLocations.sumOf { point ->
            totalVerticalSize - point.second
        }
    }
    private fun parseRocks(text: List<String>): TheField {
        val movableRocks = mutableListOf<Point>()
        val immovableRocks = mutableListOf<Point>()


        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                when (char) {
                    'O' -> movableRocks.add(x to y)
                    '#' -> immovableRocks.add(x to y)
                }
            }
        }

        val totalVerticalSize = text.size
        val totalHorizontalSize = text.first().length

        return TheField(totalVerticalSize, totalHorizontalSize, movableRocks, immovableRocks)
    }


    private fun moveRocks(theField: TheField, currentRocks: List<Point>, direction: Direction): List<Point> {
        var anyChanges = true
        var newLocations = currentRocks.map { it }

        while(anyChanges) {
            var tempAnyChanges = false
            val tempNewLocations = mutableListOf<Point>()

            when (direction) {
                Direction.NORTH -> {
                    newLocations.sortedBy { it.second }.forEach { point ->
                        if (point.second > 0) {
                            val up = point.up()
                            if (up !in theField.immovableRocks && up !in newLocations) {
                                tempNewLocations.add(up)
                                tempAnyChanges = true
                            } else {
                                tempNewLocations.add(point)
                            }
                        } else {
                            tempNewLocations.add(point)
                        }
                    }
                }
                Direction.WEST -> {
                    newLocations.sortedBy { it.first }.forEach { point ->
                        if (point.first > 0) {
                            val left = point.left()
                            if (left !in theField.immovableRocks && left !in newLocations) {
                                tempNewLocations.add(left)
                                tempAnyChanges = true
                            } else {
                                tempNewLocations.add(point)
                            }
                        } else {
                            tempNewLocations.add(point)
                        }
                    }
                }
                Direction.SOUTH -> {
                    newLocations.sortedByDescending { it.second }.forEach { point ->
                        if (point.second < theField.verticalSize - 1) {
                            val down = point.down()
                            if (down !in theField.immovableRocks && down !in newLocations) {
                                tempNewLocations.add(down)
                                tempAnyChanges = true
                            } else {
                                tempNewLocations.add(point)
                            }
                        } else {
                            tempNewLocations.add(point)
                        }
                    }
                }
                Direction.EAST -> {
                    newLocations.sortedByDescending { it.first }.forEach { point ->
                        if (point.first < theField.horizontalSize - 1) {
                            val right = point.right()
                            if (right !in theField.immovableRocks && right !in newLocations) {
                                tempNewLocations.add(right)
                                tempAnyChanges = true
                            } else {
                                tempNewLocations.add(point)
                            }
                        } else {
                            tempNewLocations.add(point)
                        }
                    }
                }
                else -> throw IllegalArgumentException("bad direction")
            }

            newLocations = tempNewLocations
            anyChanges = tempAnyChanges
        }

        return newLocations
    }
}

fun main() {
    val text = readInput("year_2023/day_14/Day14.txt")

    val solutionOne = Day14.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day14.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
