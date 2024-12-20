package year_2024.day_19

import readInput

class Day19(text: List<String>) {
  val patterns = text[0].split(", ")
  val designs = text.subList(2, text.size)

  fun solutionOne(): Int {
    return designs.count { design ->
      isPossible(design)
    }
  }

  fun solutionTwo(): Long {
    return designs.sumOf { design ->
      possibleCombinations(design)
    }
  }

  val patternMemory = mutableMapOf<String, Boolean>()
  private fun isPossible(design: String): Boolean {
    if (design.isEmpty()){
      return true
    }

    if (patternMemory.containsKey(design)) {
      return patternMemory[design]!!
    }

    for (pattern in patterns) {
      if (design.startsWith(pattern) && isPossible(design.substring(startIndex = pattern.length))) {
        patternMemory[design] = true
        return true
      }
    }

    patternMemory[design] = false
    return false
  }

  val combinationMemory = mutableMapOf<String, Long>()
  private fun possibleCombinations(design: String): Long {
    return combinationMemory.getOrPut(design) {
      if (design.isEmpty()) {
        return 1
      } else {
        patterns.sumOf { pattern ->
          if (design.startsWith(pattern)) {
            possibleCombinations(design.substring(startIndex = pattern.length))
          } else {
            0
          }
        }
      }
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_19/Day19.txt")

  val day19Pt1 = Day19(text)
  val solutionOne = day19Pt1.solutionOne()
  println("Solution 1: $solutionOne")

  val day19Pt2 = Day19(text)
  val solutionTwo = day19Pt2.solutionTwo()
  println("Solution 2: $solutionTwo")
}
