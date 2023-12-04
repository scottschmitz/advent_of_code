package year_2023.day_04

import readInput

data class ScratchCard(
    val cardNumber: Int,
    val winningNumbers: List<Int>,
    val myNumbers: List<Int>
) {
    val matchingNumbers: Set<Int> get() {
        return winningNumbers.intersect(myNumbers.toSet())
    }
    val score: Int get() {
        var points = 0
        matchingNumbers.forEach { _ ->
            points = if (points == 0) 1 else points * 2
        }
        return points
    }
}
object Day04 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        return parseCards(text).sumOf { it.score }
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val originalCards = parseCards(text)
        val cardCounts = mutableMapOf<Int, Int>() // card number to count

        originalCards.forEach { card ->
            cardCounts[card.cardNumber] = 1
        }

        originalCards.forEachIndexed { index, scratchCard ->
            val cardCount = cardCounts[scratchCard.cardNumber] ?: 1
            for (winIndex in scratchCard.cardNumber + 1..scratchCard.cardNumber + scratchCard.matchingNumbers.size ) {
                cardCounts[winIndex] = ((cardCounts[winIndex] ?: 0) + cardCount)
            }
        }

        return cardCounts.toList().sumOf { it.second }
    }

    private fun parseCards(text: List<String>): List<ScratchCard> {
        return text.map { line ->
            val split = line.split(":")

            val cardNumber = split[0].split(" ").filter { it.isNotEmpty() }[1].replace(":", "").toInt()
            val numbers = split[1].split("|")

            val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val myNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

            ScratchCard(cardNumber, winningNumbers, myNumbers)
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_04/Day04.txt")
    Thread.sleep(1000)


    val solutionOne = Day04.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day04.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
