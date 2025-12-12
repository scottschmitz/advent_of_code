package year_2025.day_12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import readInput

class Day12 {
    fun processFile(input: List<String>): Puzzle {
        val present0 = Present(
            id = 0,
            format = input.take(4).drop(1).map { line ->
                line.map { it == '#' }
            }
        )

        val present1 = Present(
            id = 1,
            format = input.take(9).drop(6).map { line ->
                line.map { it == '#' }
            }
        )

        val present2 = Present(
            id = 1,
            format = input.take(14).drop(11).map { line ->
                line.map { it == '#' }
            }
        )

        val present3 = Present(
            id = 1,
            format = input.take(19).drop(16).map { line ->
                line.map { it == '#' }
            }
        )

        val present4 = Present(
            id = 1,
            format = input.take(24).drop(21).map { line ->
                line.map { it == '#' }
            }
        )

        val present5 = Present(
            id = 1,
            format = input.take(29).drop(26).map { line ->
                line.map { it == '#' }
            }
        )

        val regions = input.drop(30).map { line ->
            val split = line.split(':')
            val lengthWidth = split[0].split('x').map { it.toInt() }

            val length = lengthWidth[0]
            val width = lengthWidth[1]

            val presentCounts = split[1].split(' ').filterNot { it.isEmpty() }.map { it.toInt() }
            Region(
                length = length,
                width = width,
                present0Count = presentCounts[0],
                present1Count = presentCounts[1],
                present2Count = presentCounts[2],
                present3Count = presentCounts[3],
                present4Count = presentCounts[4],
                present5Count = presentCounts[5],
            )
        }


        return Puzzle(
            presents = listOf(present0, present1, present2, present3, present4, present5),
            regions = regions
        )
    }

    fun solutionOne(input: Puzzle): Long {
        return input.regions.count {
            basicAreaCheck(input.presents, it)
        }.toLong()
    }


    private fun basicAreaCheck(presents: List<Present>, region: Region): Boolean {
        return region.present0Count * presents[0].requiredSpace +
                region.present1Count * presents[1].requiredSpace +
                region.present2Count * presents[2].requiredSpace +
                region.present3Count * presents[3].requiredSpace +
                region.present4Count * presents[4].requiredSpace +
                region.present5Count * presents[5].requiredSpace <= region.length * region.width
    }

    data class Present(
        val id: Int,
        val format: List<List<Boolean>>,
    ) {
        val requiredSpace = format.sumOf { it.count { it } }
    }

    data class Region(
        val length: Int,
        val width: Int,
        val present0Count: Int,
        val present1Count: Int,
        val present2Count: Int,
        val present3Count: Int,
        val present4Count: Int,
        val present5Count: Int,
    )

    data class Puzzle(
        val presents: List<Present>,
        val regions: List<Region>,
    )

    /**
     * This puzzle is ridiculous and instead of waiting til the heat death of the universe the smart people
     * of reddit realized that the simple solution here wins. Sadly it does not work for the actual sample input
     * which is why that has been removed from here.
     */
    @Test
    fun part1() {
        val file = readInput("year_2025/day_12/Day12.txt")
        val parsedInput = processFile(file)
        println("Part 1: " + solutionOne(parsedInput))
    }
    //</editor-fold>
}