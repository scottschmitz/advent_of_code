package day_02

import readInput

enum class Shape(val letters: List<Char>, private val pointValue: Int) {
    ROCK(listOf('A', 'X'), 1),
    PAPER(listOf('B', 'Y'), 2),
    SCISSORS(listOf('C', 'Z'),3),
    ;

    companion object {
        fun fromChar(letter: Char): Shape {
            return values().first { shape ->
                shape.letters.contains(letter)
            }
        }
    }

    fun result(other: Shape): Int {
        var score = pointValue

        when {
            // tie = 3
            this == other -> score += 3

            // victories = 6
            (this == ROCK && other == SCISSORS)
                || (this == PAPER && other == ROCK)
                || (this == SCISSORS && other == PAPER) -> score += 6

            // loss = 0
            else -> { /* no-op */ }
        }

        return score
    }
}

enum class Result(val letter: Char) {
    LOSS('X'),
    DRAW('Y'),
    WIN('Z'),
    ;

    companion object {
        fun fromChar(letter: Char): Result {
            return Result.values().first { shape ->
                shape.letter == letter
            }
        }
    }
}

data class Round(
    val elfShape: Shape,
    val myShape: Shape
) {
    val myRoundResult: Int = myShape.result(elfShape)
}

data class RequiredOutcomeRound(
    val elfShape: Shape,
    val requiredResult: Result
) {
    private val myShape: Shape get() {
        return when (requiredResult) {
            Result.WIN -> {
                when (elfShape) {
                    Shape.ROCK -> Shape.PAPER
                    Shape.PAPER -> Shape.SCISSORS
                    Shape.SCISSORS -> Shape.ROCK
                }
            }
            Result.LOSS -> {
                when (elfShape) {
                    Shape.ROCK -> Shape.SCISSORS
                    Shape.PAPER -> Shape.ROCK
                    Shape.SCISSORS -> Shape.PAPER
                }
            }
            Result.DRAW -> elfShape
        }
    }

    val myRoundResult: Int = myShape.result(elfShape)
}

object Day02 {

    /**
     * @return Total Score for all rounds
     */
    fun solutionOne(roundsText: List<String>): Int {
        return roundsText
            .map {  text ->
                Round(
                    elfShape = Shape.fromChar(text[0]),
                    myShape = Shape.fromChar(text[2])
                )
            }
            .sumOf { round ->
                round.myRoundResult
            }
    }

    /**
     * @return Total score from updated rule book
     */
    fun solutionTwo(roundsText: List<String>): Int {
        return roundsText
            .map { text ->
                RequiredOutcomeRound(
                    elfShape = Shape.fromChar(text[0]),
                    requiredResult = Result.fromChar(text[2])
                )
            }
            .sumOf { round ->
                round.myRoundResult
            }
    }
}

fun main() {
    val roundsText = readInput("day_02/Day02.txt")

    val solution1 = Day02.solutionOne(roundsText)
    println("Solution 1: Total Score: $solution1")

    val solution2 = Day02.solutionTwo(roundsText)
    println("Solution 2: Total Score: $solution2")
}
