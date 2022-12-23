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
                .filter { it.key.second == 1 }
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
        return (1_000 * (currPoint.second)) + (4 * (currPoint.first) ) + currentFacing.value
    }

    /**
     * @return
     */
    fun solutionTwo(faceSize: Int): Int {
        var currentFacing = Facing.RIGHT
        var currPoint = board
                .filter { it.key.second == 1 }
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

                        if (whichFace(potentialPoint, faceSize) != whichFace(currPoint, faceSize)) {
                            val result = wrapCubeToPointAndFacing(faceSize, currPoint, currentFacing)
                            potentialPoint = result.first
                            potentialFacing = result.second
                        }

                        if (potentialPoint !in board) {
                            throw IllegalArgumentException("Moving to unknown point!!? $potentialPoint")
                        }

                        // if the spot is open, move there (it should exist in the map)
                        if (board[potentialPoint] == true) {
                            currPoint = potentialPoint
                            currentFacing = potentialFacing
                        }
                    }
                }
            }
        }

        // The final password is the sum of 1000 times the row, 4 times the column, and the facing.
        return (1_000 * (currPoint.second)) + (4 * (currPoint.first) ) + currentFacing.value
    }

    private fun parseText(text: List<String>): Pair<BoardPositions, List<Movement>> {
        val positions = text
            .subList(0, text.size - 2)
            .flatMapIndexed { row, line ->
                line.mapIndexedNotNull { col, char ->
                    when (char) {
                        '.' -> Point(col + 1, row + 1) to true
                        '#' -> Point(col + 1, row + 1) to false
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
    private fun whichFace(point: Point, faceSize: Int): Int {
        return when {
            point.first in  (faceSize + 1)..(faceSize * 2) && point.second in   1.. faceSize -> 0
            point.first in ((faceSize * 2) + 1)..(faceSize * 3) && point.second in   1.. faceSize -> 1
            point.first in  (faceSize + 1)..(faceSize * 2) && point.second in  (faceSize + 1)..(faceSize * 2) -> 2
            point.first in   1.. (faceSize) && point.second in (faceSize * 2) + 1..(faceSize * 3) -> 3
            point.first in (faceSize + 1)..(faceSize * 2) && point.second in (faceSize * 2) + 1 .. (faceSize * 3) -> 4
            point.first in 1..(faceSize) && point.second in (faceSize * 3) + 1 .. (faceSize * 4) -> 5
            else -> -1
        }
    }

    private fun wrapCubeToPointAndFacing(faceSize: Int, point: Point, currentFacing: Facing): Pair<Point, Facing> {
        return when(whichFace(point, faceSize)) {
            0 -> {
                when (currentFacing) {
                    Facing.UP -> {
                        val col = 1 // left edge of Face 5
                        val row = point.first + (2 * faceSize) // move down 2 faces and then continue on a side turn
                        (col to row) to Facing.RIGHT // 0 -> 5 is on a sideways turn
                    }
                    Facing.RIGHT -> point.right() to Facing.RIGHT // continue on Face 1
                    Facing.DOWN -> point.down() to Facing.DOWN // continue on Face 2
                    Facing.LEFT -> {
                        val col = 1 // left edge of Face 3
                        val row = (faceSize - point.second) + (2 * faceSize) + 1 // move down 2 faces and then invert the Y (upside down)
                        (col to row) to Facing.RIGHT // 0 -> 3 is upside down so flip the facing
                    }
                }
            }
            1 -> {
                when (currentFacing) {
                    Facing.UP -> {
                        val col = point.first - (faceSize * 2)
                        val row = (faceSize * 4) // very bottom of last face
                        (col to row) to Facing.UP
                    }
                    Facing.RIGHT -> {
                        val col = (faceSize * 2) // right edge of Face 4
                        val row = (faceSize * 2) + abs(faceSize - point.second) + 1
                        (col to row) to Facing.LEFT
                    }
                    Facing.DOWN -> {
                        val col = (faceSize * 2) //right edge of
                        val row = (point.first - faceSize) // to the right edge of 2
                        (col to row) to Facing.LEFT
                    }
                    Facing.LEFT -> point.left() to Facing.LEFT
                }
            }
            2 -> {
                when (currentFacing) {
                    Facing.UP -> point.up() to Facing.UP // Continue up on Face 0
                    Facing.RIGHT -> {
                        val col = (faceSize * 2) + (point.second - faceSize)
                        val row = faceSize
                        (col to row) to Facing.UP // Rotate to up on Face 1
                    }
                    Facing.DOWN -> point.down() to Facing.DOWN // Continue down on Face 4
                    Facing.LEFT -> {
                        val col = point.second - faceSize
                        val row = (faceSize * 2) + 1
                        (col to row) to Facing.DOWN // Rotate to down 3
                    }

                }
            }
            3 -> {
                when (currentFacing) {
                    Facing.UP -> {
                        val col = (faceSize + 1)
                        val row = (faceSize + point.first)
                        (col to row) to Facing.RIGHT // Right on Face 2
                    }
                    Facing.RIGHT -> point.right() to Facing.RIGHT // Continue right on Face 4
                    Facing.DOWN -> point.down() to Facing.DOWN // Continue down on Face 5
                    Facing.LEFT -> {
                        val col = faceSize + 1
                        val row = faceSize - (point.second - (faceSize * 2)) + 1
                        (col to row) to Facing.RIGHT // Rotate to right on Face 0
                    }

                }
            }
            4 -> {
                when (currentFacing) {
                    Facing.UP -> point.up() to Facing.UP // Continue up on Face 2
                    Facing.RIGHT -> {
                        val col = faceSize * 3
                        val row = faceSize - (point.second - (faceSize * 2)) + 1
                        (col to row) to Facing.LEFT // Rotate to left on 1
                    }
                    Facing.DOWN -> {
                        val col = faceSize
                        val row = (faceSize * 2) + point.first
                        (col to row) to Facing.LEFT // Rotate to left on Face 5
                    }
                    Facing.LEFT -> point.left() to Facing.LEFT // Continue left on Face 3

                }
            }
            5 -> {
                when (currentFacing) {
                    Facing.UP -> point.up() to Facing.UP // Continue up on Face 3
                    Facing.RIGHT -> {
                        val col = point.second - (faceSize * 2)
                        val row = faceSize * 3
                        (col to row) to Facing.UP // Rotate to up on Face 4
                    }
                    Facing.DOWN -> {
                        val col = point.first + (faceSize * 2)
                        val row = 1
                        (col to row) to Facing.DOWN // Rotate to down on Face 1
                    }
                    Facing.LEFT -> {
                        val col = point.second - (faceSize * 2)
                        val row = 1
                        (col to row) to Facing.DOWN // Rotate to down on Face 0
                    }
                }
            }
            else -> throw IllegalArgumentException("Unknown face")
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_22/Day22.txt")
    val day22 = Day22(inputText)

    println("Solution 1: ${day22.solutionOne()}")
    println("Solution 2: ${day22.solutionTwo(50)}")
}