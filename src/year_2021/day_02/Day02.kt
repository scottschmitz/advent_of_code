package year_2021.day_02

import readInput

enum class Heading(val text: String) {
    FORWARD("forward"),
    DOWN("down"),
    UP("up"),
    ;

    companion object {
        fun fromText(text: String): Heading {
            return Heading.values().first { it.text == text }
        }
    }
}

data class Direction(
    val heading: Heading,
    val amount: Int
)

data class AimlessPosition(
    var horizontal: Int,
    var depth: Int
) {
    fun apply(direction: Direction) {
        when (direction.heading) {
            Heading.FORWARD -> horizontal += direction.amount
            Heading.UP -> depth -= direction.amount
            Heading.DOWN -> depth += direction.amount
        }
    }
}

data class AimPosition(
    var horizontal: Int,
    var depth: Int,
    var aim: Int
) {
    fun apply(direction: Direction) {
        when (direction.heading) {
            Heading.FORWARD -> {
                horizontal += direction.amount
                depth += (direction.amount * aim)
            }
            Heading.UP -> aim -= direction.amount
            Heading.DOWN -> aim += direction.amount
        }
    }
}

object Day02 {
    /**
     * Modify position by following the directions exactly
     *
     * @return the resulting horizontal * depth position
     */
    fun solutionOne(directionsText: List<String>): Int {
        val position = AimlessPosition(0, 0)
        val course = parseCourse(directionsText)

        course.forEach { direction -> position.apply(direction) }

        return position.horizontal * position.depth
    }

    /**
     * Instead of modifying depth directly, change the "aim" then move
     * the depth when moving forward by aim * forward
     *
     * @return the resulting horizontal * depth position
     */
    fun solutionTwo(directionsText: List<String>): Int {
        val position = AimPosition(0, 0, 0)
        val course = parseCourse(directionsText)

        course.forEach { direction -> position.apply(direction) }
        return position.horizontal * position.depth
    }

    private fun parseCourse(directionsText: List<String>): List<Direction> {
        return directionsText.map { direction ->
            val split = direction.split(" ")
            Direction(
                heading = Heading.fromText(split[0]),
                amount = split[1].toInt()
            )
        }
    }
}

fun main() {
    val sonarDepthsText = readInput("year_2021/day_02/Day02.txt")

    val solutionOne = Day02.solutionOne(sonarDepthsText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day02.solutionTwo(sonarDepthsText)
    println("Solution 2: $solutionTwo")
}
