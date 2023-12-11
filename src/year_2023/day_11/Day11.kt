package year_2023.day_11

import readInput
import util.Point


data class Universe(
    val galaxies: List<Point>,
    val horizontalExpansions: List<Int>,
    val verticalExpansions: List<Int>
)

object Day11 {

    /**
     *
     */
    fun distances(text: List<String>, age: Int): Long {
        val distances = mutableMapOf<Pair<Point, Point>, Long>()
        val universe = parseUniverse(text)

        universe.galaxies.forEach { galaxy ->
            universe.galaxies.filter { it != galaxy }.forEach { otherGalaxy ->
                if (!distances.containsKey(galaxy to otherGalaxy) && !distances.containsKey(otherGalaxy to galaxy)) {
                    distances[galaxy to otherGalaxy] = calculateDistance(galaxy, otherGalaxy, universe, age)
                }
            }
        }

        return distances.values.sum()
    }

    private fun parseUniverse(text: List<String>): Universe {
        val galaxies = mutableListOf<Point>()
        val horizontalExpansions = mutableListOf<Int>()
        val verticalExpansions = mutableListOf<Int>()

        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    galaxies.add(x to y)
                }
            }
        }

        text.forEachIndexed { y, line ->
            if (line.all { it == '.' }) {
                verticalExpansions.add(y)
            }
        }

        for (x in text.first().indices) {
            if (text.all { it[x] == '.'} ) {
                horizontalExpansions.add(x)
            }
        }

        return Universe(
            galaxies = galaxies,
            horizontalExpansions = horizontalExpansions,
            verticalExpansions = verticalExpansions
        )
    }

    private fun calculateDistance(pointA: Point, pointB: Point, universe: Universe, age: Int): Long {
        var hDiff = 0L
        val left = if (pointA.first < pointB.first) pointA.first else pointB.first
        val right = if (pointA.first > pointB.first) pointA.first else pointB.first
        for (x in left until right) {
            hDiff += 1

            if (x in universe.horizontalExpansions) {
                hDiff += age - 1
            }
        }

        var vDiff = 0L
        val top = if (pointA.second < pointB.second) pointA.second else pointB.second
        val bottom = if (pointA.second > pointB.second) pointA.second else pointB.second
        for (y in top until bottom) {
            vDiff += 1

            if (y in universe.verticalExpansions) {
                vDiff += age - 1
            }
        }

        return hDiff + vDiff
    }
}

fun main() {
    val text = readInput("year_2023/day_11/Day11.txt")

    val solutionOne = Day11.distances(text, 2)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day11.distances(text, 1_000_000)
    println("Solution 2: $solutionTwo")
}
