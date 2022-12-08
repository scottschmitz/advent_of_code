package year_2022.day_08

import readInput

object Day08 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val forest = parseForest(text)
        var visibleCount = text.first().length * 2 + (text.size - 2) * 2
        for (i in 1..forest.first().size -2 ) {
            for (j in 1..forest[i].size - 2) {
                if (isTreeVisible(forest, i, j)) {
                    visibleCount +=1
                }
            }
        }
        return visibleCount
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val forest = parseForest(text)

        var currentMax = 0
        forest.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                val viewingDistance = calculateViewingDistance(forest, i, j)
                if (viewingDistance > currentMax) {
                    currentMax = viewingDistance
                }
            }
        }

        return currentMax
    }

    private fun parseForest(text: List<String>): List<List<Int>> {
        return text.map { line ->
            line.toCharArray().map { Integer.parseInt("$it") }
        }
    }

    private fun isTreeVisible(forest: List<List<Int>>, row: Int, column: Int): Boolean {
        val treeHeight = forest[row][column]

        val visibleFromLeft = forest[row].subList(0, column).max() < treeHeight
        val visibleFromRight = forest[row].subList(column + 1, forest[row].size).max() < treeHeight

        val columnHeights = forest.map { it[column] }
        val visibleFromTop = columnHeights.subList(0, row).max() < treeHeight
        val visibleFromBottom = columnHeights.subList(row + 1, columnHeights.size).max() < treeHeight

        return visibleFromLeft || visibleFromRight || visibleFromTop || visibleFromBottom
    }

    private fun calculateViewingDistance(forest: List<List<Int>>, row: Int, column: Int): Int {
        val treeHeight = forest[row][column]

        var distanceToLeft = 0
        for (i in column - 1 downTo 0) {
            distanceToLeft += 1

            if (treeHeight <= forest[row][i]) {
                break
            }
        }

        var distanceToRight = 0
        for (i in column + 1 until forest[row].size) {
            distanceToRight += 1

            if (treeHeight <= forest[row][i]) {
                break
            }
        }

        var distanceToTop = 0
        for (i in row - 1 downTo  0) {
            distanceToTop += 1

            if (treeHeight <= forest[i][column]) {
                break
            }
        }

        var distanceToBottom = 0
        for (i in row + 1 until  forest.size) {
            distanceToBottom += 1

            if (treeHeight <= forest[i][column]) {
                break
            }
        }

        return distanceToLeft * distanceToRight * distanceToTop * distanceToBottom
    }
}

fun main() {
    val inputText = readInput("year_2022/day_08/Day08.txt")

    val solutionOne = Day08.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day08.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}