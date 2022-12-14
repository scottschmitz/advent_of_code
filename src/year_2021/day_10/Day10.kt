package year_2021.day_10

import readInput
import java.util.Stack

object Day10 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        return text.sumOf { line ->
            findFirstIllegalCharacter(line)
        }
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Long {
        val solutionValues = text
            .filter { line -> findFirstIllegalCharacter(line) == 0 }
            .map { line -> completeLine(line) }
            .sorted()

        return solutionValues[solutionValues.size / 2]
    }

    private fun findFirstIllegalCharacter(text: String): Int {
        val leftStack = Stack<Char>()

        text.toCharArray().forEach { char ->
            when (char) {
                '(', '[', '{', '<' -> leftStack.push(char)
                ')' -> {
                    if (leftStack.peek() == '(') {
                        leftStack.pop()
                    } else {
                        return 3
                    }
                }
                ']' -> {
                    if (leftStack.peek() == '[') {
                        leftStack.pop()
                    } else {
                        return 57
                    }
                }
                '}' -> {
                    if (leftStack.peek() == '{') {
                        leftStack.pop()
                    } else {
                        return 1197
                    }
                }
                '>' -> {
                    if (leftStack.peek() == '<') {
                        leftStack.pop()
                    } else {
                        return 25137
                    }
                }
            }
        }

        return 0
    }

    private fun completeLine(text: String): Long {
        val leftStack = Stack<Char>()

        text.toCharArray().forEach { char ->
            when (char) {
                '(', '[', '{', '<' -> leftStack.push(char)
                ')' -> {
                    if (leftStack.peek() == '(') {
                        leftStack.pop()
                    }
                }
                ']' -> {
                    if (leftStack.peek() == '[') {
                        leftStack.pop()
                    }
                }
                '}' -> {
                    if (leftStack.peek() == '{') {
                        leftStack.pop()
                    }
                }
                '>' -> {
                    if (leftStack.peek() == '<') {
                        leftStack.pop()
                    }
                }
            }
        }

        var score :Long = 0
        val solution = mutableListOf<Char>()
        while (leftStack.isNotEmpty()) {
            when (leftStack.pop()) {
                '(', -> solution.add(')')
                '[', -> solution.add(']')
                '{' -> solution.add('}')
                '<' -> solution.add('>')
            }
        }

        solution.forEach {
            val value = when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
            score = (score * 5) + value
        }

        return score
    }
}

fun main() {
    val inputText = readInput("year_2021/day_10/Day10.txt")

    val solutionOne = Day10.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day10.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}