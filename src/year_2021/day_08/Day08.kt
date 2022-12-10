package year_2021.day_08

import readInput

data class Puzzle(
    val uniqueNumbers: List<ClockNumber>,
    val toSolve: List<ClockNumber>
)

data class ClockNumber(
    val segments: List<Char>
)

object Day08 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val puzzles = parseText(text)
        return puzzles.sumOf { puzzle ->
            puzzle.toSolve
                .filter { it.segments.size in listOf(2,3,4,7) }
                .size
        }
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val puzzles = parseText(text)

        return puzzles.sumOf { puzzle ->
            val map = puzzle.uniqueNumbers.groupBy { it.segments.size }

            val numberOne = map[2]!!.first()
            val numberSeven = map[3]!!.first()
            val numberFour = map[4]!!.first()

            // All of the remaining numbers have the bottom line
            val fiveSegmentNumbers = map[5]!! // 2, 3, 5
            val sixSegmentNumbers = map[6]!! // 0, 6, 9

            // the only letter we can be 100% confident in immediately
            val top = numberSeven.segments.filterNot { it in numberOne.segments }.first()

            // if in 4 but not in 1 and in all of 6 segments == upper left
            val topLeft = numberFour.segments
                .filterNot { it in numberOne.segments }
                .first { fourSegmentChar -> sixSegmentNumbers.all { it.segments.contains(fourSegmentChar) } }

            // if in 1 and all of 6 segments == bottom right
            val bottomRight = numberOne.segments.first { oneSegmentNumbers -> sixSegmentNumbers.all { it.segments.contains(oneSegmentNumbers) }}

            // bottom right is the only other in the 1
            val topRight = numberOne.segments.first { it != bottomRight }

            // middle is the only remaining letter
            val middle = numberFour.segments.first { it !in listOf(topLeft, topRight, bottomRight) }

            // bottom is in all of the 5 segments... just filter out already used
            val remainingLetters = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g').filterNot {it in listOf(top, topLeft, topRight, bottomRight, middle) }
            val bottom = remainingLetters.first { remainingLetter -> fiveSegmentNumbers.all { it.segments.contains(remainingLetter) } }

            // bottom left is just whats left over
            val bottomLeft = remainingLetters.first { it != bottom }

//            println("top $top")
//            println("topLeft $topLeft")
//            println("topRight $topRight")
//            println("middle $middle")
//            println("bottomLeft $bottomLeft")
//            println("bottomRight $bottomRight")
//            println("bottom $bottom")

            val one = listOf(topRight, bottomRight)
            val two = listOf(top, topRight, middle, bottomLeft, bottom)
            val three = listOf(top, topRight, middle, bottomRight, bottom)
            val four = listOf(topLeft, middle, topRight, bottomRight)
            val five = listOf(top, topLeft, middle, bottomRight, bottom)
            val six = listOf(top, topLeft, middle, bottomLeft, bottomRight, bottom)
            val seven = listOf(top, topRight, bottomRight)
            val eight = listOf(top, topLeft, topRight, middle, bottomLeft, bottomRight, bottom)
            val nine = listOf(top, topLeft, topRight, middle, bottomRight, bottom)

            var fullNumber = ""
            puzzle.toSolve.forEach { number ->
                fullNumber += when {
                    number.segments.all { it in one } && number.segments.size == one.size -> "1"
                    number.segments.all { it in two } && number.segments.size == two.size -> "2"
                    number.segments.all { it in three } && number.segments.size == three.size -> "3"
                    number.segments.all { it in four } && number.segments.size == four.size -> "4"
                    number.segments.all { it in five } && number.segments.size == five.size -> "5"
                    number.segments.all { it in six } && number.segments.size == six.size -> "6"
                    number.segments.all { it in seven } && number.segments.size == seven.size -> "7"
                    number.segments.all { it in eight } && number.segments.size == eight.size -> "8"
                    number.segments.all { it in nine } && number.segments.size == nine.size -> "9"
                    else -> "0"
                }
            }

            Integer.parseInt(fullNumber)
        }
    }

    private fun parseText(text: List<String>): List<Puzzle> {
        return text.map { line ->
            val split = line.split("|")
            val segments = split[0]
                .split(" ")
                .map { ClockNumber(it.toList()) }
            val toSolve = split[1]
                .split(" ")
                .map { ClockNumber(it.toList()) }

            Puzzle(
                uniqueNumbers = segments,
                toSolve = toSolve
            )
        }
    }
}

fun main() {
    val inputText = readInput("year_2021/day_08/Day08.txt")

    val solutionOne = Day08.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day08.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}