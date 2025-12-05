package year_2025.day_05

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import BaseDay
import util.simplifyOverlaps
import util.size
import year_2025.day_05.Day05.Cafeteria

class Day05 : BaseDay<Cafeteria, Int, Long>("year_2025/day_05/Day05.txt") {

    override fun processFile(input: List<String>): Cafeteria {
        val freshIngredients = mutableListOf<LongRange>()
        val availableIngredients = mutableListOf<Long>()

        input.forEach { line ->
            when {
                line.isEmpty() -> {}
                line.contains('-') -> {
                    val split = line.split("-")
                    freshIngredients.add(LongRange(split[0].toLong(), split[1].toLong()))
                }
                else -> {
                    availableIngredients.add(line.toLong())
                }
            }
        }
        return Cafeteria(
            freshIngredients = freshIngredients.simplifyOverlaps(),
            availableIngredients = availableIngredients,
        )
    }

    override fun solutionOne(input: Cafeteria): Int {
        return input.availableIngredients.count { ingredient ->
            input.freshIngredients.any { freshRange -> freshRange.contains(ingredient) }
        }
    }

    override fun solutionTwo(input: Cafeteria): Long {
        return input.freshIngredients.sumOf { range ->
            range.size
        }
    }

    data class Cafeteria(
        val freshIngredients: List<LongRange>,
        val availableIngredients: List<Long>,
    )

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "3-5",
        "10-14",
        "16-20",
        "12-18",
        "",
        "1",
        "5",
        "8",
        "11",
        "17",
        "32",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        Assertions.assertEquals(3, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        Assertions.assertEquals(14, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}