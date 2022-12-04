package year_2022.day_03

import readInput
import toAlphabetPosition

data class Rucksack(
    val compartmentOne: List<Char>,
    val compartmentTwo: List<Char>
) {
    val allItemTypes: List<Char> = compartmentOne + compartmentTwo
    val errorItemType: Char = compartmentOne.intersect(compartmentTwo.toSet()).first()
}

data class ElfGroup(
    val rucksacks: List<Rucksack>
) {
    val badge: Char get() {
        var commonItemGroups = rucksacks.first().allItemTypes.toSet()
        rucksacks.forEach { rucksack ->
            commonItemGroups = commonItemGroups.intersect(rucksack.allItemTypes.toSet())
        }
        return commonItemGroups.first()
    }
}

object Day03 {

    /**
     * @return sum of the priority of items erroneously in both compartments
     */
    fun solutionOne(rucksacksText: List<String>): Int {
        val rucksacks = parseRucksacks(rucksacksText)
        return rucksacks.sumOf { rucksack ->
            rucksack.errorItemType.toAlphabetPosition()
        }
    }

    /**
     * @return the total priority of elf group badges
     */
    fun solutionTwo(rucksacksText: List<String>): Int {
        val rucksacks = parseRucksacks(rucksacksText)
        val elfGroups = groupRucksacks(rucksacks)
        return elfGroups.sumOf { group ->
            group.badge.toAlphabetPosition()
        }
    }

    private fun parseRucksacks(text: List<String>): List<Rucksack> {
        return text.map { rucksackText ->
            val rucksackSize = rucksackText.length
            Rucksack(
                compartmentOne = rucksackText.substring(0, (rucksackSize + 1) / 2).toList(),
                compartmentTwo = rucksackText.substring((rucksackSize + 1) / 2, rucksackSize).toList()
            )
        }
    }

    private fun groupRucksacks(rucksacks: List<Rucksack>): List<ElfGroup> {
        return rucksacks.chunked(3).map { rucksackGroup ->
            ElfGroup(rucksacks = rucksackGroup)
        }
    }
}

fun main() {
    val rucksacksText = readInput("year_2022/day_03/Day03.txt")

    val solutionOne = Day03.solutionOne(rucksacksText)
    println("Solution 1: Total Priority of Error Items: $solutionOne")

    val solutionTwo = Day03.solutionTwo(rucksacksText)
    println("Solution 2: Total Elf Badge Priorities: $solutionTwo")
}
