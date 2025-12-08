package year_2025.day_06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import util.transpose
import year_2023.day_06.product
import year_2025.day_04.Day04
import year_2025.day_04.Day04.Tile

class Day06 : BaseDay<List<String>, Long, Long>("year_2025/day_06/Day06.txt") {

    override fun processFile(input: List<String>): List<String> = input

    override fun solutionOne(input: List<String>): Long {
        val listOfNumbers = input.dropLast(1).map { line ->
            line.split(' ').filter { it.isNotEmpty() }
                .map { it.toLong() }
        }.transpose()
        val operators = input.last().split(' ').filter { it.isNotEmpty() }.map { Operator.fromChar(it.first()) }

        val equations = listOfNumbers.zip(operators) { nums, op ->
            Equation(nums, op)
        }

        return equations.sumOf { equation ->
            when (equation.operator) {
                Operator.PLUS -> equation.nums.sum()
                Operator.MULTIPLY -> equation.nums.product()
            }
        }
    }

    override fun solutionTwo(input: List<String>): Long {
        val height = input.size
        val width = input.maxOf { it.length }
        val operatorRowIndex = input.lastIndex

        // Normalise all rows to same width
        val grid = input.map { it.padEnd(width, ' ') }


        fun isSeparatorCol(col: Int): Boolean = (0 until height).all { row -> grid[row][col] == ' ' }
        fun isNumberCol(col: Int): Boolean = (0 until operatorRowIndex).any { row -> grid[row][col].isDigit() }

        val blocks = mutableListOf<Block>()
        var inBlock = false
        var blockStart = 0

        for (col in 0 until width) {
            if (isSeparatorCol(col)) {
                if (inBlock) {
                    blocks += Block(blockStart, col - 1)
                    inBlock = false
                }
            } else {
                if (!inBlock) {
                    inBlock = true
                    blockStart = col
                }
            }
        }
        if (inBlock) {
            blocks += Block(blockStart, width - 1)
        }

        val equations = mutableListOf<Equation>()
        for (block in blocks.asReversed()) {
            // Find the operator in the operator row somewhere in this block
            val opChar: Char? = (block.start..block.end)
                .map { col -> grid[operatorRowIndex][col] }
                .firstOrNull { it != ' ' }

            val operator = opChar?.let { Operator.fromChar(it) }
                ?: error("No operator found in block $block")

            // Collect number columns in this block, right-to-left
            val nums = mutableListOf<Long>()

            for (col in block.end downTo block.start) {
                if (!isNumberCol(col)) continue

                val digits = buildString {
                    for (row in 0 until operatorRowIndex) {
                        val ch = grid[row][col]
                        if (ch.isDigit()) append(ch)
                    }
                }

                if (digits.isNotEmpty()) {
                    nums += digits.toLong()
                }
            }

            if (nums.isEmpty()) {
                error("Block $block has operator but no numbers")
            }

            equations += Equation(nums = nums, operator = operator)
        }

        return equations.sumOf { equation ->
            when (equation.operator) {
                Operator.PLUS -> equation.nums.sum()
                Operator.MULTIPLY -> equation.nums.product()
            }
        }
    }

    data class Block(
        val start: Int,
        val end: Int,
    )

    data class Equation(
        val nums: List<Long>,
        val operator: Operator
    )

    enum class Operator(val char: Char) {
        PLUS('+'),
        MULTIPLY('*'),
        ;
        companion object {
            private val lookup = Operator.values().associateBy(Operator::char)
            fun fromChar(c: Char): Operator = lookup[c]!!
        }
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "123 328  51 64 ",
        " 45 64  387 23 ",
        "  6 98  215 314",
        "*   +   *   +  ",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(4277556L, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(3263827L, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}