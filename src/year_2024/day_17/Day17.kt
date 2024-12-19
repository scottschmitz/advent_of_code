package year_2024.day_17

import readInput
import util.pow

class Day17(val text: List<String>) {

  private var directions = text[4].split(": ")[1].split(",").map { it.toInt() }

  fun solutionOne(): Result {
    val a = text[0].split(": ")[1].toLong()
    val b = text[1].split(": ")[1].toLong()
    val c = text[2].split(": ")[1].toLong()
    return solve(a, b, c)
  }

  fun solutionTwo(): Long {
    val initialB = text[1].split(": ")[1].toLong()
    val initalC = text[2].split(": ")[1].toLong()

    fun findA(currentA: Long = 0): Long? =
      (currentA..currentA + 8).firstNotNullOfOrNull { a ->
        val result = solve(a, initialB, initalC)

        if (directions.takeLast(result.output.size) == result.output) {
          if (directions == result.output) {
            a
          } else {
            findA(maxOf(a shl 3, 8))
          }
        } else {
          null
        }
      }

    return findA() ?: error("No solution")
  }

  private fun solve(aRegister: Long, bRegister: Long, cRegister: Long): Result {
    var a = aRegister
    var b = bRegister
    var c = cRegister

    val combo = { operand: Int ->
      when (operand) {
        in 0..3 -> operand.toLong()
        4 -> a
        5 -> b
        6 -> c
        else -> error("Invalid operand: $operand")
      }
    }

    var position = 0
    val outputs = mutableListOf<Int>()

    while (position in directions.indices) {
      val opcode = directions[position]
      val operand = directions[position + 1]

      when (opcode) {
        0 -> a = a shr combo(operand).toInt() // adv
        1 -> b = b xor operand.toLong() // bxl
        2 -> b = combo(operand) % 8 // bst
        3 -> if (a != 0L) { // jnz
          position = operand
          continue
        }
        4 -> b = b xor c // bxc
        5 -> outputs.add((combo(operand) % 8).toInt()) // out
        6 -> b = a shr combo(operand).toInt() // bxs
        7 -> c = a shr combo(operand).toInt() // cxs
      }

      position += 2
    }

    return Result(a, b, c, outputs)
  }

  data class Result(val a: Long, val b: Long, val c: Long, val output: List<Int>)
}

fun main() {
  val text = readInput("year_2024/day_17/Day17.txt")

  val day17 = Day17(text)
  val solutionOne = day17.solutionOne()
  println("Solution 1: ${solutionOne.output.joinToString(",")}")

  val solutionTwo = day17.solutionTwo()
  println("Solution 2: $solutionTwo")
}
