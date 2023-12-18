package year_2023.day_18

import readInput
import util.*


interface IDig {
    val direction: Direction
    val distance: Int
}

data class DigStep(
    override val direction: Direction,
    override val distance: Int,
    val color: String
): IDig

data class UpdatedDig(
    override val direction: Direction,
    override val distance: Int
): IDig

object Day18 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Long {
        val digPlan = parseDigPlan(text)
        val vertices = dig(digPlan)
        return vertices.calculateArea(true)
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Long {
        val updatedDigPlan = parseDigPlan(text).map { oldPlan ->
            val distance = Integer.parseInt(oldPlan.color.substring(0, oldPlan.color.length - 1), 16)
            val direction = digitToDirection(oldPlan.color.last())

            UpdatedDig(
                direction =  direction,
                distance = distance
            )
        }

        val vertices = dig(updatedDigPlan)
        return vertices.calculateArea(true)
    }

    private fun parseDigPlan(text: List<String>): List<DigStep> {
        return text.map { line ->
            val split = line.split(" ")
            DigStep(
                direction = Direction.from(split[0].first()),
                distance = split[1].toInt(),
                color = split[2].substring(2, split[2].length - 1)
            )
        }
    }

    private fun dig(digPlan: List<IDig>):  List<PointL> {
        var currentPoint: PointL = 0L to 0L
        val points = mutableListOf<PointL>()

        digPlan.forEach { step ->
            currentPoint = (currentPoint.first + (step.direction.delta.first * step.distance)
              to currentPoint.second + (step.direction.delta.second * step.distance))
            points.add(currentPoint)
        }

        return points
    }

    private fun digitToDirection(digit: Char): Direction {
        return when (digit) {
            '0' -> Direction.EAST
            '1' -> Direction.SOUTH
            '2' -> Direction.WEST
            '3' -> Direction.NORTH
            else -> throw IllegalArgumentException("Unhandled direction")
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_18/Day18.txt")

    val solutionOne = Day18.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day18.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
