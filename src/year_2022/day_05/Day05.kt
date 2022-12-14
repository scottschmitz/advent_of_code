package year_2022.day_05

import readInput

data class Rearrangement(
    val quantity: Int,
    val from: Int,
    val to: Int
)

object Day05 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): String {
        val stacks = parseContainers(text)
        val rearrangements = parseRearrangements(text)

        rearrangements.forEach { rearrangement ->
            for (i in 0 until rearrangement.quantity) {
                val topContainer = stacks[rearrangement.from]!!.removeLast()
                stacks[rearrangement.to]!!.add(topContainer)
            }
        }

        var message = ""
        stacks.forEach { (_, containers) ->
            message += containers.last()
        }
        return message
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): String {
        val stacks = parseContainers(text)
        val rearrangements = parseRearrangements(text)

        rearrangements.forEach { rearrangement ->
            val fromStack = stacks[rearrangement.from]!!
            val toStack = stacks[rearrangement.to]!!

            val toMove = mutableListOf<Char>()
            for (i in 0 until rearrangement.quantity) {
                toMove.add(fromStack.removeLast())
            }

            toStack.addAll(toMove.reversed())
        }

        var message = ""
        stacks.forEach { (_, containers) ->
            message += containers.last()
        }
        return message
    }

    private fun parseContainers(text: List<String>): Map<Int, MutableList<Char>> {
        val blankLine = text.indexOfFirst { it.isEmpty() }
        val stacksText = text[blankLine -1]
        val stackPositions = stacksText.mapIndexedNotNull { index, c ->
            if (c.isDigit()) {
                c.digitToInt() to index
            } else { null }
        }.toMap()

        val stacks = mutableMapOf<Int, MutableList<Char>>()
        for (i in blankLine - 1 downTo  0) {
            val line = text[i]

            stackPositions.forEach { (stackId, index) ->
                if (line.length >= index) {
                    val container = line[index]
                    if (container.isLetter()) {

                        if (stacks[stackId] == null) {
                            stacks[stackId] = mutableListOf<Char>()
                        }
                        stacks[stackId]!!.add(container)
                    }
                }
            }
        }


        return stacks
    }

    private fun parseRearrangements(text: List<String>): List<Rearrangement> {
        val rearrangements = mutableListOf<Rearrangement>()
        text.forEach { line ->
            if (line.startsWith("move")) {
                // is a rearrangement
                val parts = line.split(" ")
                rearrangements.add(
                    Rearrangement(
                            quantity = parts[1].toInt(),
                            from = parts[3].toInt(),
                            to = parts[5].toInt()
                    )
                )
            }
        }
        return rearrangements
    }
}

fun main() {
    val inputText = readInput("year_2022/day_05/Day05.txt")

    val solutionOne = Day05.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day05.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}