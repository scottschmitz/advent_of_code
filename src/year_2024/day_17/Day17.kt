package year_2024.day_17

import readInput
import util.pow

class Day17(text: List<String>) {
  val _aRegister: Long
  var aRegister: Long

  val _bRegister: Long
  var bRegister: Long

  val _cRegister: Long
  var cRegister: Long
  var directions: List<Long>

  init {
    _aRegister = text[0].split(": ")[1].toLong()
    aRegister = _aRegister

    _bRegister = text[1].split(": ")[1].toLong()
    bRegister = _bRegister

    _cRegister = text[2].split(": ")[1].toLong()
    cRegister = _cRegister

    directions = text[4].split(": ")[1].split(",").map { it.toLong() }
  }

  fun solutionOne(): Result {
    var position = 0
    val outputs = mutableListOf<Int>()

    while (position < directions.size) {
      val opcode = directions[position].toInt()
      val operand = directions[position + 1].toInt()
      val operandValue = when (operand) {
        in 0..3 -> operand.toLong()
        4 -> aRegister
        5 -> bRegister
        6 -> cRegister
        else -> -1
      }

      var isJumping = false
      when (opcode) {
        0 -> aRegister = adv(aRegister, operandValue)
        1 -> bRegister = bxl(bRegister, operand.toLong()) // use the literal value
        2 -> bRegister = bst(operandValue)
        3 -> {
          isJumping = jnz(aRegister)
          if (isJumping) {
            position = operand // intentionally use the literal value
          }
        }
        4 -> bRegister = bxc(bRegister, cRegister, operandValue)
        5 -> outputs.add(out(operandValue))
        6 -> bRegister = bdv(aRegister, operandValue)
        7 -> cRegister = cdv(aRegister, operandValue)
        else -> throw IllegalArgumentException("Invalid opcode")
      }

      if (!isJumping) {
        position += 2
      }
    }

    return Result(aRegister, bRegister, cRegister, outputs.joinToString(","))
  }

  fun solutionTwo(): Long {
    val originalDirections = directions.joinToString(",")
    var i = 265_000_000_000_000L
    while (true) {
      println(i)

      aRegister = i
      bRegister = _bRegister
      cRegister = _cRegister

      var position = 0
      val output = mutableListOf<Int>()

      while (position < directions.size) {
        val opcode = directions[position].toInt()
        val operand = directions[position + 1].toInt()
        val operandValue = when (operand) {
          in 0..3 -> operand.toLong()
          4 -> aRegister
          5 -> bRegister
          6 -> cRegister
          else -> -1
        }

        var isJumping = false
        when (opcode) {
          0 -> aRegister = adv(aRegister, operandValue)
          1 -> bRegister = bxl(bRegister, operand.toLong()) // use the literal value
          2 -> bRegister = bst(operandValue)
          3 -> {
            isJumping = jnz(aRegister)
            if (isJumping) {
              position = operand // intentionally use the literal value
            }
          }
          4 -> bRegister = bxc(bRegister, cRegister, operandValue)
          5 -> {
            output += out(operandValue)
            if (originalDirections.startsWith(output.joinToString(","))) {
              break
            }
          }
          6 -> bRegister = bdv(aRegister, operandValue)
          7 -> cRegister = cdv(aRegister, operandValue)
          else -> throw IllegalArgumentException("Invalid opcode")
        }

        if (!isJumping) {
          position += 2
        }
      }

      val outputString = output.joinToString(",")
      if (outputString == originalDirections) {
        return i
      }

      i++
    }
  }


  /**
   * @return value for the A register
   */
  private fun adv(aRegister: Long, operand: Long): Long {
    return (aRegister / 2L.pow(operand)).toInt().toLong()
  }

  /**
   * @return value for the B register
   */
  private fun bxl(bRegister: Long, operand: Long): Long {
    return bRegister xor operand
  }

  /**
   * @return value for the B register
   */
  private fun bst(operand: Long): Long {
    return operand % 8
  }

  /**
   * @return true if should jump to operand
   */
  private fun jnz(aRegister: Long): Boolean {
    return when (aRegister) {
      0L -> false
      else -> true
    }
  }

  /**
   * @return value for the register B
   */
  private fun bxc(bRegister: Long, cRegister: Long, operand: Long): Long {
    return bRegister xor cRegister
  }

  /**
   * @return value to be outputted
   */
  private fun out(operand: Long): Int {
    return (operand % 8).toInt()
  }

  /**
   * @return value for the register B
   */
  private fun bdv(aRegister: Long, operand: Long): Long {
    return (aRegister / 2L.pow(operand)).toInt().toLong()
  }

  /**
   * @return value for the register C
   */
  private fun cdv(aRegister: Long, operand: Long): Long {
    return aRegister / 2L.pow(operand)
  }

  data class Result(val a: Long, val b: Long, val c: Long, val output: String)
}

fun main() {
  val text = readInput("year_2024/day_17/Day17.txt")

  val day17 = Day17(text)
  val solutionOne = day17.solutionOne()
  println("Solution 1: $solutionOne")

  val solutionTwo = day17.solutionTwo()
  println("Solution 2: $solutionTwo")
}
