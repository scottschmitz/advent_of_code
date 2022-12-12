package year_2022.day_10

import readInput

sealed class Operation(var cycleCount: Int) {
    object NoOp: Operation(1)
    class Add(
        val quantity: Int
    ): Operation(2) {
        override fun toString(): String {
            return "Add $quantity - in $cycleCount cyclces"
        }
    }
}

object Day10 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val operations = parseCommands(text)

        var cycleCount = 1
        var currentOperation = 0
        var currentValue = 1

        var solution = 0

        while (currentOperation < operations.size) {
            cycleCount += 1
            val operation = operations[currentOperation]
            operation.cycleCount -= 1

            if (operation.cycleCount < 1) {
                if (operation is Operation.Add) {
                    currentValue += operation.quantity
                }

                currentOperation +=1
            }

            if (cycleCount == 20 || (cycleCount - 20) % 40 == 0) {
                solution += (cycleCount * currentValue)
            }
        }
        return solution
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): List<String> {
        val operations = parseCommands(text)

        var cycleCount = 1
        var currentOperation = 0
        var currentValue = 1

        var solution = ""
        while (currentOperation < operations.size) {
            val operation = operations[currentOperation]
            operation.cycleCount -= 1

            solution += if ((cycleCount -1) % 40 in listOf(currentValue -1, currentValue, currentValue +1)) {
                "#"
            } else {
                "."
            }

            if (operation.cycleCount < 1) {
                if (operation is Operation.Add) {
                    currentValue += operation.quantity
                }

                currentOperation +=1
            }
            cycleCount += 1
        }

        val screen = solution.windowed(40, 40)

        screen.forEach {
            println(it)
        }

        return screen
    }

    private fun parseCommands(text: List<String>): List<Operation> {
        return text.map { line ->
            val split = line.split(" ")
            when (split.size) {
                1 -> Operation.NoOp
                else -> Operation.Add(
                    quantity = Integer.parseInt(split[1])
                )
            }
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_10/Day10.txt")

    val solutionOne = Day10.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day10.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}