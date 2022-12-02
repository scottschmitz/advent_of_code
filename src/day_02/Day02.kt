package day_02

import readInput

enum class Shape(val letters: List<Char>, val pointValue: Int) {
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

            // victories are 6
            (this == ROCK && other == SCISSORS)
                || (this == PAPER && other == ROCK)
                || (this == SCISSORS && other == PAPER) -> score += 6

            // loss is nothing
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
    val myShape: Shape get() {
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

fun main() {

    // A/X = Rock = 1 pt
    // B/Y = Paper = 2 pts
    // C/Z = Scissors = 3 pts

    // 0 = Loss
    // 3 = Draw
    // 6 = Win


    val roundsText = readInput("day_02/Day02")


    val solution1Rounds = roundsText.map {  text ->
        Round(
            elfShape = Shape.fromChar(text[0]),
            myShape = Shape.fromChar(text[2])
        )
    }
    println("Solution 1: Total Score: ${solution1Rounds.sumOf { it.myRoundResult }}")


    val solution2Rounds = roundsText.map { text ->
        RequiredOutcomeRound(
            elfShape = Shape.fromChar(text[0]),
            requiredResult = Result.fromChar(text[2])
        )
    }
    println("Solution 2: Total Score: ${solution2Rounds.sumOf { it.myRoundResult }}")
}
