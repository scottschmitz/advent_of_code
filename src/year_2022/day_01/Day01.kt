package year_2022.day_01

import readInput

data class Elf(val foodCalories: List<Int>) {
    val totalCalories: Int get() = foodCalories.sum()
}

object Day01 {
    /**
     * Calculate the position and elf with the most calories
     *
     * @return Elf position to Total Calories
     */
    fun solutionOne(elvesText: List<String>): Pair<Int, Int> {
        val elves = parseElves(elvesText)
        val highestCalorieElf = elves
            .mapIndexed { index, elf -> index to elf }
            .maxByOrNull { (_, elf) -> elf.totalCalories }!!
        return highestCalorieElf.first + 1 to highestCalorieElf.second.totalCalories
    }

    /**
     * Calculate the total calories for the 3 elves with the most calories
     *
     * @return Sum of Top 3 Calorie Elves
     */
    fun solutionTwo(elvesText: List<String>): Int {
        val elves = parseElves(elvesText)
        return elves
            .mapIndexed { index, elf -> index to elf }
            .sortedByDescending { (_, elf) -> elf.totalCalories }
            .subList(0, 3)
            .sumOf { (_, elf) -> elf.totalCalories }
    }

    private fun parseElves(input: List<String>): List<Elf> {
        val mutableElves = mutableListOf<Elf>()

        var currentFoodList = mutableListOf<Int>()
        input.forEach {  text ->
            if (text.isEmpty()) {
                mutableElves.add(Elf(currentFoodList))
                currentFoodList = mutableListOf()
            } else {
                currentFoodList.add(text.toInt())
            }
        }
        mutableElves.add(Elf(currentFoodList))

        return mutableElves
    }
}

fun main() {
    val elvesText = readInput("year_2022/day_01/Day01.txt")

    val solutionOne = Day01.solutionOne(elvesText)
    println("Solution 1: Elf: ${solutionOne.first}. Total Calories: ${solutionOne.second}")

    val solutionTwo = Day01.solutionTwo(elvesText)
    println("Solution 2: Total 3 Total Calories: $solutionTwo")
}
