# Advent of Code – Kotlin Solutions

This repository contains my Advent of Code solutions across multiple years.
Beginning in 2025, the project uses a simplified and test-driven structure with a shared BaseDay abstraction.
Earlier years remain in the repository **but do not follow the new style.**

This README explains the current workflow, which applies to all new work going forward.

## 🎄 Project Layout
```
src/
  main/
    kotlin/
      year_2025/
        BaseDay.kt
        day_01/
          Day01.kt
          Day01.txt        (git-ignored)
        ...
```

Older years (year_2021, year_2022, etc.) may look different and do not follow the BaseDay pattern.

## ⚙️ BaseDay (2025+)

Starting in 2025, puzzle days extend a shared BaseDay:
```
abstract class BaseDay<T, U, V>(
    private val filePath: String
) {
    private val rawInput: List<String> by lazy { readInput(filePath) }
    val parsedInput: T by lazy { processFile(rawInput) }

    abstract fun processFile(input: List<String>): T
    abstract fun solutionOne(input: T): U
    abstract fun solutionTwo(input: T): V
}
```

All 2025+ solutions implement this minimal contract.

## ⚡ Creating a New Day (Template Workflow)

You can quickly scaffold a new day using a custom Android Studio file template.

To create a new day:

Right-click:
`src/main/kotlin/year_2025/`

Select:
`New → aoc (the custom template)`

When prompted for `${AOC_DAY}`, enter the day number with leading zeros:
`01`, `02`, … `25`

### Template contents (for reference)
#### To create or edit the template
1. `⌘ ,` -> `Editor` -> `File and Code Templates`
2. Create a new File
3. Enter the name you would prefer (like `aoc`)
4. Enter `kt` for the extension
5. Enter `Day${AOC_DAY}` for the title
6. Copy and paste the template below

```
package year_${YEAR}.day_${AOC_DAY}

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay

class Day${AOC_DAY} : BaseDay<List<String>, Long, Long>("year_${YEAR}/day_${AOC_DAY}/Day${AOC_DAY}.txt") {

    override fun processFile(input: List<String>): List<String> = input

    override fun solutionOne(input: List<String>): Long {
        return -1L
    }

    override fun solutionTwo(input: List<String>): Long {
        return -1L
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
    )
    
    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(1L, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(1L, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}
```
IntelliJ will prompt you for the value for ${AOC_DAY} automatically when you create the file.


### Why use tests?
* IntelliJ automatically shows execution time
* Easy to run individual parts
* Clean separation of sample tests vs real input
* No need to maintain main() functions

## 📁 Input Files (Important!)

Daily puzzle input files (DayXX.txt) are stored alongside each day:
```
year_2025/day_07/Day07.txt
```

### These files are intentionally git-ignored
* Advent of Code asks participants **not to share puzzle inputs**
* Inputs are unique per user
* Keeping them out of Git avoids clutter and keeps the repo compliant

Your `.gitignore` prevents committing any real puzzle input files.

### If you need an input file again
Retrieve it from the official Advent of Code website:
```
https://adventofcode.com/2025/day/XX/input
```
You must be logged in to access your personal input.

## 🗃 About Previous Years

Folders for earlier Advent of Code years are kept for reference, but:
* They do not use the new `BaseDay` design
* They may have different folder layouts
* They may contain older experiments or scripts

This README describes the **2025+ architecture** only.

## 🔧 Development Notes (Reminders for Future Me)
* Always use leading zeros for day numbers (01, not 1)
* Input .txt files are intentionally not tracked by Git

📚 AoC & Kotlin Resources
* [Advent of Code](https://adventofcode.com/)
* [Kotlin Docs](https://kotlinlang.org/docs/home.html)
* [Kotlin Slack](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up)
* [Original JetBrains AoC Template](https://github.com/kotlin-hands-on/advent-of-code-kotlin-template)

Happy Advent of Code! 🎄