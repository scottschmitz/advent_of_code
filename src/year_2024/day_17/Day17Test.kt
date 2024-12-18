package year_2024.day_17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {

  @Test
  fun testSolutionOne1() {
    val text = listOf(
      "Register A: 0",
      "Register B: 0",
      "Register C: 9",
      "",
      "Program: 2,6",
    )
    val day17 = Day17(text)
    val result = day17.solutionOne()

    assertEquals(0, result.a)
    assertEquals(1, result.b)
    assertEquals(9, result.c)
  }

  @Test
  fun testSolutionOne2() {
    val text = listOf(
      "Register A: 10",
      "Register B: 0",
      "Register C: 0",
      "",
      "Program: 5,0,5,1,5,4",
    )
    val day17 = Day17(text)
    val result = day17.solutionOne()

    assertEquals("0,1,2", result.output)
  }

  @Test
  fun testSolutionOne3() {
    val text = listOf(
      "Register A: 2024",
      "Register B: 0",
      "Register C: 0",
      "",
      "Program: 0,1,5,4,3,0",
    )
    val day17 = Day17(text)
    val result = day17.solutionOne()

    assertEquals("4,2,5,6,7,7,7,7,3,1,0", result.output)
    assertEquals(0, result.a)
  }

  @Test
  fun testSolutionOne4() {
    val text = listOf(
      "Register A: 0",
      "Register B: 29",
      "Register C: 0",
      "",
      "Program: 1,7",
    )
    val day17 = Day17(text)
    val result = day17.solutionOne()

    assertEquals(26, result.b)
  }

  @Test
  fun testSolutionOne5() {
    val text = listOf(
      "Register A: 0",
      "Register B: 2024",
      "Register C: 43690",
      "",
      "Program: 4,0",
    )
    val day17 = Day17(text)
    val result = day17.solutionOne()

    assertEquals(44354, result.b)
  }

  @Test
  fun testSolutionOne() {
    val text = listOf(
      "Register A: 729",
      "Register B: 0",
      "Register C: 0",
      "",
      "Program: 0,1,5,4,3,0",
    )
    val day17 = Day17(text)
    val solutionOne = day17.solutionOne()

    assertEquals("4,6,3,5,6,3,5,2,1,0", solutionOne.output)
  }

  @Test
  fun testSolutionTwo() {
    val text = listOf(
      "Register A: 2024",
      "Register B: 0",
      "Register C: 0",
      "",
      "Program: 0,3,5,4,3,0",
    )
    val day17 = Day17(text)
    val solutionTwo = day17.solutionTwo()

    assertEquals(117440, solutionTwo)
  }
}