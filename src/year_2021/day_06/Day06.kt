package year_2021.day_06

import readInput
import java.math.BigInteger

data class LanternFish(
    val spawnTimer: Int,
    val quantity: BigInteger,
) {
    fun advance(): List<LanternFish> {
        val newTime = spawnTimer - 1

        return if (newTime < 0) {
            listOf(
                LanternFish(6, quantity),
                LanternFish(8, quantity)
            )
        } else {
            listOf(
                LanternFish(newTime, quantity)
            )
        }
    }
}

object Day06 {

    /**
     * @return
     */
    fun solutionOne(text: String): BigInteger {
        var fish = parseFish(text)
        for (i in 0 until 80) {
            fish = fish.flatMap { it.advance() }
                .groupBy { it.spawnTimer }
                .map { (timer, fishes) ->
                    LanternFish(
                        spawnTimer = timer,
                        quantity = fishes.sumOf { it.quantity }
                    )
                }
        }
        return fish.sumOf { it.quantity }
    }

    /**
     * @return
     */
    fun solutionTwo(text: String): BigInteger {
        var fish = parseFish(text)
        for (i in 0 until 256) {
            fish = fish.flatMap { it.advance() }
                .groupBy { it.spawnTimer }
                .map { (timer, fishes) ->
                    LanternFish(
                        spawnTimer = timer,
                        quantity = fishes.sumOf { it.quantity }
                    )
                }
        }
        return fish.sumOf { it.quantity }
    }

    private fun parseFish(text: String): List<LanternFish> {
        return text
            .split(",")
            .map { Integer.parseInt(it) }
            .groupBy { it }
            .map { (timer, fish) ->
                LanternFish(timer, fish.size.toBigInteger())
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