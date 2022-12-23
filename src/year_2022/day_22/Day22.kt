package year_2022.day_22

import readInput
import util.*
import kotlin.IllegalArgumentException
import kotlin.math.abs

typealias BoardPositions = Map<Point, Boolean>

sealed class Movement {
    data class Move(val steps: Int): Movement()
    data class Turn(val direction: TurnDirection): Movement()
}

enum class TurnDirection {
    RIGHT,
    LEFT,
    ;
}

enum class Facing(val value: Int) {
    RIGHT(0),
    DOWN(1),
    LEFT(2),
    UP(3),
    ;

    fun turn(direction: TurnDirection): Facing {
        return when (direction) {
            TurnDirection.RIGHT -> when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
            TurnDirection.LEFT -> {
                when (this) {
                    UP -> LEFT
                    RIGHT -> UP
                    DOWN -> RIGHT
                    LEFT -> DOWN
                }
            }
        }
    }
}

class Day22(text: List<String>) {

    private val board: BoardPositions
    private val movements: List<Movement>

    init {
        val (board, movements) = parseText(text)
        this.board = board
        this.movements = movements
    }
    /**
     * @return
     */
    fun solutionOne(): Int {
        var currentFacing = Facing.RIGHT
        var currPoint = board
                .filter { it.key.second == 0 }
                .filter { it.value }
                .keys
                .minBy { it.first }


        movements.forEach { movement ->
            when (movement) {
                is Movement.Turn -> {
                    val newFacing = currentFacing.turn(movement.direction)
                    println("Turning ${movement.direction} and now facing $newFacing")
                    currentFacing = currentFacing.turn(movement.direction)
                }
                is Movement.Move -> {
                    println("Moving ${movement.steps} steps")
                    for (step in 1..movement.steps) {
                        var potentialPoint = when (currentFacing) {
                            Facing.UP ->    currPoint.first     to currPoint.second - 1
                            Facing.DOWN ->  currPoint.first     to currPoint.second + 1
                            Facing.RIGHT -> currPoint.first + 1 to currPoint.second
                            Facing.LEFT ->  currPoint.first - 1 to currPoint.second
                        }

                        // if not on the board find the wrap around
                        if (board[potentialPoint] == null) {
                            potentialPoint = findWrapAround(currPoint, currentFacing)
                        }

                        // if the spot is open, move there (it should exist in the map)
                        if (board[potentialPoint] == null) {
                            throw IllegalArgumentException("WHAT HAVE I DONE")
                        }
                        if (board[potentialPoint] == true) {
//                            println("Moving to $potentialPoint")
                            currPoint = potentialPoint
                        }
                    }

//                    println("Arrived at $currPoint")
                }
            }
        }

        // The final password is the sum of 1000 times the row, 4 times the column, and the facing.
        return (1_000 * (currPoint.second + 1)) + (4 * (currPoint.first + 1) ) + currentFacing.value
    }

    /**
     * @return
     */
    fun solutionTwo(): Int {
        var currentFacing = Facing.RIGHT
        var currPoint = board
                .filter { it.key.second == 0 }
                .filter { it.value }
                .keys
                .minBy { it.first }


        movements.forEach { movement ->
            when (movement) {
                is Movement.Turn -> {
                    val newFacing = currentFacing.turn(movement.direction)
//                    println("Turning ${movement.direction} and now facing $newFacing")
                    currentFacing = currentFacing.turn(movement.direction)
                }
                is Movement.Move -> {
//                    println("Moving ${movement.steps} steps")
                    for (step in 1..movement.steps) {
                        var potentialFacing = currentFacing
                        var potentialPoint = when (currentFacing) {
                            Facing.UP ->    currPoint.first     to currPoint.second - 1
                            Facing.DOWN ->  currPoint.first     to currPoint.second + 1
                            Facing.RIGHT -> currPoint.first + 1 to currPoint.second
                            Facing.LEFT ->  currPoint.first - 1 to currPoint.second
                        }

                        if (whichFace(potentialPoint) != whichFace(currPoint)) {
                            val result = wrapCubeToPointAndFacing(currPoint, currentFacing)
                            println("Wrapping cube... " +
                                    "from ${whichFace((currPoint))} to ${whichFace(result.first)}" +
                                    "... was heading $currentFacing but now facing ${result.second}")
                            potentialPoint = result.first
                            potentialFacing = result.second
                        }

                        // if the spot is open, move there (it should exist in the map)
                        if (board[potentialPoint] == true) {
                            currPoint = potentialPoint
                            currentFacing = potentialFacing
                        }
                    }

//                    println("Arrived at $currPoint")
                }
            }
        }

        // The final password is the sum of 1000 times the row, 4 times the column, and the facing.
        return (1_000 * (currPoint.second + 1)) + (4 * (currPoint.first + 1) ) + currentFacing.value
    }

    private fun parseText(text: List<String>): Pair<BoardPositions, List<Movement>> {
        val positions = text
            .subList(0, text.size - 2)
            .flatMapIndexed { row, line ->
                line.mapIndexedNotNull { col, char ->
                    when (char) {
                        '.' -> Point(col, row) to true
                        '#' -> Point(col, row) to false
                        else -> { null }
                    }
                }
            }.associate { it.first to it.second }

        var builder = ""
        val movements = mutableListOf<Movement>()
        text.last().forEach { char ->
            if (char.isLetter()) {
                val turnMovement = when (char) {
                    'R' -> Movement.Turn(TurnDirection.RIGHT)
                    'L' -> Movement.Turn(TurnDirection.LEFT)
                    else -> throw IllegalArgumentException("Unknown letter: $char")
                }

                if (builder.isNotEmpty()) {
                    movements.add(Movement.Move(Integer.parseInt(builder)))
                }

                movements.add(turnMovement)
                builder = ""
            } else {
                builder += char
            }
        }

        if (builder.isNotEmpty()) {
            movements.add(Movement.Move(Integer.parseInt(builder)))
        }

        return positions to movements
    }

    private fun findWrapAround(point: Point, facing: Facing): Point {
        var result = point

        when (facing) {
            Facing.UP -> {
                var checkDown = point.down()
                while(board.contains(checkDown)) {
                    result = checkDown
                    checkDown = checkDown.down()
                }
            }
            Facing.RIGHT -> {
                var checkLeft = point.left()
                while(board.contains(checkLeft)) {
                    result = checkLeft
                    checkLeft = checkLeft.left()
                }
            }
            Facing.DOWN -> {
                var checkUp = point.up()
                while(board.contains(checkUp)) {
                    result = checkUp
                    checkUp = checkUp.up()
                }
            }
            Facing.LEFT -> {
                var checkRight = point.right()
                while(board.contains(checkRight)) {
                    result = checkRight
                    checkRight = checkRight.right()
                }
            }
        }

        return result
    }

    /**
     * Face 1 = (100,   0) -> (149,  49)
     * Face 2 = ( 50,  50) -> (149,  99)
     * Face 3 = (  0, 100) -> ( 49, 149)
     * Face 4 = ( 50, 100) -> ( 99, 149)
     * Face 5 = (  0, 150) -> ( 49, 199)
     */
    private fun whichFace(point: Point): Int {
        return when {
            point.first < 100 && point.second < 50 -> 0
            point.second < 50 -> 1
            point.second < 100 -> 2
            point.first < 50 && point.second < 150 -> 3
            point.second < 150 -> 4
            else -> 5
        }
    }

    private fun wrapCubeToPointAndFacing(point: Point, currentFacing: Facing): Pair<Point, Facing> {
        return when(whichFace(point)) {
            0 -> {
                when (currentFacing) {
                    Facing.RIGHT -> point.right() to Facing.RIGHT // continue on Face 1
                    Facing.DOWN -> point.down() to Facing.DOWN // continue on Face 2
                    Facing.LEFT -> (0 to 149 - point.second) to Facing.RIGHT // move to Face 3... Facing has now changed
                    Facing.UP -> (0 to 149 + point.first - 50) to Facing.RIGHT // move to Face 5... Facing has now changed
                }
            }
            1 -> {
                when (currentFacing) {
                    Facing.RIGHT -> (99 to 149 - point.second) to Facing.LEFT // Continue on 4
                    Facing.DOWN -> (99 to point.first - 50) to Facing.LEFT
                    Facing.LEFT -> point.left() to Facing.LEFT
                    Facing.UP -> (point.first - 100 to 149) to Facing.UP
                }
            }
            2 -> {
                when (currentFacing) {
                    Facing.RIGHT -> (point.second + 50 to 49) to Facing.UP // 1
                    Facing.DOWN -> point.down() to Facing.DOWN // 0
                    Facing.LEFT -> (point.second - 50 to 100) to Facing.DOWN // 3
                    Facing.UP -> point.up() to Facing.UP // 4
                }
            }
            3 -> {
                when (currentFacing) {
                    Facing.RIGHT -> point.right() to Facing.RIGHT // 4
                    Facing.DOWN -> point.down() to Facing.DOWN // 5
                    Facing.LEFT -> (50 to abs(point.second - 149)) to Facing.RIGHT // 0
                    Facing.UP -> (50 to 50 + point.first) to Facing.RIGHT // 2
                }
            }
            4 -> {
                when (currentFacing) {
                    Facing.RIGHT -> (149 to abs(point.second - 149)) to Facing.LEFT // 1
                    Facing.DOWN -> (49 to 100 + point.first) to Facing.LEFT // 5
                    Facing.LEFT -> point.left() to Facing.LEFT // 3
                    Facing.UP -> point.up() to Facing.UP //2
                }
            }
            5 -> {
                when (currentFacing) {
                    Facing.RIGHT -> (point.second - 199 to 149) to Facing.UP // 4
                    Facing.DOWN -> (point.first - 100 to 0) to Facing.DOWN // 1
                    Facing.LEFT -> (point.second - 100 to 0) to Facing.DOWN // 0
                    Facing.UP -> point.up() to Facing.UP // 3
                }
            }
            else -> throw IllegalArgumentException("Unknown face")
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_22/Day22.txt")
    val day22 = Day22(inputText)

//    println("Solution 1: ${day22.solutionOne()}")
    println("Solution 2: ${day22.solutionTwo()}")
}