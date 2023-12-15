package year_2023.day_15

import readInput

object Day15 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val values = parseInput(text).map { sequence ->
            var currValue = 0
            sequence.forEach { char ->
                currValue += char.code
                currValue *= 17
                currValue %= 256
            }
            sequence to currValue
        }

//        values.forEach { println("${it.first} becomes ${it.second}") }

        return values.sumOf { it.second }
    }

    data class Sequence(
        val boxNumber: Int,
        val label: String,
        val modifier: Char,
        val focalLength: Int?
    )


    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val sequences = parseInput(text).map { sequence ->
            var boxNumber = 0

            val label = sequence.split("=").first().split("-").first()
            label.forEach { char ->
                boxNumber += char.code
                boxNumber *= 17
                boxNumber %= 256
            }

            val focalLength = try {
                sequence.split("=").last().split("-").last().toInt()
            } catch(e: Exception) {
                null
            }

            Sequence(
                boxNumber,
                label,
                if (sequence.contains("=")) '=' else '-',
                focalLength
            )
        }

        // map<BoxNumber, List<Label, Focal>
        val map = mutableMapOf<Int, MutableList<Sequence>>()

        sequences.forEach { sequence ->
            if (map.containsKey(sequence.boxNumber)) {
                val box = map[sequence.boxNumber]!!

                if (sequence.modifier == '=') {
                    val exists = box.indexOfFirst { it.label == sequence.label }
                    if (exists == -1) {
                        box.add(sequence)
                    } else {
                        box[exists] = sequence
                    }
                } else {
                    val exists = box.indexOfFirst { it.label == sequence.label }
                    if (exists > -1) {
                        box.removeAt(exists)
                    }
                }
            } else {
                map[sequence.boxNumber] = mutableListOf(sequence)
            }
        }

        return map.toList()
            .sortedBy { (boxNumber, _) -> boxNumber }
            .sumOf { (boxNumber, lenses) ->
                lenses.sumOf { lense ->
                    (boxNumber + 1) * (lenses.indexOf(lense) + 1) * lense.focalLength!!
                }
            }
    }

    private fun parseInput(text: List<String>): List<String> {
        val sequences = mutableListOf<String>()
        text.forEach {
            sequences.addAll(it.replace("\n", "").split(","))
        }
        return sequences
    }
}

fun main() {
    val text = readInput("year_2023/day_15/Day15.txt")

    val solutionOne = Day15.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day15.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
