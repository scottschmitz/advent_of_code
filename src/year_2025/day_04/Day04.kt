package year_2025.day_04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import util.Point
import util.neighbors

class Day04 : BaseDay<Map<Point, Day04.Tile>, Int, Int>("year_2025/day_04/Day04.txt") {

    override fun processFile(input: List<String>): Map<Point, Tile> {
        return input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to Tile.fromChar(char)
            }
        }.toMap()
    }

    override fun solutionOne(input: Map<Point, Tile>): Int {
        return identifyRemovablePaper(input).size
    }

    override fun solutionTwo(input: Map<Point, Tile>): Int {
        val mutableMap = input.toMutableMap()
        var totalRemoved = 0
        var removed = true

        while (removed) {
            val removablePaper = identifyRemovablePaper(mutableMap)
            removablePaper.forEach {  point ->
                mutableMap[point] = Tile.OPEN
            }
            totalRemoved += removablePaper.size
            removed = removablePaper.isNotEmpty()
        }

        return totalRemoved
    }

    private fun identifyRemovablePaper(input: Map<Point, Tile>): List<Point> {
        return input.toList().filter { (point, tile) ->
            if (tile == Tile.PAPER_ROLL) {
                val neighboringPaperRolls = point.neighbors(includeDiagonals = true).count { neighborPoint ->
                    input[neighborPoint] == Tile.PAPER_ROLL
                }
                neighboringPaperRolls < 4
            } else {
                false
            }
        }.map { it.first }
    }

    enum class Tile(val char: Char) {
        PAPER_ROLL('@'),
        OPEN('.'),
        ;

        companion object {
            private val lookup = values().associateBy(Tile::char)
            fun fromChar(c: Char): Tile = lookup[c]!!
        }
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "..@@.@@@@.",
        "@@@.@.@.@@",
        "@@@@@.@.@@",
        "@.@@@@..@.",
        "@@.@@@@.@@",
        ".@@@@@@@.@",
        ".@.@.@.@@@",
        "@.@@@.@@@@",
        ".@@@@@@@@.",
        "@.@.@@@.@.",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(13, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(43, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}