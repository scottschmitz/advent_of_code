package year_2025.day_08

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import readInput
import util.product
import kotlin.math.sqrt

class Day08 {

    private val rawInput: List<String> by lazy { readInput("year_2025/day_08/Day08.txt") }

    fun processFile(input: List<String>): List<ThreeDimCoordinate> {
        return input.map { line ->
            val split = line.split(',')
            Day08.ThreeDimCoordinate(
                x = split[0].toInt(),
                y = split[1].toInt(),
                z = split[2].toInt(),
            )
        }
    }

    fun solutionOne(input: List<ThreeDimCoordinate>, connections: Int): Int {
        val allCombos = input
            .flatMapIndexed { index, coordA -> input.drop(index + 1).map { coordB -> coordA to coordB } }
            .sortedBy { it.first.distanceTo(it.second) }


        val circuits = mutableListOf<Circuit>()
        circuits.addAll(
            input.map { Circuit(mutableListOf(it)) }
        )

        allCombos.take(connections).forEach { (junctionA, junctionB) ->
            val circuitWithA = circuits.first { it.junctions.contains(junctionA) }
            val circuitWithB = circuits.first { it.junctions.contains(junctionB) }

            if (circuitWithA != circuitWithB) {
                circuitWithA.junctions.addAll(circuitWithB.junctions)
                circuits.remove(circuitWithB)
            }
        }

        return circuits
            .map { it.junctions.size }
            .sortedDescending()
            .take(3)
            .product()
    }

    fun solutionTwo(input: List<ThreeDimCoordinate>): Long {
        val allCombos = input
            .flatMapIndexed { index, coordA -> input.drop(index + 1).map { coordB -> coordA to coordB } }
            .sortedBy { it.first.distanceTo(it.second) }


        val circuits = mutableListOf<Circuit>()
        circuits.addAll(
            input.map { Circuit(mutableListOf(it)) }
        )

        allCombos.forEach { (junctionA, junctionB) ->
            val circuitWithA = circuits.first { it.junctions.contains(junctionA) }
            val circuitWithB = circuits.first { it.junctions.contains(junctionB) }

            if (circuitWithA != circuitWithB) {
                circuitWithA.junctions.addAll(circuitWithB.junctions)
                circuits.remove(circuitWithB)

                if (circuits.size == 1) {
                    return junctionA.x.toLong() * junctionB.x.toLong()
                }
            }
        }

        return -1
    }

    data class ThreeDimCoordinate(
        val x: Int,
        val y: Int,
        val z: Int,
    ) {
        fun distanceTo(other: ThreeDimCoordinate): Double {
            val dx = (other.x - x).toDouble()
            val dy = (other.y - y).toDouble()
            val dz = (other.z - z).toDouble()
            return sqrt(dx * dx + dy * dy + dz * dz)
        }
    }

    data class Circuit(
        val junctions: MutableList<ThreeDimCoordinate>
    )

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "162,817,812",
        "57,618,57",
        "906,360,560",
        "592,479,940",
        "352,342,300",
        "466,668,158",
        "542,29,236",
        "431,825,988",
        "739,650,466",
        "52,470,668",
        "216,146,977",
        "819,987,18",
        "117,168,530",
        "805,96,715",
        "346,949,466",
        "970,615,88",
        "941,993,340",
        "862,61,35",
        "984,92,344",
        "425,690,689",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(40, solutionOne(sampleInput, 10))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(processFile(rawInput), 1000))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(25272L, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        // 405910288 too low
        println("Part 2: " + solutionTwo(processFile(rawInput)))
    }
    //</editor-fold>
}