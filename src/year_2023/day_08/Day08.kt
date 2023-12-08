package year_2023.day_08

import readInput
import util.lcm

data class Node(
    val name: String,
    val left: String,
    val right: String
)

object Day08 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Long {
        val pattern = text[0]
        val nodes = text.drop(2).map { parseNode(it) }

        val theMap = mutableMapOf<String, Node>()
        nodes.forEach { theMap[it.name] = it }

        val startingNode: Node = theMap["AAA"]!!
        return calculateMoves(pattern, startingNode, theMap) { it.name == "ZZZ"}
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Long {
        val pattern = text[0]
        val nodes = text.drop(2).map { parseNode(it) }

        val theMap = mutableMapOf<String, Node>()
        nodes.forEach { theMap[it.name] = it }

        val startingNodes = nodes.filter { it.name.endsWith('A') }.toMutableList()
        val cycleLengths = startingNodes.map { node ->
            calculateMoves(pattern, node, theMap) { it.name.endsWith('Z') }
        }

        return cycleLengths.lcm()
    }

    private fun parseNode(line: String): Node {
        // AAA = (BBB, CCC)
        val split = line.split(" = ")

        val node = split[0]

        val connections = split[1].filterNot { it == '(' || it == ')' }.split(", ")
        val left = connections[0]
        val right = connections[1]

        return Node(node, left, right)
    }

    private fun calculateMoves(pattern: String, startingNode: Node, theMap: Map<String, Node>, destinationCheck: (Node) -> Boolean): Long {
        var count = 0L
        var currentNode = startingNode

        while (true) {
            for(i in pattern.indices) {
                val direction = pattern[i]
                currentNode = when (direction) {
                    'L' -> theMap[currentNode.left]!!
                    'R' -> theMap[currentNode.right]!!
                    else -> throw IllegalArgumentException("Unknown direction.")
                }
                count += 1

                if (destinationCheck(currentNode)) {
                    return count
                }
            }
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_08/Day08.txt")

    val solutionOne = Day08.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day08.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
