package year_2021.day_09

import readInput
import util.neighbors
import util.product

object Day09 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val heightmap = parseText(text)

        val lowPoints = heightmap.flatMapIndexed { rowIndex, row ->
            row.filterIndexed { colIndex, _ ->
                isLowPoint(rowIndex, colIndex, heightmap)
            }
        }

        return lowPoints.sumOf { it + 1 }
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val positions = text.indices.product(text[0].indices)
        val depths = positions.associateWith { (row, col) -> text[row][col] }.withDefault { '@' }

        val positionsWithBasinLabels = mutableMapOf<Pair<Int, Int>, Int>()
        var label = 0
        positions.forEach { position -> searchBasin(position, depths, positionsWithBasinLabels, label++) }
        val basins = positionsWithBasinLabels.entries.groupBy({ it.value }) { it.key }.values

        return basins
            .map { it.size }
            .sortedBy { it }
            .takeLast(3)
            .reduce { a, b -> a * b }
    }

    private fun parseText(text: List<String>): List<List<Int>> {
        return text.map { line ->
            line.map { height ->
                Integer.parseInt("$height")
            }
        }
    }

    private fun isLowPoint(row: Int, column: Int, heightmap: List<List<Int>>): Boolean {
        val height = heightmap[row][column]

        var top = Integer.MAX_VALUE
        var bottom = Integer.MAX_VALUE
        var left = Integer.MAX_VALUE
        var right = Integer.MAX_VALUE

        if (row > 0) {
            top = heightmap[row - 1][column]
        }

        if (row < heightmap.size - 1) {
            bottom = heightmap[row + 1][column]
        }

        if (column > 0) {
            left = heightmap[row][column - 1]
        }

        if (column < heightmap[row].size - 1) {
            right = heightmap[row][column + 1]
        }

        return height < top && height < bottom && height < left && height < right
    }

    private fun searchBasin(
        position: Pair<Int, Int>,
        depths: Map<Pair<Int, Int>, Char>,
        positionsWithBasinLabels: MutableMap<Pair<Int, Int>, Int>,
        label: Int
    ) {
        if (position !in positionsWithBasinLabels && depths.getValue(position) < '9') {
            positionsWithBasinLabels[position] = label
            position.neighbors().forEach { searchBasin(it, depths, positionsWithBasinLabels, label) }
        }
    }
}

fun main() {
    val inputText = readInput("year_2021/day_09/Day10.txt")

    val solutionOne = Day09.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day09.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}