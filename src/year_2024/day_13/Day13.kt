package year_2024.day_13

import readInput
import util.Point
import util.PointL
import util.x
import util.y

object Day13 {
  fun solutionOne(text: List<String>): Long {
    return parseText(text)
      .mapNotNull { machine -> calculatePresses(machine) }
      .sumOf { (aPresses, bPresses) -> 3 * aPresses + bPresses  }
  }

  fun solutionTwo(text: List<String>): Long {
    return parseText(text)
      .map { machine ->
        machine.prize = PointL(machine.prize.x() + 10000000000000, machine.prize.y()+10000000000000)
        machine
      }
      .mapNotNull { machine -> calculatePresses(machine) }
      .sumOf { (aPresses, bPresses) -> 3 * aPresses + bPresses  }
  }

  fun calculatePresses(machine: PrizeMachine): Pair<Long, Long>? {
    with (machine) {
      val bPresses = (a.x() * prize.y() - a.y() * prize.x()) / (a.x() * b.y() - a.y() * b.x())
      val aPresses = (prize.y() - b.y() * bPresses) / a.y()

      return if (
        a.x() * aPresses + b.x() * bPresses == prize.x()
        && a.y() * aPresses + b.y() * bPresses == prize.y()
      ) {
        aPresses to bPresses
      }
      else {
        null
      }
    }
  }

  private fun parseText(text: List<String>): List<PrizeMachine> {
    val buttonRegex = """X\+(\d+), Y\+(\d+)""".toRegex()
    val prizeRegex = """X=(\d+), Y=(\d+)""".toRegex()

    val machines = mutableListOf<PrizeMachine>()
    var a: Point? = null
    var b: Point? = null

    text.forEach { line ->
      when {
        line.startsWith("Button A") -> a = buttonRegex.find(line)!!.destructured.let { (x, y) -> Point(x.toInt(), y.toInt()) }
        line.startsWith("Button B") -> b = buttonRegex.find(line)!!.destructured.let { (x, y) -> Point(x.toInt(), y.toInt()) }
        line.startsWith("Prize") -> {
          machines.add(
            PrizeMachine(
              a!!,
              b!!,
              prizeRegex.find(line)!!.destructured.let { (x, y) -> PointL(x.toLong(), y.toLong()) }
            )
          )
          a = null
          b = null
        }
      }
    }

    return machines
  }
}

data class PrizeMachine(val a: Point, val b: Point, var prize: PointL)

fun main() {
  val text = readInput("year_2024/day_13/Day13.txt")

  val solutionOne = Day13.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day13.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
