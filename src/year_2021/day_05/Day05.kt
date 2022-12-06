package year_2021.day_05

import readInput

data class Point(
    val x: Int,
    val y: Int
)

data class LineSegment(
    val pointA: Point,
    val pointB: Point
) {
    val allHorizontalAndVerticalPoints: List<Point> get() {
        return when {
            pointA.x == pointB.x -> {
                // vertical
                val (start, end) = if (pointA.y < pointB.y) {
                    pointA to pointB
                } else { pointB to pointA }

                (start.y..end.y).map { y ->
                    Point(
                            x = pointA.x,
                            y = y
                    )
                }
            }
            pointA.y == pointB.y -> {
                // horizontal
                val (start, end) = if (pointA.x < pointB.x) {
                    pointA to pointB
                } else { pointB to pointA }

                (start.x..end.x).map { x ->
                    Point(
                            x = x,
                            y = pointA.y
                    )
                }
            }
            else -> {
                emptyList()
            }
        }
    }
    val allPoints: List<Point> get() {
        return when {
            pointA.x == pointB.x -> {
                // vertical
                val (start, end) = if (pointA.y < pointB.y) {
                    pointA to pointB
                } else { pointB to pointA }

                (start.y..end.y).map { y ->
                    Point(
                        x = pointA.x,
                        y = y
                    )
                }
            }
            pointA.y == pointB.y -> {
                // horizontal
                val (start, end) = if (pointA.x < pointB.x) {
                    pointA to pointB
                } else { pointB to pointA }

                (start.x..end.x).map { x ->
                    Point(
                        x = x,
                        y = pointA.y
                    )
                }
            }
            pointA.x > pointB.x && pointA.y > pointB.y -> {
                // left up
                (pointB.x..pointA.x).mapIndexed { index, x ->
                    Point(
                        x = x,
                        y = pointB.y + index
                    )
                }
            }
            pointA.x > pointB.x -> {
                // left down
                (pointB.x..pointA.x).mapIndexed { index, x ->
                    Point(
                        x = x,
                        y = pointB.y - index
                    )
                }
            }
            pointA.y > pointB.y -> {
                // right up
                (pointA.x..pointB.x).mapIndexed { index, x ->
                    Point(
                        x = x,
                        y = pointA.y - index
                    )
                }
            }
            else -> {
                // right down
                (pointA.x..pointB.x).mapIndexed { index, x ->
                    Point(
                        x = x,
                        y = pointA.y + index
                    )
                }
            }
        }
    }
}

object Day05 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        return parseLines(text)
            .flatMap { lineSegment -> lineSegment.allHorizontalAndVerticalPoints }
            .groupBy { it }
            .filter { (_, points) -> points.size > 1 }
            .count()
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        return parseLines(text)
            .flatMap { lineSegment -> lineSegment.allPoints }
            .groupBy { it }
            .filter { (_, points) -> points.size > 1 }
            .count()
    }

    private fun parseLines(text: List<String>): List<LineSegment> {
        return text.map { line ->
            val points = line
                .split(" -> ")
                .map { pointText ->
                    val points = pointText.split(",").map { it.toInt() }
                    Point(
                        x = points[0],
                        y = points[1]
                    )
                }
            LineSegment(
                pointA = points[0],
                pointB = points[1],
            )
        }
    }
}

fun main() {
    val inputText = readInput("year_2021/day_05/Day05.txt")

    val solutionOne = Day05.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day05.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}