package year_2024.day_05

import readInput

object Day05 {
  fun solutionOne(text: List<String>): Int {
    val rules = parsePrintingRules(text)

    return rules.pagesToPrint
      .filter { update -> isOrdered(update, rules.pageOrdering) }
      .sumOf { update -> update[update.size / 2] }
  }

  fun solutionTwo(text: List<String>): Int {
    val rules = parsePrintingRules(text)

    return rules.pagesToPrint.filterNot { update -> isOrdered(update, rules.pageOrdering) }.map { update ->
      var printOrder = update
      while (!isOrdered(printOrder, rules.pageOrdering)) {
        val newOrder = mutableListOf<Int>()
        printOrder.forEach { page ->
          val mustBeBeforeRule = rules.pageOrdering.filter { (_, after) -> after == page }.map { it.first }
          val mustBeAfterRule = rules.pageOrdering.filter { (before, _) -> before == page }.map { it.second }

          val mustBeBeforeIndex = newOrder.indexOfLast { mustBeBeforeRule.contains(it) }
          val mustBeAfterIndex = newOrder.indexOfFirst { mustBeAfterRule.contains(it) }

          if (mustBeBeforeIndex == -1) {
            if (mustBeAfterIndex == -1) {
              newOrder.add(page)
            } else {
              newOrder.add(mustBeAfterIndex, page)
            }
          } else {
            newOrder.add(mustBeBeforeIndex + 1, page)
          }
        }
        printOrder = newOrder
      }
      printOrder
    }
      .sumOf { update -> update[update.size / 2] }
  }

  private fun parsePrintingRules(text: List<String>): PrintingRules {
    val separator = text.indexOfFirst { it.isEmpty() }
    val pageOrdering = text.subList(0, separator).map { line ->
      val (from, to) = line.split("|").map { it.toInt() }
      from to to
    }
    val pagesToPrint = text.subList(separator + 1, text.size).map { line ->
      line.split(",").map { it.toInt() }
    }
    return PrintingRules(
      pageOrdering = pageOrdering,
      pagesToPrint = pagesToPrint
    )
  }

  private fun isOrdered(pages: List<Int>, ordering: List<Pair<Int, Int>>): Boolean {
    val position = pages.withIndex().associate { it.value to it.index }
    return ordering.all { (from, to) ->
      position[from]?.let { fromPos ->
        position[to]?.let { toPos ->
          fromPos < toPos
        } ?: true
      } ?: true
    }
  }
}

data class PrintingRules(
  val pageOrdering: List<Pair<Int, Int>>,
  val pagesToPrint: List<List<Int>>
)

fun main() {
  val text = readInput("year_2024/day_05/Day05.txt")

  val solutionOne = Day05.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day05.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
