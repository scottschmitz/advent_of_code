package year_2024.day_13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import util.Point
import util.PointL

internal class Day13Test {
  @Test
  fun textCoinCalculationA() {
    val machine = PrizeMachine(Point(94, 34), Point(22, 67), PointL(8400, 5400))
    val (aPresses, bPresses) = Day13.calculatePresses(machine)!!

    assertEquals(80, aPresses)
    assertEquals(40, bPresses)
  }

  @Test
  fun textCoinCalculationB() {
    val machine = PrizeMachine(Point(26, 66), Point(67, 21), PointL(12748, 12176))
    val presses = Day13.calculatePresses(machine)

    assertNull(presses)
  }

  @Test
  fun textCoinCalculationC() {
    val machine = PrizeMachine(Point(17, 86), Point(84, 37), PointL(7870, 6450))
    val (aPresses, bPresses) = Day13.calculatePresses(machine)!!

    assertEquals(38, aPresses)
    assertEquals(86, bPresses)
  }

  @Test
  fun textCoinCalculationD() {
    val machine = PrizeMachine(Point(69, 23), Point(27, 71), PointL(18641, 10279))
    val presses = Day13.calculatePresses(machine)

    assertNull(presses)
  }


  val sampleText = listOf(
    "Button A: X+94, Y+34",
    "Button B: X+22, Y+67",
    "Prize: X=8400, Y=5400",
    "",
    "Button A: X+26, Y+66",
    "Button B: X+67, Y+21",
    "Prize: X=12748, Y=12176",
    "",
    "Button A: X+17, Y+86",
    "Button B: X+84, Y+37",
    "Prize: X=7870, Y=6450",
    "",
    "Button A: X+69, Y+23",
    "Button B: X+27, Y+71",
    "Prize: X=18641, Y=10279",
  )
  @Test
  fun testSolutionOne() {
    val solutionOne = Day13.solutionOne(sampleText)

    assertEquals(480, solutionOne)
  }

  @Test
  fun testSolutionTwo() {
    val solutionTwo = Day13.solutionTwo(sampleText)

    assertEquals(875318608908, solutionTwo)
  }
}