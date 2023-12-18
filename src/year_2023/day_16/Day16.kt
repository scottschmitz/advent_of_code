package year_2023.day_16

import readInput
import util.*

interface Mirror {
    fun reflect(fromDirection: Direction): Direction
}

object ForwardSlash: Mirror {
    override fun reflect(fromDirection: Direction): Direction {
        return when (fromDirection) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.NORTH
            Direction.WEST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            else -> throw IllegalArgumentException()
        }
    }
}

object BackSlash: Mirror {
    override fun reflect(fromDirection: Direction): Direction {
        return when (fromDirection) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.SOUTH
            Direction.WEST -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Splitter {
    UP_DOWN,
    LEFT_RIGHT;
}

data class Layout(
    val height: Int,
    val width: Int,
    val mirrors: Map<Point, Mirror>,
    val splitters: Map<Point, Splitter>
)

object Day16 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val layout = parseLayout(text)
        layout.splitters.keys.maxBy { it.second }

        val tilesVisitedTop = mutableListOf<Pair<Point, Direction>>()
        followPath(-1 to 0, Direction.EAST, layout, tilesVisitedTop)

        // -1 to remove -1,0
        return tilesVisitedTop.distinctBy { it.first }.size - 1
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val layout = parseLayout(text)

        var currentMax = 0

        // enter from the top and bottom
        for (x in 0 .. layout.width) {
            val tilesVisitedTop = mutableListOf<Pair<Point, Direction>>()
            followPath(x to -1, Direction.SOUTH, layout, tilesVisitedTop)
            val fromTop = tilesVisitedTop.distinctBy { it.first }.size - 1

            if (fromTop > currentMax) {
                currentMax = fromTop
            }

            val tilesVisitedBottom = mutableListOf<Pair<Point, Direction>>()
            followPath(x to layout.height - 1, Direction.NORTH, layout, tilesVisitedBottom)
            val fromBottom = tilesVisitedBottom.distinctBy { it.first }.size - 1

            if (fromBottom > currentMax) {
                currentMax = fromBottom
            }
        }

        // enter from the left and right
        for (y in 0 .. layout.height) {
            val tilesVisitedLeft = mutableListOf<Pair<Point, Direction>>()
            followPath(-1 to y, Direction.EAST, layout, tilesVisitedLeft)
            val fromLeft = tilesVisitedLeft.distinctBy { it.first }.size - 1

            if (fromLeft > currentMax) {
                currentMax = fromLeft
            }

            val tilesVisitedRight = mutableListOf<Pair<Point, Direction>>()
            followPath(layout.width -1 to y, Direction.WEST, layout, tilesVisitedRight)
            val fromRight = tilesVisitedRight.distinctBy { it.first }.size - 1

            if (fromRight > currentMax) {
                currentMax = fromRight
            }
        }

        return currentMax
    }
    
    private fun parseLayout(text: List<String>): Layout {
        val mirrors = mutableMapOf<Point, Mirror>()
        val splitters = mutableMapOf<Point, Splitter>()

        text.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                when (char) {
                    '/' -> mirrors[x to y] = ForwardSlash
                    '\\' -> mirrors[x to y] = BackSlash
                    '-' -> splitters[x to y] = Splitter.LEFT_RIGHT
                    '|' -> splitters[x to y] = Splitter.UP_DOWN
                }
            }
        }

        return Layout(
            text.size,
            text[0].length,
            mirrors,
            splitters
        )
    }

    private fun followPath(point: Point, direction: Direction, layout: Layout, previouslyVisitedTiles: MutableList<Pair<Point, Direction>>) {
        var currPoint = point
        var currDirection = direction

        while (currPoint to currDirection !in previouslyVisitedTiles) {
            previouslyVisitedTiles.add(currPoint to currDirection)

            currPoint = when (currDirection) {
                Direction.NORTH -> currPoint.up()
                Direction.EAST -> currPoint.right()
                Direction.SOUTH -> currPoint.down()
                Direction.WEST -> currPoint.left()
                else -> throw IllegalArgumentException("invalid direction.")
            }

            if (currPoint in layout.mirrors) {
                val mirror = layout.mirrors[currPoint]!!
                currDirection = mirror.reflect(currDirection)
            } else if (currPoint in layout.splitters) {
                val splitter = layout.splitters[currPoint]!!
                when (splitter) {
                    Splitter.LEFT_RIGHT -> {
                        if (currDirection == Direction.NORTH || currDirection == Direction.SOUTH) {
                            followPath(currPoint, Direction.EAST, layout, previouslyVisitedTiles)
                            followPath(currPoint, Direction.WEST, layout, previouslyVisitedTiles)
                            break
                        }
                    }
                    Splitter.UP_DOWN -> {
                        if (currDirection == Direction.EAST || currDirection == Direction.WEST) {
                            followPath(currPoint, Direction.NORTH, layout, previouslyVisitedTiles)
                            followPath(currPoint, Direction.SOUTH, layout, previouslyVisitedTiles)
                            break
                        }
                    }
                }
            }

            if (currPoint.x() < 0 || currPoint.x() >= layout.width || currPoint.y() < 0 || currPoint.y() >= layout.height) {
                break
            }
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_16/Day16.txt")

    val solutionOne = Day16.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day16.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
