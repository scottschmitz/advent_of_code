package year_2023.day_12

import readInput

data class SpringCollection(
    val pattern: List<Char>,
    val damageCounts: List<Int>
)

object Day12 {
    private val memory = mutableMapOf<Pair<List<Char>, List<Int>>, Int>()

    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        return text.sumOf { line ->
            val springCollection = parseSpring(line)
            findThem(springCollection.pattern, springCollection.damageCounts)
        }
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        return text.sumOf { line ->
            val springCollection = parseSpring(line)

            val actualPattern = listOf(springCollection.pattern, listOf('?'), springCollection.pattern, listOf('?'), springCollection.pattern, listOf('?'), springCollection.pattern, listOf('?'), springCollection.pattern).flatten()
            val actualDamageCounts = listOf(springCollection.damageCounts, springCollection.damageCounts, springCollection.damageCounts, springCollection.damageCounts, springCollection.damageCounts).flatten()

            findThem(actualPattern.toList(), actualDamageCounts)
        }
    }

    private fun parseSpring(text: String): SpringCollection {
        val split = text.split(" ")
        val pattern = split[0]
        val damagedCounts = split[1].split(",").map { it.toInt() }

        return SpringCollection(pattern.toList(), damagedCounts)
    }

    private fun findThem(pattern: List<Char>, brokenParts: List<Int>): Int {
        if (pattern.isEmpty()) {
            return when (brokenParts.size) {
                0 -> 1
                else -> 0
            }
        }

        return when (pattern.first()) {
            '.' -> findThem(pattern.drop(1), brokenParts)
            '#' -> otherFun(pattern, brokenParts).also { memory[pattern to brokenParts] = it }
            '?' -> findThem(pattern.drop(1), brokenParts) + otherFun(pattern, brokenParts).also { memory[pattern to brokenParts] = it }
            else -> throw IllegalArgumentException("whats this? ${pattern.first()}")
        }
    }

    private fun otherFun(
        pattern: List<Char>,
        brokenCounts: List<Int>
    ): Int {

        memory[pattern to brokenCounts]?.let{
            return it
        }

        // make sure need to look for broken counts
        if (brokenCounts.isEmpty()) {
            return 0
        }

        // make sure there is enough characters
        val brokenCount = brokenCounts.first()
        if (pattern.size < brokenCount) {
            return 0
        }

        // make sure there are enough not known good parts to meet need
        for (i in 0 until brokenCount) {
            if (pattern[i] == '.') {
                return 0
            }
        }

        if (pattern.size == brokenCount) {
            return when (brokenCounts.size) {
                1 -> 1
                else -> 0
            }
        }

        if (pattern[brokenCount] == '#') {
            return 0
        }

        return findThem(pattern.drop(brokenCount + 1), brokenCounts.drop(1))
    }
}

fun main() {
    val text = readInput("year_2023/day_12/Day12.txt")

    val solutionOne = Day12.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day12.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
