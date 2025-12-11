package year_2025.day_11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import util.Pathfinding

class Day11 : BaseDay<Map<String, List<String>>, Long, Long>("year_2025/day_11/Day11.txt") {

    override fun processFile(input: List<String>): Map<String, List<String>> {
        return input.associate { line ->
            val split = line.split(':')

            val id = split.first()
            val connectedTo = split[1].split(' ').filterNot { it.isEmpty() }

            id to connectedTo
        }
    }

    override fun solutionOne(input: Map<String, List<String>>): Long {
       return Pathfinding.availablePaths(
            start = "you",
            end = "out",
            nodes = input
        )
    }

    override fun solutionTwo(input: Map<String, List<String>>): Long {
        return Pathfinding.availablePaths(
            start = "svr",
            end = "out",
            nodes = input,
            requiredNodes = listOf("dac", "fft")
        )
    }

    data class Device(
        val id: String,
        val connectedTo: List<String>
    )

    //<editor-fold desc="Samples">
    private val sampleText1 = listOf<String>(
        "aaa: you hhh",
        "you: bbb ccc",
        "bbb: ddd eee",
        "ccc: ddd eee fff",
        "ddd: ggg",
        "eee: out",
        "fff: out",
        "ggg: out",
        "hhh: ccc fff iii",
        "iii: out",
    )

    private val sampleText2 = listOf(
        "svr: aaa bbb",
        "aaa: fft",
        "fft: ccc",
        "bbb: tty",
        "tty: ccc",
        "ccc: ddd eee",
        "ddd: hub",
        "hub: fff",
        "eee: dac",
        "dac: fff",
        "fff: ggg hhh",
        "ggg: out",
        "hhh: out",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText1)
        assertEquals(5, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText2)
        assertEquals(2, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}