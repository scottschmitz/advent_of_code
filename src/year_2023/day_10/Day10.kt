package year_2023.day_10

import readInput
import util.*
import kotlin.math.E

enum class Direction() {
    North,
    East,
    South,
    West,
    ;

    fun inverse(): Direction {
        return when (this) {
            North -> South
            East -> West
            South -> North
            West -> East
        }
    }
}
enum class Pipe(val char: Char, val first: Direction, val second: Direction) {
    Vertical('|', Direction.North, Direction.South),
    Horizontal('-', Direction.East, Direction.West),
    Ninety_NE('L', Direction.North, Direction.East),
    Ninety_NW('J', Direction.North, Direction.West),
    Ninety_SW('7', Direction.South, Direction.West),
    Ninety_SE('F', Direction.South, Direction.East),
    ;

    companion object {
        fun fromChar(char: Char): Pipe? {
            return Pipe.values().firstOrNull { it.char == char }
        }
    }
}

data class Field(
    val startPoint: Point,
    val pipes: Map<Point, Pipe>
)

object Day10 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val field = parseField(text)
        return calculateLoopFromStart(field).size / 2
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val field = parseField(text)
        val path = calculateLoopFromStart(field)
        return path.area()
    }

    private fun parseField(text: List<String>): Field {
        var startingPoint: Point? = null
        val pipes = mutableMapOf<Point, Pipe>()
        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == 'S') {
                    startingPoint = x to y
                } else {
                    Pipe.fromChar(char)?.let { pipe ->
                        pipes[(x to y)] = pipe
                    }
                }
            }
        }

        return Field(
            startPoint = startingPoint!!,
            pipes = pipes
        )
    }

    private fun calculateLoopFromStart(field: Field): List<Point> {
        makeLoop(Direction.North, field)?.let { return it }
        makeLoop(Direction.East, field)?.let { return it }
        makeLoop(Direction.South, field)?.let { return it }
        makeLoop(Direction.West, field)?.let { return it }

        throw IllegalArgumentException("No paths")
    }
    private fun makeLoop(direction: Direction, field: Field): List<Point>? {
        var currentPoint = field.startPoint
        var currentDirection = direction

        val path = mutableListOf<Point>()
        path.add(currentPoint)

        while (true) {
            currentPoint = when (currentDirection) {
                Direction.North -> currentPoint.up()
                Direction.East -> currentPoint.right()
                Direction.South -> currentPoint.down()
                Direction.West -> currentPoint.left()
            }
            if (currentPoint == field.startPoint) {
                return path
            }

            val nextPipe = field.pipes[currentPoint] ?: return null
            currentDirection = when (currentDirection.inverse()) {
                nextPipe.first -> nextPipe.second
                nextPipe.second -> nextPipe.first
                else -> return null
            }

            path.add(currentPoint)
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_10/Day10.txt")

    val solutionOne = Day10.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day10.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
