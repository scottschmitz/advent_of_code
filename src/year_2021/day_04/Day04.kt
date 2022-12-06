package year_2021.day_04

import readInput
import java.lang.NumberFormatException

data class BingoSquare(
    val number: Int,
    var marked: Boolean = false
)

data class Board(
    val rows: List<List<BingoSquare>>
) {
    val hasWon: Boolean get() {
        val rowWin = rows.any { row ->
            row.all { square -> square.marked }
        }

        var columnWin = false
        for (i in rows.first().indices) {
            columnWin = columnWin || rows.all { row -> row[i].marked }
        }

        return rowWin || columnWin
    }

    /**
     * @return true if creates bingo
     */
    fun mark(number: Int): Boolean {
        rows.forEach { row ->
            row
                .filter { square -> square.number == number }
                .forEach { square -> square.marked = true }
        }
        return hasWon
    }

    fun sumUnmarked(): Int {
        return rows.sumOf { row ->
            row
                .filterNot { square -> square.marked }
                .sumOf { square -> square.number }
        }
    }
}

object Day04 {

    /**
     * @return score for the first board to win
     */
    fun solutionOne(text: List<String>): Int {
        val drawnNumbers = text
            .first()
            .split(",")
            .map { it.toInt() }
        val boards = parseBoards(text.subList(2, text.size))

        drawnNumbers.forEach { number ->
            boards.forEach { board ->
                if (board.mark(number)) {
                    return board.sumUnmarked() * number
                }
            }
        }

        return -1
    }

    /**
     * @return score of the last board to win
     */
    fun solutionTwo(text: List<String>): Int {
        val drawnNumbers = text
                .first()
                .split(",")
                .map { it.toInt() }
        val boards = parseBoards(text.subList(2, text.size))

        drawnNumbers.forEach { number ->
            boards.forEach { board ->
                if (board.mark(number)) {
                    if (boards.all { board -> board.hasWon }) {
                        return board.sumUnmarked() * number
                    }
                }
            }
        }

        return -1
    }

    private fun parseBoards(text: List<String>): List<Board> {
        val boards = mutableListOf<Board>()
        var rows = mutableListOf<List<BingoSquare>>()

        text.forEach { line ->
            if (line.isEmpty()) {
                boards.add(Board(rows))
                rows = mutableListOf()
            } else {
                rows.add(line.split(" ").mapNotNull {
                    try {
                        BingoSquare(it.toInt())
                    } catch (e: NumberFormatException) {
                        null
                    }
                })
            }
        }
        boards.add(Board(rows))


        return boards
    }
}

fun main() {
    val inputText = readInput("year_2021/day_04/Day04.txt")

    val solutionOne = Day04.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day04.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}