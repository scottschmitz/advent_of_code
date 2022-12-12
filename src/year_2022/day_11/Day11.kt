package year_2022.day_11

import readInput
import java.math.BigInteger

data class Monkey(
    val id: Int,
    val items: MutableList<Int>,
    val operation: List<String>,
    val divisibleBy: Int,
    val whenTrue: Int,
    val whenFalse: Int
) {
    var inspectionCount: Long = 0

    /**
     * @return pair of worry level to monkey tossed to
     */
    fun test(worryLevel: Int, modulo: Int, decreaseWorry: Boolean = true): Pair<Int, Int> {
        inspectionCount += 1

        val formula = operation.map { value ->
            if (value == "old") {
                worryLevel.toString()
            } else {
                value
            }
        }

        var bigResult = when (formula[1]) {
            "+" -> BigInteger(formula[0]) + BigInteger(formula[2])
            "-" -> BigInteger(formula[0]) - BigInteger(formula[2])
            "*" -> BigInteger(formula[0]) * BigInteger(formula[2])
            "/" -> BigInteger(formula[0]) / BigInteger(formula[2])
            else -> {
                println("UNKNOWN OPERATION: ${formula[1]}")
                0.toBigInteger()
            }
        }

        if (bigResult > modulo.toBigInteger()) {
            bigResult %= modulo.toBigInteger()
        }

        var result = bigResult.toInt()
        if (decreaseWorry) {
            result /= 3
        }

        return if (result % divisibleBy == 0) {
            result to whenTrue
        } else {
            result to whenFalse
        }
    }
}

object Day11 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Long {
        val monkeys = parseText(text)

        var modulo = monkeys.first().divisibleBy
        for (i in 1 until monkeys.size) {
            modulo *= monkeys[i].divisibleBy
        }

        for (round in 1..20) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val itemWorryLevel = monkey.items.removeFirst()
                    val (newWorry, nextMonkey) = monkey.test(itemWorryLevel, modulo)

                    monkeys.first { it.id == nextMonkey }.items.add(newWorry)
                }
            }

//            println("== After round $round")
//            monkeys.forEach { monkey ->
//                println("Monkey ${monkey.id}: ${monkey.items}")
//            }
//            println("\n\n")
        }

        val inspections = monkeys
            .map { monkey -> monkey.inspectionCount }
            .sortedByDescending { it }

        return inspections[0] * inspections[1]
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Long {
        val monkeys = parseText(text)

        var modulo = monkeys.first().divisibleBy
        for (i in 1 until monkeys.size) {
            modulo *= monkeys[i].divisibleBy
        }

        for (round in 1..10_000) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val itemWorryLevel = monkey.items.removeFirst()
                    val (newWorry, nextMonkey) = monkey.test(itemWorryLevel, modulo, false)

                    monkeys.first { it.id == nextMonkey }.items.add(newWorry)
                }
            }

//            if (round in listOf(1, 20) || round % 1000 == 0) {
//                println("\n== After round $round ==")
//                monkeys.forEach { monkey ->
//                    println("Monkey ${monkey.id} inspected items ${monkey.inspectionCount} times.")
//                }
//            }
        }

        val inspections = monkeys
            .map { monkey -> monkey.id to monkey.inspectionCount }
            .sortedByDescending { it.second }

        return inspections[0].second * inspections[1].second
    }

    private fun parseText(text: List<String>): List<Monkey> {
        return text.filter { it.isNotEmpty() }.windowed(6, 6).map { monkey ->
            val monkeyId = monkey[0].split(" ")[1].split(":").first()
            val items = monkey[1].substring(18).split(", ").map { Integer.parseInt(it) }
            val operation = monkey[2].split(": ")[1].split(" ").subList(2, 5)
            val testDivisibleBy = monkey[3].split(" ").last()
            val testTrue = monkey[4].split(" ").last()
            val testFalse = monkey[5].split(" ").last()

            Monkey(
                id = Integer.parseInt(monkeyId),
                items = items.toMutableList(),
                operation = operation,
                divisibleBy = Integer.parseInt(testDivisibleBy),
                whenTrue = Integer.parseInt(testTrue),
                whenFalse = Integer.parseInt(testFalse)
            )
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_11/Day11.txt")

    val solutionOne = Day11.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day11.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}