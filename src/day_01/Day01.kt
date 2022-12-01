fun main() {

    data class Elf(
        val foodCalories: List<Int>
    ) {
        val totalCalories: Int get() = foodCalories.sum()
    }

    fun parseElves(input: List<String>): List<Elf> {
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

    val elvesText = readInput("day_01/Day01")
    val elves = parseElves(elvesText)

    val sortedElves = elves
        .mapIndexed { index, elf -> index to elf }
        .sortedByDescending { (_, elf) -> elf.totalCalories }


    println("Solution 1: Elf: ${sortedElves.first().first}. Total Calories: ${sortedElves.first().second.totalCalories}")

    val topThreeCalories = sortedElves
        .subList(0, 3)
        .sumOf { (_, elf) -> elf.totalCalories }
    println("Solution 2: Total 3 Total Calories: $topThreeCalories")
}
