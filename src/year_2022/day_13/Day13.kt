package year_2022.day_13

import readInput

object Day13 {

    val compare: (String, String) -> Int = { s1: String, s2: String ->
        if (inOrder(s1.toMutableList(), s2.toMutableList())){
            -1
        } else {
            1
        }
    }

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        return parseText(text)
            .mapIndexed { index, (left, right) ->
                val inOrder = compare(left, right)
                if (inOrder != 1) {
                    index + 1
                } else {
                    0
                }
            }
            .sum()
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val pairs = parseText(text)
        val two = "[[2]]"
        val six = "[[6]]"

        val sorted = (pairs.flatten() + two + six).sortedWith(compare)
        return (sorted.indexOf(two) + 1) * (sorted.indexOf(six) + 1)
    }

    private fun parseText(text: List<String>): List<List<String>> {
        return text
            .filter { it.isNotEmpty() }
            .windowed(2, 2)
    }

    private fun inOrder(l: MutableList<Char>, r: MutableList<Char>): Boolean {
        val (lk, rk) = l.getNumber() to r.getNumber()

        if (l[0] == '[' && rk != null) {
            r.addBrackets(1+rk/10)
        }

        if (r[0] == '[' && lk != null) {
            l.addBrackets(1+lk/10)
        }

        return when {
            (l[0] == ']' && r[0] != ']') -> true
            (l[0] != ']' && r[0] == ']') -> false
            (lk == (rk ?: -1)) -> inOrder(l.subList(1+lk/10, l.size), r.subList(1+rk!!/10, r.size))
            (lk != null && rk != null) -> lk < rk
            else -> inOrder(l.subList(1, l.size), r.subList(1, r.size))
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_13/Day13.txt")

    val solutionOne = Day13.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day13.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}


fun MutableList<Char>.addBrackets(len: Int) {
    add(len, ']')
    add(0, '[')
}

fun List<Char>.getNumber(): Int? {
    return when (first().isDigit()) {
        true -> takeWhile { it.isDigit() }.joinToString("").toInt()
        else -> null
    }
}