package year_2021.day_06

import readInput

data class LanternFish(
    val spawnTimer: Int
) {
    fun advance(): List<LanternFish> {
        val newTime = spawnTimer - 1

        return if (newTime < 0) {
            listOf(
                LanternFish(6),
                LanternFish(8)
            )
        } else {
            listOf(
                LanternFish(newTime)
            )
        }
    }
}

object Day06 {

    /**
     * @return
     */
    fun solutionOne(text: String): Int {
        var fish = parseFish(text)
        for (i in 0 until 80) {
           fish = fish.flatMap { it.advance() }
        }
        return fish.size
    }

    /**
     * @return
     */
    fun solutionTwo(text: String): Int {
        var fish = parseFish(text)
        for (i in 0 until 256) {
            println("advancing.. day $i")
            fish = fish.flatMap { it.advance() }
        }
        return fish.size
    }

    private fun parseFish(text: String): List<LanternFish> {
        return text
            .split(",")
            .map { Integer.parseInt(it) }
            .map { timer ->
                LanternFish(spawnTimer = timer)
            }
    }
}

fun main() {
    val inputText = readInput("year_2021/day_06/Day06.txt")

    val solutionOne = Day06.solutionOne(inputText.first())
    println("Solution 1: $solutionOne")

    val solutionTwo = Day06.solutionTwo(inputText.first())
    println("Solution 2: $solutionTwo")
}