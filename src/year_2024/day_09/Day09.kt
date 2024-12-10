package year_2024.day_09

import readInput

object Day09 {
  fun solutionOne(text: String): Long {
    val blocks = mutableListOf<Int?>()
    text.forEachIndexed { index, char ->
      val fileId = when (index % 2) {
        0 -> (index / 2)
        else -> null
      }

      repeat(char.toString().toInt()) {
        blocks += fileId
      }
    }

    blocks.indices.forEach { index ->
      val fileId = blocks[index]
      if (fileId == null) {
        val lastFileIdIndex = blocks.indexOfLast { it != null }
        if (lastFileIdIndex > index) {
          blocks[index] = blocks[lastFileIdIndex]
          blocks[lastFileIdIndex] = null
        }
      }
    }
    return blocks
      .filterNotNull()
      .withIndex()
      .sumOf { (index, fileId) -> index * fileId.toLong() }
  }

  fun solutionTwo(text: String): Long {
    val blocks = text.mapIndexed { index, char ->
      when (index % 2) {
        0 -> Memory.File(fileId = index / 2, size = char.toString().toInt())
        else -> Memory.Empty(size = char.toString().toInt())
      }
    }.toMutableList()

    blocks.reversed().forEachIndexed { blockToMoveIndex, blockToMove ->
      val blockToMoveIndexUnreversed = blocks.indexOf(blockToMove)

      if (blockToMove is Memory.File) {
        val freeSpaceIndex = blocks.indexOfFirst { it is Memory.Empty && it.size >= blockToMove.size }
        if (freeSpaceIndex != -1 && freeSpaceIndex < blockToMoveIndexUnreversed) {
          val freeSpace = blocks[freeSpaceIndex] as Memory.Empty
          if (freeSpace.size == blockToMove.size) {
            blocks[freeSpaceIndex] = blockToMove
            blocks[blockToMoveIndexUnreversed] = Memory.Empty(size = blockToMove.size)
          } else {
            blocks[freeSpaceIndex] = blockToMove
            blocks[blockToMoveIndexUnreversed] = Memory.Empty(size = blockToMove.size)
            // do the same thing but add a free memory of the left over space
            blocks.add(freeSpaceIndex + 1, Memory.Empty(size = freeSpace.size - blockToMove.size))
          }
        }
      }
    }

    return blocks
      .flatMap { memory ->
        when (memory) {
          is Memory.File -> List(memory.size) { memory.fileId }
          else -> List(memory.size) { 0 }
        }
      }
      .withIndex()
      .sumOf { it.index * it.value.toLong() }
  }

  private fun printBlocks(blocks: List<Memory>) {
    blocks.forEach { memory ->
      repeat(memory.size) {
        when (memory) {
          is Memory.File -> print(memory.fileId)
          else -> print(".")
        }
      }
    }
    println()
  }

  sealed class Memory(open val size: Int) {
    data class File(val fileId: Int, override val size: Int) : Memory(size)
    data class Empty(override val size: Int) : Memory(size)
  }
}

fun main() {
  val text = readInput("year_2024/day_09/Day09.txt").first()

  val solutionOne = Day09.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day09.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
