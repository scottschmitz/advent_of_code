package year_2022.day_15

import readInput
import util.Line
import util.Point
import util.crossingPoint
import util.manhattanDistance
import kotlin.math.abs

data class Sensor(
    val position: Point,
    val closestBeaconPosition: Point,
    val closestBeaconDistance: Int
)

object Day15 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>, row: Int): Int {
        val sensors = parseText(text)

        val sensorPositions = sensors.map { it.position }
        val beaconPositions = sensors.map { it.closestBeaconPosition }

        val maxBeaconDistance = sensors.maxOf { it.closestBeaconDistance } + 1
        val minX = sensorPositions.minOf { it.first } - maxBeaconDistance
        val maxX = sensorPositions.maxOf { it.first } + maxBeaconDistance

        val impossibleLocations = (minX .. maxX)
            .filter { i ->
                val checkPosition = i to row
                checkPosition !in beaconPositions
                    && checkPosition !in sensorPositions
                    && sensors.any { sensor -> sensor.position.manhattanDistance(checkPosition) <= sensor.closestBeaconDistance }
            }

        return impossibleLocations.size
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>, maxSearchArea: Int): Long {
        val sensors = parseText(text)

        val linesOfInterest = sensors.flatMap { sensor ->
            getBoundaryLines(sensor.position, sensor.closestBeaconDistance + 1)
        }

        val pointsOfInterest = linesOfInterest
            .flatMap { line1 -> linesOfInterest.mapNotNull { line2 -> line1.crossingPoint(line2) } }
            .filter { (col, row) -> col in (0 .. maxSearchArea) && row in (0 .. maxSearchArea) }
            .distinct()

        return pointsOfInterest
            .filter { point -> sensors.all { sensor -> sensor.position.manhattanDistance(point) > sensor.closestBeaconDistance } }
            .map { (col, row) -> col * 4_000_000L + row }
            .single()
    }

    private fun parseText(text: List<String>): List<Sensor> {
        return text.map { line ->
            val split = line.split(" ")
            val sensorX = Integer.parseInt(split[2].split("=")[1].split(",")[0])
            val sensorY = Integer.parseInt(split[3].split("=")[1].split(":")[0])
            val beaconX = Integer.parseInt(split[8].split("=")[1].split(",")[0])
            val beaconY = Integer.parseInt(split[9].split("=")[1])

            Sensor(
                position = sensorX to sensorY,
                closestBeaconPosition = beaconX to beaconY,
                closestBeaconDistance = (sensorX to sensorY).manhattanDistance((beaconX to beaconY))
            )
        }
    }

    private fun getBoundaryLines(point: Point, distance: Int): List<Line> {
        val up = point.first to point.second - distance
        val down = point.first to point.second + distance
        val left = point.first - distance to point.second
        val right = point.first + distance to point.second

        return listOf(
            up to left,
            up to right,
            down to left,
            down to right
        )
    }
}

fun main() {
    val inputText = readInput("year_2022/day_15/Day15.txt")

    val solutionOne = Day15.solutionOne(inputText, 2_000_000)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day15.solutionTwo(inputText, 4_000_000)
    println("Solution 2: $solutionTwo")
}