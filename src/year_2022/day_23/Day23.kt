package year_2022.day_23

import readInput
import util.*

enum class Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST,
    ;

    companion object {
        fun byOrder(order: Int): Direction {
            return when(order) {
                1 -> NORTH
                2 -> SOUTH
                3 -> WEST
                0 -> EAST
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class Day23(text: List<String>) {

    private val originalElfPositions = parseText(text)

    fun solutionOne(debug: Boolean = false): Int {
        val currentPoints = originalElfPositions.toMutableList()

        for (round in 1..10) {
            val proposals = currentPoints.map { point ->
                val proposedPosition = proposeLocation(
                    point = point,
                    priorityDirection = Direction.byOrder(round % 4),
                    currentPoints = currentPoints
                )

                point to proposedPosition
            }

            val approvedProposals = proposals
                .groupBy { it.second }
                .filter { it.value.size == 1 }
                .map { it.key }

            proposals
                .filter { (_, proposed) -> proposed in approvedProposals }
                .forEach { (current, approved) ->
                    currentPoints.remove(current)
                    currentPoints.add(approved)
                }

            if (debug) {
                println("== End of Round $round ==")

                val minX = currentPoints.minOfOrNull { it.first }!!
                val maxX = currentPoints.maxOfOrNull { it.first }!!
                val minY = currentPoints.minOfOrNull { it.second }!!
                val maxY = currentPoints.maxOfOrNull { it.second }!!

                for (row in minY..maxY) {
                    for (col in minX .. maxX) {
                        if (col to row in currentPoints) {
                            print("#")
                        } else {
                            print(".")
                        }
                    }
                    println()
                }
            }
        }

        val minX = currentPoints.minOfOrNull { it.first }!!
        val maxX = currentPoints.maxOfOrNull { it.first }!!
        val minY = currentPoints.minOfOrNull { it.second }!!
        val maxY = currentPoints.maxOfOrNull { it.second }!!

        var totalEmpty = 0
        for (row in minY..maxY) {
            for (col in minX .. maxX) {
                if (col to row !in currentPoints) {
                    totalEmpty += 1
                }
            }
        }
        return totalEmpty
    }

    fun solutionTwo(debug: Boolean = false): Int {
        val currentPoints = originalElfPositions.toMutableList()

        var round = 1
        println("Total elves: ${currentPoints.size}")

        while (true) {
            val proposals = currentPoints.map { point ->
                val proposedPosition = proposeLocation(
                        point = point,
                        priorityDirection = Direction.byOrder(round % 4),
                        currentPoints = currentPoints
                )

                point to proposedPosition
            }

            // if no one needs to move then we alllll good
            if (debug) {
                println("Round $round - moving ${proposals.count { it.first != it.second }} elves.")
            }
            if (proposals.all { it.first == it.second }) {
                break
            }

            val approvedProposals = proposals
                    .groupBy { it.second }
                    .filter { it.value.size == 1 }
                    .map { it.key }

            proposals
                    .filter { (_, proposed) -> proposed in approvedProposals }
                    .forEach { (current, approved) ->
                        currentPoints.remove(current)
                        currentPoints.add(approved)
                    }

            if (debug) {
                println("== End of Round $round ==")

                val minX = currentPoints.minOfOrNull { it.first }!!
                val maxX = currentPoints.maxOfOrNull { it.first }!!
                val minY = currentPoints.minOfOrNull { it.second }!!
                val maxY = currentPoints.maxOfOrNull { it.second }!!

                for (row in minY..maxY) {
                    for (col in minX .. maxX) {
                        if (col to row in currentPoints) {
                            print("#")
                        } else {
                            print(".")
                        }
                    }
                    println()
                }
            }

            round += 1
        }

        return round
    }

    private fun parseText(text: List<String>): List<Point> {
        return text.flatMapIndexed { row, rowString ->
            rowString.mapIndexedNotNull { col, letter ->
                if (letter == '#') {
                    Point(col, row)
                } else {
                    null
                }
            }
        }
    }

    private fun proposeLocation(point: Point, priorityDirection: Direction, currentPoints: List<Point>): Point {
        if (evaluateAlone(point, currentPoints)) {
            return point
        }

        return when (priorityDirection) {
            Direction.NORTH -> {
                if (evaluateNorth(point, currentPoints)) {
                    point.up()
                } else if (evaluateSouth(point, currentPoints)) {
                    point.down()
                } else if (evaluateWest(point, currentPoints)) {
                    point.left()
                } else if (evaluateEast(point, currentPoints)) {
                    point.right()
                } else {
                    point
                }
            }
            Direction.SOUTH -> {
                if (evaluateSouth(point, currentPoints)) {
                    point.down()
                } else if (evaluateWest(point, currentPoints)) {
                    point.left()
                } else if (evaluateEast(point, currentPoints)) {
                    point.right()
                } else if (evaluateNorth(point, currentPoints)) {
                    point.up()
                } else {
                    point
                }
            }
            Direction.WEST -> {
                if (evaluateWest(point, currentPoints)) {
                    point.left()
                } else if (evaluateEast(point, currentPoints)) {
                    point.right()
                } else if (evaluateNorth(point, currentPoints)) {
                    point.up()
                } else if (evaluateSouth(point, currentPoints)) {
                    point.down()
                } else {
                    point
                }
            }
            Direction.EAST -> {
                if (evaluateEast(point, currentPoints)) {
                    point.right()
                } else if (evaluateNorth(point, currentPoints)) {
                    point.up()
                } else if (evaluateSouth(point, currentPoints)) {
                    point.down()
                } else if (evaluateWest(point, currentPoints)) {
                    point.left()
                } else {
                    point
                }
            }
        }
    }

    private fun evaluateAlone(point: Point, currentPoints: List<Point>): Boolean {
        return point.upLeft() !in currentPoints
                && point.up() !in currentPoints
                && point.upRight() !in currentPoints
                && point.right() !in currentPoints
                && point.downRight() !in currentPoints
                && point.down() !in currentPoints
                && point.downLeft() !in currentPoints
                && point.left() !in currentPoints
    }

    private fun evaluateNorth(point: Point, currentPoints: List<Point>): Boolean  {
        return point.upLeft() !in currentPoints
            && point.up() !in currentPoints
            && point.upRight() !in currentPoints
    }

    private fun evaluateSouth(point: Point, currentPoints: List<Point>): Boolean {
        return point.downLeft() !in currentPoints
            && point.down() !in currentPoints
            && point.downRight() !in currentPoints
    }

    private fun evaluateEast(point: Point, currentPoints: List<Point>): Boolean {
        return point.upRight() !in currentPoints
            && point.right() !in currentPoints
            && point.downRight() !in currentPoints
    }

    private fun evaluateWest(point: Point, currentPoints: List<Point>): Boolean {
        return point.upLeft() !in currentPoints
            && point.left() !in currentPoints
            && point.downLeft() !in currentPoints
    }
}

fun main() {
    val inputText = readInput("year_2022/day_23/Day23.txt")
    val day = Day23(text = inputText)

    println("Solution 1: ${day.solutionOne()}")
    println("Solution 2: ${day.solutionTwo()}")
}