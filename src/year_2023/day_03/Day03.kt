package year_2023.day_03

import readInput
import kotlin.math.max
import kotlin.math.min

data class PartNumber(
    val lineNumber: Int,
    val startPos: Int,
    val endPos: Int,
    val number: Int
)

fun String.containsSymbol(): Boolean {
    return this.any { !it.isDigit() && it != '.' }
}

object Day03 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val numbers = mutableSetOf<PartNumber>()

        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                getNumberAtLocation(line, y, x)?.let { numbers.add(it) }
            }
        }

        val partNumbers = mutableSetOf<PartNumber>()
        numbers.forEach { partNumber ->
            val start = max(0, partNumber.startPos - 1)
            val end = min(text.first().length - 1, partNumber.endPos + 1)

            if (partNumber.lineNumber > 0) {
                if (text[partNumber.lineNumber - 1].substring(start, end + 1).containsSymbol()) {
                    partNumbers.add(partNumber)
                }
            }

            if (text[partNumber.lineNumber].substring(start, end + 1).containsSymbol()) {
                partNumbers.add(partNumber)
            }

            if (partNumber.lineNumber + 1 < text.size) {
                if (text[partNumber.lineNumber + 1].substring(start, end + 1).containsSymbol()) {
                    partNumbers.add(partNumber)
                }
            }
        }

        return partNumbers.sumOf { it.number }
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        var total = 0
        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, character ->
                if (character == '*') {
                    val parts = mutableSetOf<PartNumber>()

                    // above
                    if (y > 0) {
                        getNumberAtLocation(text[y - 1],  y-1,x - 1)?.let { parts.add(it) }
                        getNumberAtLocation(text[y - 1], y-1, x)?.let { parts.add(it) }
                        getNumberAtLocation(text[y - 1], y-1, x + 1)?.let { parts.add(it) }
                    }

                    // same line
                    getNumberAtLocation(line, y, x - 1)?.let { parts.add(it) }
                    getNumberAtLocation(line, y, x + 1)?.let { parts.add(it) }

                    // below
                    if (y + 1 < text.size) {
                        getNumberAtLocation(text[y + 1], y + 1, x - 1)?.let { parts.add(it) }
                        getNumberAtLocation(text[y + 1], y+1, x)?.let { parts.add(it) }
                        getNumberAtLocation(text[y + 1], y+1, x + 1)?.let { parts.add(it) }
                    }

                    if (parts.size == 2) {
                        val partsList = parts.toList()
                        total += partsList[0].number * partsList[1].number
                    }
                }
            }
        }
        return total
    }

    private fun getNumberAtLocation(line: String, y: Int, x: Int): PartNumber? {
        if (!line[x].isDigit()) {
            return null
        }

        var startPos = x
        var endPos = x
        var fullString = line[x].toString()

        while (startPos > 0) {
            if (line[startPos -1].isDigit()) {
                startPos -= 1
                fullString = "${line[startPos]}$fullString"
            }
            else {
                break
            }
        }

        while (endPos < line.length - 1) {
            if (line[endPos + 1].isDigit()) {
                endPos += 1
                fullString = "$fullString${line[endPos]}"
            } else {
                break
            }
        }

        return PartNumber(y, startPos, endPos, fullString.toInt())
    }
}

fun main() {
    val text = readInput("year_2023/day_03/Day03.txt")

    val solutionOne = Day03.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day03.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
