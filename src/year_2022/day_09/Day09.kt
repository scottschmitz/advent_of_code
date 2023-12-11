package year_2022.day_09

import readInput
import kotlin.math.abs

enum class Direction(val letter: String, val xMod: Int, val yMod: Int) {
    UP("U", 0, 1),
    DOWN("D", 0, -1),
    LEFT("L", -1, 0),
    RIGHT("R", 1, 0),
    ;
}

data class Movement(
    val direction: Direction,
    val quantity: Int
)

data class Position(
    val x: Int,
    val y: Int
) {
    fun isTouching(other: Position): Boolean {
        return abs(x - other.x) < 2 && abs(y - other.y) < 2
    }

    fun calculatePositionToTouch(other: Position): Position {
        var xMod = 0
        var yMod = 0

        if (x == other.x) {
            // same x so need to move up/down
            yMod = if (y < other.y) 1 else -1
        } else if (y == other.y) {
            // same y so need to move left/right
            xMod = if (x < other.x) 1 else -1
        } else {
            // need to move on a diagonal to catch up
            xMod = if (x < other.x) 1 else -1
            yMod = if (y < other.y) 1 else -1
        }

        return Position(
            x = x + xMod,
            y = y + yMod
        )
    }
}

object Day09 {

    /**
     * @return unique placements of the tail if 2 ropes
     */
    fun solutionOne(text: List<String>): Int {
        val ropesCount = 2
        val movements = parseMovements(text)
        return calculateRopeMotion(ropesCount, movements).toSet().size
    }

    /**
     * @return unique placements of the tail if 10 ropes
     */
    fun solutionTwo(text: List<String>): Int {
        val ropesCount = 10
        val movements = parseMovements(text)
        return calculateRopeMotion(ropesCount, movements).toSet().size
    }

    private fun parseMovements(text: List<String>): List<Movement> {
        return text.map { line ->
            val split = line.split(" ")
            val direction = Direction.values().first { direction -> direction.letter == split[0] }
            val quantity = Integer.parseInt(split[1])

            Movement(direction, quantity)
        }
    }

    /**
     * @return the path of the tail
     */
    private fun calculateRopeMotion(ropeCount: Int, movements: List<Movement>): List<Position> {
        val startingPosition = Position(0, 0)

        val ropes = mutableListOf<MutableList<Position>>()
        for (i in 0 until ropeCount) {
            ropes.add(mutableListOf(startingPosition))
        }

        movements.forEach { movement ->
            for (i in 0 until movement.quantity) {
                val currentHead = ropes[0].last()
                val newHead = Position(
                    x = currentHead.x + movement.direction.xMod,
                    y = currentHead.y + movement.direction.yMod
                )
                ropes[0].add(newHead)

                for (j in 1 until ropes.size) {
                    val leadingRopePos = ropes[j-1].last()
                    val ropePos = ropes[j].last()

                    if (!ropePos.isTouching(leadingRopePos)) {
                        val newRopePos = ropePos.calculatePositionToTouch(leadingRopePos)
                        ropes[j].add(newRopePos)
                    }
                }
            }
        }

        return ropes.last()
    }
}

fun main() {
    val inputText = readInput("year_2022/day_09/Day10.txt")

    val solutionOne = Day09.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day09.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}