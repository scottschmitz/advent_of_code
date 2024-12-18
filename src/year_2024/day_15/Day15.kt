package year_2024.day_15

import readInput
import util.*

object Day15 {
  fun solutionOne(text: List<String>): Long {
    val (warehouse, directions) = parseText(text)

    var position = warehouse.startingLocation
    directions.forEach { direction ->
      val nextPos = position.plus(direction.delta)
      var feeler = nextPos
      if (warehouse.rocks.contains(feeler)) {
        while (feeler in warehouse.rocks) {
          feeler = feeler.plus(direction.delta)
        }
        if (feeler in warehouse.walls) {
          // a wall is there cant push
        } else {
          warehouse.rocks.remove(nextPos)
          warehouse.rocks.add(feeler)
          position = nextPos
        }
      } else if (warehouse.walls.contains(nextPos)) {
        // a wall is there
      } else {
        position = nextPos
      }
    }

    return warehouse.rocks.sumOf { rock ->
      ((100 * rock.y()) + rock.x()).toLong()
    }
  }

  fun solutionTwo(text: List<String>): Long {
    val (w, directions) = parseText(text)
    val doubleWarehouse = DoubleWarehouse.fromWarehouse(w)

    var position = doubleWarehouse.startingLocation
    directions.forEach { direction ->
      val nextPos = position.plus(direction.delta)
      var feeler = nextPos

      val leftRocks = mutableSetOf<Point>()
      val rightRocks = mutableSetOf<Point>()

      if (doubleWarehouse.leftRocks.contains(feeler) || doubleWarehouse.rightRocks.contains(feeler)) {
        // horizontal is eassssyyy
        if (direction == Direction.EAST || direction == Direction.WEST) {
          while (feeler in doubleWarehouse.leftRocks || feeler in doubleWarehouse.rightRocks) {
            doubleWarehouse.leftRocks.firstOrNull { it == feeler }?.let {
              leftRocks.add(it)
            }
            doubleWarehouse.rightRocks.firstOrNull { it == feeler }?.let {
              rightRocks.add(it)
            }
            feeler = feeler.plus(direction.delta)
          }

          if (feeler in doubleWarehouse.walls) {
            // a wall is there cant push
          } else {
            doubleWarehouse.leftRocks.removeAll(leftRocks)
            doubleWarehouse.leftRocks.addAll(leftRocks.map { it.plus(direction.delta) })

            doubleWarehouse.rightRocks.removeAll(rightRocks)
            doubleWarehouse.rightRocks.addAll(rightRocks.map { it.plus(direction.delta) })

            position = nextPos
          }
        } else {
          // vertical sucks
          var feelers = listOf(feeler)
          while (
            (doubleWarehouse.leftRocks.any { it in feelers } || doubleWarehouse.rightRocks.any { it in feelers })
            && doubleWarehouse.walls.none { it in feelers }
          ) {
            val newLeftRocks = doubleWarehouse.leftRocks.filter { it in feelers }.toMutableList()
            val newRightRocks = doubleWarehouse.rightRocks.filter { it in feelers }.toMutableList()

            newLeftRocks.forEach { r ->
              newRightRocks.add(r.plus(Direction.EAST.delta))
            }
            newRightRocks.forEach { r ->
              newLeftRocks.add(r.plus(Direction.WEST.delta))
            }

            leftRocks.addAll(newLeftRocks)
            rightRocks.addAll(newRightRocks)

            feelers = (newLeftRocks + newRightRocks).map { it.plus(direction.delta) }
          }

          if (doubleWarehouse.walls.any { it in feelers }) {
            // a wall is there cant push
          } else {
            doubleWarehouse.leftRocks.removeAll(leftRocks)
            doubleWarehouse.leftRocks.addAll(leftRocks.map { it.plus(direction.delta) })

            doubleWarehouse.rightRocks.removeAll(rightRocks)
            doubleWarehouse.rightRocks.addAll(rightRocks.map { it.plus(direction.delta) })

            position = nextPos
          }
        }
      } else if (doubleWarehouse.walls.contains(nextPos)) {
        // do nothing
      } else {
        position = nextPos
      }
    }

    val values = doubleWarehouse.leftRocks.toSet().map { rock ->
      ((100 * rock.y()) + rock.x()).toLong()
    }
    values.sorted().forEach { println(it) }

    return values.sum()
  }

  fun parseText(text: List<String>): Pair<Warehouse, List<Direction>> {
    val walls = mutableListOf<Point>()
    val rocks = mutableListOf<Point>()
    var startingLocation = Point(0, 0)

    text.take(text.size - 2).forEachIndexed { y, line ->
      line.forEachIndexed { x, char ->
        when (char) {
          '#' -> walls.add(Point(x, y))
          'O' -> rocks.add(Point(x, y))
          '@' -> startingLocation = Point(x, y)
        }
      }
    }

    val directions = text.drop(text.size - 1).first().map {
      when (it) {
        '^' -> Direction.NORTH
        'v' -> Direction.SOUTH
        '<' -> Direction.WEST
        '>' -> Direction.EAST
        else -> throw IllegalArgumentException("Invalid direction")
      }
    }

    return Pair(
      Warehouse(rocks, walls, startingLocation),
      directions
    )
  }

  data class Warehouse(
    val rocks: MutableList<Point>,
    val walls: List<Point>,
    val startingLocation: Point
  )

  data class DoubleWarehouse(
    val leftRocks: MutableList<Point>,
    val rightRocks: MutableList<Point>,
    val walls: List<Point>,
    val startingLocation: Point
  ) {
    companion object {
      fun fromWarehouse(warehouse: Warehouse): DoubleWarehouse {
        val leftRocks = warehouse.rocks.map { Point(it.x() * 2, it.y()) }.toMutableList()
        val rightRocks = leftRocks.map { Point(it.x() + 1, it.y()) }.toMutableList()
        val walls = warehouse.walls.flatMap {
          listOf(
            Point(it.x() * 2, it.y()),
            Point((it.x() * 2) + 1, it.y())
          )
        }
        val startingLocation = Point(warehouse.startingLocation.x() * 2, warehouse.startingLocation.y())

        return DoubleWarehouse(leftRocks, rightRocks, walls, startingLocation)
      }
    }
  }
}

fun main() {
  val text = readInput("year_2024/day_15/Day15.txt")

  val solutionOne = Day15.solutionOne(text)
  println("Solution 1: $solutionOne")

  val solutionTwo = Day15.solutionTwo(text)
  println("Solution 2: $solutionTwo")
}
