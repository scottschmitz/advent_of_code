package year_2025.day_07

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import util.Point
import util.down
import util.left
import util.right

class Day07 : BaseDay<Map<Point, Day07.Location>, Long, Long>("year_2025/day_07/Day07.txt") {

    override fun processFile(input: List<String>): Map<Point, Location> {
        return input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to Location.fromChar(char)
            }
        }.toMap()
    }

    var partOneBrain = mutableMapOf<Point, Location>()
    override fun solutionOne(input: Map<Point, Location>): Long {
        partOneBrain = input.toMutableMap()

        return countSplittersDown(input.toList().first { it.second == Location.START }.first)
    }

    var partTwoBrain = mutableMapOf<Point, Location>()
    override fun solutionTwo(input: Map<Point, Location>): Long {
        partTwoBrain = input.toMutableMap()
        return navigateDown(input.toList().first { it.second == Location.START }.first, true)
    }

    val memory = mutableMapOf<Point, Long>()


    private fun countSplittersDown(point: Point): Long {
        var currPoint = point
        var splitterCount = 0L

        while (partOneBrain[currPoint.down()] != null) {
            currPoint = currPoint.down()

            when (partOneBrain[currPoint]) {
                Location.SPLITTER -> {
                    splitterCount += 1
                    if (partOneBrain[currPoint.left()] == Location.OPEN) {
                        partOneBrain[currPoint.left()] = Location.BEAM
                        splitterCount += countSplittersDown(currPoint.left())
                    }

                    if (partOneBrain[currPoint.right()] == Location.OPEN) {
                        partOneBrain[currPoint.right()] = Location.BEAM
                        splitterCount += countSplittersDown(currPoint.right())
                    }

                    break
                }

                Location.OPEN -> {
                    partOneBrain[currPoint] = Location.BEAM
                }

                Location.START,
                Location.BEAM,
                null -> { break }
            }
        }
        return splitterCount
    }

    // @return number of splitters hit
    private fun navigateDown(point: Point, allowCrossingBeams: Boolean): Long {
        var currPoint = point

        while (true) {
            val nextPoint = currPoint.down()
            val nextLoc = partTwoBrain[nextPoint] ?: return 1

            currPoint = nextPoint
            if (currPoint in memory) {
                return memory[currPoint]!!
            }


            when (nextLoc) {
                Location.START -> {
                    memory[currPoint] = 0
                    return 0
                }
                Location.SPLITTER -> {
                    var timelines = 0L

                    if (partTwoBrain[currPoint.left()] == Location.OPEN) {
                        if (!allowCrossingBeams) {
                            partTwoBrain[currPoint.left()] = Location.BEAM
                        }
                        timelines += navigateDown(currPoint.left(), allowCrossingBeams)
                    }

                    if (partTwoBrain[currPoint.right()] == Location.OPEN) {
                        if (!allowCrossingBeams) {
                            partTwoBrain[currPoint.right()] = Location.BEAM
                        }
                        timelines += navigateDown(currPoint.right(), allowCrossingBeams)
                    }

                    memory[currPoint] = timelines
                    return timelines
                }
                Location.BEAM -> {
                    if (!allowCrossingBeams) {
                        memory[currPoint] = 0
                        return 0
                    }
                }
                Location.OPEN -> {
                    if (!allowCrossingBeams) {
                        partTwoBrain[currPoint] = Location.BEAM
                    }
                }
            }
        }
    }

    private fun printManifold(brain: Map<Point, Location>) {
        val maxX = brain.keys.maxOf { it.first }
        val maxY = brain.keys.maxOf { it.second }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                print(brain[x to y]?.char)
            }
            println()
        }
    }


    enum class Location(val char: Char) {
        START('S'),
        SPLITTER('^'),
        BEAM('|'),
        OPEN('.')
        ;
        companion object {
            private val lookup = Location.values().associateBy(Location::char)
            fun fromChar(c: Char): Location = lookup[c]!!
        }
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        ".......S.......",
        "...............",
        ".......^.......",
        "...............",
        "......^.^......",
        "...............",
        ".....^.^.^.....",
        "...............",
        "....^.^...^....",
        "...............",
        "...^.^...^.^...",
        "...............",
        "..^...^.....^..",
        "...............",
        ".^.^.^.^.^...^.",
        "...............",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(21, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(40, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        // 405881186 too low
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}