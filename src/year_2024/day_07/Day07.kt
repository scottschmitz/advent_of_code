package year_2024.day_07

import readInput
import java.math.BigInteger

object Day07 {
  fun solutionOne(text: List<String>): BigInteger {
    val equations = parseEquations(text)
    return equations.filter { equation ->
      findCombinationSolution1(equation)
    }.sumOf { equation -> equation.answer }
  }

  fun solutionTwo(text: List<String>): BigInteger {
    val equations = parseEquations(text)
    return equations.filter { equation ->
      findCombinationSolution2(equation)
    }.sumOf { equation -> equation.answer }
  }

  private fun parseEquations(text: List<String>): List<Equation> {
    return text.map { line ->
      val (answer, numbers) = line.split(": ")
      Equation(answer.toBigInteger(), numbers.split(" ").map { it.toBigInteger() })
    }
  }

  private fun findCombinationSolution1(equation: Equation): Boolean {
    fun helper(index: Int, currentValue: BigInteger): Boolean {
      if (index == equation.numbers.size) {
        return currentValue == equation.answer
      }
      val nextNumber = equation.numbers[index]
      return helper(index + 1, currentValue + nextNumber) || helper(index + 1, currentValue * nextNumber)
    }
    return helper(1, equation.numbers[0])
  }

  private fun findCombinationSolution2(equation: Equation): Boolean {
    fun helper(index: Int, currentValue: BigInteger): Boolean {
      if (index == equation.numbers.size) {
        return currentValue == equation.answer
      }
      val nextNumber = equation.numbers[index]
      return helper(index + 1, currentValue + nextNumber)
          || helper(index + 1, currentValue * nextNumber)
          || helper(index + 1, (currentValue.toString() +  nextNumber.toString()).toBigInteger())
    }
    return helper(1, equation.numbers[0])
  }

  data class Equation(
    val answer: BigInteger,
    val numbers: List<BigInteger>
  )
}

fun main() {
  val text = readInput("year_2024/day_07/Day07.txt")

  val solutionOne = Day07.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day07.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
