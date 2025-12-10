package year_2025.day_10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.microsoft.z3.*
import BaseDay
import util.Pathfinding
import year_2025.day_10.Day10.Machine

/*
 * First time learning about Z3. This implementation is nearly a 1:1 copy from
 * https://github.com/Jadarma/advent-of-code-kotlin-solutions/blob/92e1c6945b689ab88f271ab235447e626d791f0d/solutions/aockt/y2025/Y2025D10.kt
 */
class Day10 : BaseDay<List<Machine>, Int, Int>("year_2025/day_10/Day10.txt") {

    override fun processFile(input: List<String>): List<Machine> {
        return input.map { line ->
            val split = line.split(' ')
            val lightDiagram = split.first().mapNotNull { char ->
                when (char) {
                    '.' -> false
                    '#' -> true
                    else -> null
                }
            }
            val joltageRequirement = split.last().drop(1).dropLast(1).split(',').map { it.toInt() }

            val buttonWirings = split.drop(1).dropLast(1).map { wiring ->
                wiring.drop(1).dropLast(1).split(',').map { it.toInt() }
            }

            Machine(
                indicatorLightDiagram = lightDiagram,
                buttonWirings = buttonWirings,
                joltageRequirement = joltageRequirement
            )
        }
    }

    override fun solutionOne(input: List<Machine>): Int {
        return input.sumOf { solveLights(it) }
    }

    override fun solutionTwo(input: List<Machine>): Int {
        return input.sumOf { solveJoltages(it) }
    }

    private fun solveLights(machine: Machine): Int {
        return Pathfinding.search(
            start = List(machine.indicatorLightDiagram.size) { false },
            goalFunction = { state -> state == machine.indicatorLightDiagram },
            neighbours = { state ->
                machine.buttonWirings
                    .map { flips -> state.mapIndexed { index, on -> if (index in flips) !on else on } }
                    .map { it to 1 }
            },
        )!!.cost
    }

    private fun solveJoltages(machine: Machine): Int = Context().use { ctx ->
        val solver = ctx.mkOptimize()
        val zero = ctx.mkInt(0)

        // Counts number of presses for each button, and ensures it is positive.
        val buttons = machine.buttonWirings.indices
            .map { ctx.mkIntConst("button#$it") }
            .onEach { button -> solver.Add(ctx.mkGe(button, zero)) }
            .toTypedArray()

        // For each joltage counter, require that the sum of presses of all buttons that increment it is equal to the
        // target value specified in the config.
        machine.joltageRequirement.forEachIndexed { counter, targetValue ->
            val buttonsThatIncrement = machine.buttonWirings
                .withIndex()
                .filter { (_, counters) -> counter in counters }
                .map { buttons[it.index] }
                .toTypedArray()
            val target = ctx.mkInt(targetValue)
            val sumOfPresses = ctx.mkAdd(*buttonsThatIncrement) as IntExpr
            solver.Add(ctx.mkEq(sumOfPresses, target))
        }

        // Describe that the presses (solution answer) is the sum of all individual button presses, and should be as
        // low as possible.
        val presses = ctx.mkIntConst("presses")
        solver.Add(ctx.mkEq(presses, ctx.mkAdd(*buttons)))
        solver.MkMinimize(presses)

        // Find solution and return.
        if (solver.Check() != Status.SATISFIABLE) error("No solution found for machine: $machine.")
        solver.getModel().evaluate(presses, false).let { it as IntNum }.int
    }

    data class Machine(
        val indicatorLightDiagram: List<Boolean>,
        val buttonWirings: List<List<Int>>,
        val joltageRequirement: List<Int>,
    )

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
        "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
        "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(7, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(33, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}