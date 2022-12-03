package day_03

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

fun main() {
    fun parseRucksacks(text: List<String>): List<Rucksack> {
        return text.map { text ->
            val rucksackSize = text.length
            Rucksack(
                compartmentOne = text.substring(0, (rucksackSize + 1) / 2).toList(),
                compartmentTwo = text.substring((rucksackSize + 1) / 2, rucksackSize).toList()
            )
        }
    }

    fun groupRucksacks(rucksacks: List<Rucksack>): List<ElfGroup> {
        return rucksacks.chunked(3).map { rucksackGroup ->
            ElfGroup(
                rucksacks = rucksackGroup
            )
        }
    }

    val rucksacksText = readInput("day_03/Day03.txt")
    val rucksacks = parseRucksacks(rucksacksText)

    val totalPriorityOfErrorItems = rucksacks.sumOf {rucksack ->
        rucksack.errorItemType.toAlphabetPosition()
    }
    println("Solution 1: Total Priority of Error Items: $totalPriorityOfErrorItems")

    val elfGroups = groupRucksacks(rucksacks)
    val totalElfBadgePriorities = elfGroups.sumOf { group ->
        group.badge.toAlphabetPosition()
    }
    println("Solution 2: Total Elf Badge Priorities: $totalElfBadgePriorities")
}
