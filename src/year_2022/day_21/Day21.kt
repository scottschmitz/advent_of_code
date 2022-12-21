package year_2022.day_21

import readInput

enum class Operation {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    ;

    companion object {
        fun from(char: Char): Operation {
            return when (char) {
                '+' -> PLUS
                '-' -> MINUS
                '*' -> MULTIPLY
                '/' -> DIVIDE
                else -> throw IllegalArgumentException()
            }
        }
    }

    val char: String get() {
        return when (this) {
            PLUS -> "+"
            MINUS -> "-"
            MULTIPLY -> "*"
            DIVIDE -> "/"
        }
    }

    fun evaluate(a: Long, b: Long): Long {
        return when (this) {
            PLUS -> a + b
            MINUS -> a - b
            MULTIPLY -> a * b
            DIVIDE -> a / b
        }
    }

    fun evaluateInverse(a: Long, b: Long, aIsHumn: Boolean): Long {
        return when (this) {
            PLUS -> a - b
            MINUS -> if (aIsHumn) a + b else b - a
            MULTIPLY -> a / b
            DIVIDE -> a * b
        }
    }
}

sealed class Monkey(val id: String) {
    data class Number(
        val _id: String,
        val value: Long
    ): Monkey(id = _id)
    data class Operation(
        val _id: String,
        val monkeyAId: String,
        val operation: year_2022.day_21.Operation,
        val monkeyBId: String
    ): Monkey(id = _id)
}

sealed class Equation {
    data class Value(
            val monkey: Monkey.Number
        ): Equation() {
        override fun toString(): String {
            return "${monkey.value}"
        }
    }
    data class Evaluation(val e1: Equation, val operation: Operation, val e2: Equation): Equation() {
        override fun toString(): String {
            return "($e1 ${operation.char} $e2)"
        }
    }
    object Human: Equation() {
        override fun toString(): String {
            return "HUMAN_VALUE"
        }
    }

    fun solve(): Long {
        return when(this) {
            is Value -> monkey.value
            is Evaluation -> {
                operation.evaluate(e1.solve(), e2.solve())
            }
            is Human -> {
                throw IllegalArgumentException("Cannot solve for human")
            }
        }
    }
}

class Day21(text: List<String>){

    private val monkeys = parseMonkeys(text)

    /**
     * @return
     */
    fun solutionOne(): Long {
        val monkeyYells = yell()
        return monkeyYells["root"] ?: -1
    }

    /**
     * @return
     */
    fun solutionTwo(): Long {
        val root = monkeys["root"] as Monkey.Operation

        // Left side has the monkey... so right side can be evaluated
        val rightEquation = getEquation(root.monkeyBId)
        val rightSolution = rightEquation.solve()

        val values = mutableMapOf<String, Long>()
        return solveForHumn(root.monkeyAId, rightSolution, monkeys, values)
    }

    private fun parseMonkeys(text: List<String>): Map<String, Monkey> {
        return text.map { line ->
            val split = line.split(": ")

            val id = split[0]

            val splitAgain = split[1].split(" ")
            if (splitAgain.size == 3) {
                Monkey.Operation(
                    _id = id,
                    monkeyAId = splitAgain[0],
                    operation = Operation.from(splitAgain[1].first()),
                    monkeyBId = splitAgain[2]
                )
            } else {
                Monkey.Number(
                    _id = id,
                    value = Integer.parseInt(split[1]).toLong()
                )
            }
        }.associateBy { monkey -> monkey.id }
    }

    private fun yell(): Map<String, Long> {
        val monkeyYells = mutableMapOf<String, Long>()
        val nonYelledMonkeys = monkeys.values
                .filterIsInstance<Monkey.Operation>()
                .toMutableSet()

        monkeys.values
                .filterIsInstance<Monkey.Number>()
                .forEach { monkey ->
//                    println("Monkey ${monkey.id} yells ${monkey.value}")
                    monkeyYells[monkey.id] = monkey.value
                }

        while (nonYelledMonkeys.isNotEmpty()) {
            val monkey = nonYelledMonkeys.first { monkey ->
                monkeyYells.contains(monkey.monkeyAId)
                        && monkeyYells.contains(monkey.monkeyBId)
            }
            val aYell = monkeyYells[monkey.monkeyAId]
            val bYell = monkeyYells[monkey.monkeyBId]

//            println("Monkey ${monkey.id} checks ${monkey.monkeyAId} - gets value $aYell and ${monkey.monkeyBId} - gets value $bYell")

            if (aYell != null && bYell != null) {
                val value = monkey.operation.evaluate(aYell, bYell)
//                println("Monkey ${monkey.id} yells $value")

                monkeyYells[monkey.id] = value
                nonYelledMonkeys.remove(monkey)
            }
        }
        return monkeyYells
    }

    private fun getEquation(id: String): Equation {
        return when (val monkey = monkeys[id]!!) {
            is Monkey.Number -> {
                if (id == "humn") {
                    Equation.Human
                } else {
                    Equation.Value(monkey)
                }
            }
            is Monkey.Operation -> {
                Equation.Evaluation(
                    e1 = getEquation(monkey.monkeyAId),
                    operation = monkey.operation,
                    e2 = getEquation(monkey.monkeyBId)
                )
            }
        }
    }

    private fun solveForHumn(id: String, expectedValue: Long, monkeys: Map<String, Monkey>, values: MutableMap<String, Long>): Long {
        if (id == "humn") {
            return expectedValue
        }

        return when (val monkey = monkeys[id]!!) {
            is Monkey.Number -> monkey.value
            is Monkey.Operation -> {
                val (humnMonkey, otherMonkey) = whichMonkeyLeadsToHumn(monkey.monkeyAId, monkey.monkeyBId, monkeys)
                val knownValue = solveFor(otherMonkey, monkeys, values)
                val newExpectedValue = monkey.operation.evaluateInverse(expectedValue, knownValue, humnMonkey == monkey.monkeyAId)

                solveForHumn(humnMonkey, newExpectedValue, monkeys, values)
            }
        }
    }

    private fun whichMonkeyLeadsToHumn(lhs: String, rhs: String, monkeys: Map<String, Monkey>): Pair<String, String> {
        return if (leadsToHumn(lhs, monkeys)) {
            Pair(lhs, rhs)
        } else {
            Pair(rhs, lhs)
        }
    }

    private fun leadsToHumn(id: String, monkeys: Map<String, Monkey>): Boolean {
        if (id == "humn") {
            return true
        }

        return when (val monkey = monkeys[id]!!) {
            is Monkey.Number -> false
            is Monkey.Operation -> leadsToHumn(monkey.monkeyAId, monkeys) || leadsToHumn(monkey.monkeyBId, monkeys)
        }
    }

    private fun solveFor(id: String, monkeys: Map<String, Monkey>, values: MutableMap<String, Long>): Long {
        val value = values[id]
        if (value is Long) {
            return value
        }

        return when (val monkey = monkeys[id]!!) {
            is Monkey.Number -> monkey.value
            is Monkey.Operation -> {
                val lhs = values[monkey.monkeyAId] ?: solveFor(monkey.monkeyAId, monkeys, values)
                val rhs = values[monkey.monkeyBId] ?: solveFor(monkey.monkeyBId, monkeys, values)
                val result = monkey.operation.evaluate(lhs, rhs)
                values[id] = result
                result
            }
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_21/Day21.txt")
    val day21 = Day21(inputText)

    val solutionOne = day21.solutionOne()
    println("Solution 1: $solutionOne")

    val solutionTwo = day21.solutionTwo()
    println("Solution 2: $solutionTwo")
}