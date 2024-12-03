package year_2024.day_01

import readInput
import kotlin.math.abs

object Day01 {
    fun solutionOne(locationLists: List<String>): Int {
        val (leftList, rightList) = parseLocationIds(locationLists)

        val differences = mutableListOf<Int>()
        val sortedLeftList = leftList.sorted()
        val sortedRightList = rightList.sorted()

        sortedLeftList.indices.forEach { i ->
            differences.add(abs(sortedLeftList[i] - sortedRightList[i]))
        }
        return differences.sum()
    }

    fun solutionTwo(locationLists: List<String>): Int {
        val (leftList, rightList) = parseLocationIds(locationLists)

        val similarityScores = leftList.map { locationId ->
            val rightListCount = rightList.count { it == locationId }
            locationId * rightListCount
        }
        println(similarityScores)
        return similarityScores.sum()
    }

    private fun parseLocationIds(locationLists: List<String>): Pair<List<Int>, List<Int>> {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        locationLists.forEach { str ->
            val split = str.split(" ")
            leftList.add(split.first().toInt())
            rightList.add(split.last().toInt())
        }

        return leftList to rightList
    }
}

fun main() {
    val locationLists = readInput("year_2024/day_01/Day01.txt")

    val solutionOne = Day01.solutionOne(locationLists)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day01.solutionTwo(locationLists)
    println("Solution 2: $solutionTwo")
}
