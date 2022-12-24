package year_2022.day_24

import readInput
import util.Bounds
import util.Bounds.Companion.bounds
import util.Direction.*
import util.Point
import util.isWithin
import util.plus

private data class Move(val position: Point, val step: Int)

class Day24(text: List<String>) {
    companion object {

        private const val WALL = '#'
        private const val EMPTY = '.'
        private const val BLIZZARD_UP = '^'
        private const val BLIZZARD_RIGHT = '>'
        private const val BLIZZARD_DOWN = 'v'
        private const val BLIZZARD_LEFT = '<'

        private val SAME_DIRECTION = Point(0, 0)
        private val DIRECTION_DELTAS = (listOf(NORTH, SOUTH, WEST, EAST).map { it.delta } + SAME_DIRECTION)
    }

    private val data = text.map { it.toCharArray() }

    fun solutionOne() = data.let { input ->
        // build the map
        val initialState = buildMap(input)
        val walls = walls(input)
        val bounds = (initialState.keys + walls).bounds()

        // find the cycle with set of states
        val states = findCycleStates(initialState, bounds)

        // find the shortest path to the end
        val start = Point(1, 0)
        val end = Point(bounds.max.first - 1, bounds.max.second)

        val solution = search(start, end, 0, states, walls, bounds)

        solution
    }

    fun solutionTwo() = data.let { input ->
        // build the map
        val initialState = buildMap(input)
        val walls = walls(input)
        val bounds = (initialState.keys + walls).bounds()

        // find the cycle with set of states
        val states = findCycleStates(initialState, bounds)

        // find the shortest path to the end
        val start = Point(1, 0)
        val end = Point(bounds.max.first - 1, bounds.max.second)

        val solution1 = search(start, end, 0, states, walls, bounds)
        val solution2 = search(end, start, solution1, states, walls, bounds)
        val solution3 = search(start, end, solution2, states, walls, bounds)

        solution3
    }

    private fun search(
        start: Point,
        end: Point,
        initialStep: Int,
        states: List<Set<Point>>,
        walls: Set<Point>,
        bounds: Bounds
    ): Int {
        val pendingMoves = ArrayDeque<Move>().also { it.add(Move(start, initialStep + 1)) }
        val visitedMoves = HashSet<Move>()
        val possibleMoves = HashSet<Move>()
        val impossibleMoves = HashSet<Move>()

        while (pendingMoves.isNotEmpty()) {
            val move = pendingMoves.removeFirst()
            val step = move.step + 1

            if (visitedMoves.contains(move)) {
                continue
            }

            if (move.position == end) {
                return step - 1
            }

            visitedMoves.add(move)

            val nextPositions = DIRECTION_DELTAS.map { delta -> move.position.plus(delta) }
            val blizzards = states[step % states.size]
            val nextPossibleMoves: Collection<Move> = nextPositions
                .map { nextPosition ->
                    Move(
                        position = nextPosition,
                        step = step
                    ).also { move ->
                        cache(move, nextPosition, possibleMoves, impossibleMoves, bounds, walls, blizzards)
                    }
                }
                .subtract(impossibleMoves)

            pendingMoves.addAll(nextPossibleMoves)
        }

        error("no solution found")
    }

    private fun cache(
        move: Move,
        position: Point,
        possibleMoves: HashSet<Move>,
        impossibleMoves: HashSet<Move>,
        bounds: Bounds,
        walls: Set<Point>,
        blizzards: Set<Point>
    ) {
        if (!possibleMoves.contains(move) && !impossibleMoves.contains(move)) {
            val isPossibleMove = position.isWithin(bounds) && !walls.contains(position) && !blizzards.contains(position)

            if (isPossibleMove) {
                possibleMoves.add(move)
            } else {
                impossibleMoves.add(move)
            }
        }
    }

    private fun findCycleStates(
        initialState: Map<Point, List<Char>>,
        bounds: Bounds
    ): List<Set<Point>> {
        val pattern = HashSet<Map<Point, List<Char>>>()
        val states = ArrayList<Set<Point>>()

        var lastState = initialState
        var position = 0

        while (true) {
            pattern.add(lastState)
            states.add(HashSet(lastState.keys))

            val nextState = nextState(lastState, bounds)
            lastState = nextState
            position += 1

            if (pattern.contains(nextState)) {
                break
            }
        }

        return states
    }

    private fun nextState(current: Map<Point, List<Char>>, bounds: Bounds): Map<Point, List<Char>> {
        val next = mutableMapOf<Point, MutableList<Char>>()

        current.forEach { (point, items) ->
            items.forEach { item ->
                val nextPoint = when (item) {
                    BLIZZARD_UP -> {
                        val prevY = point.second - 1
                        val y = if (prevY > bounds.min.second) prevY else bounds.max.second - 1
                        Point(point.first, y)
                    }

                    BLIZZARD_RIGHT -> {
                        val nextX = point.first + 1
                        val x = if (nextX < bounds.max.first) nextX else bounds.min.first + 1
                        Point(x, point.second)
                    }

                    BLIZZARD_DOWN -> {
                        val nextY = point.second + 1
                        val y = if (nextY < bounds.max.second) nextY else bounds.min.second + 1
                        Point(point.first, y)
                    }

                    BLIZZARD_LEFT -> {
                        val prevX = point.first - 1
                        val x = if (prevX > bounds.min.first) prevX else bounds.max.first - 1
                        Point(x, point.second)
                    }

                    else -> error("undefined item $item")
                }

                next.getOrPut(nextPoint) { mutableListOf() }.add(item)
            }
        }

        return next
    }

    private fun walls(input: List<CharArray>): Set<Point> {
        val walls = mutableSetOf<Point>()
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char == WALL) {
                    walls.add(Point(col, row))
                }
            }
        }
        return walls
    }

    private fun buildMap(input: List<CharArray>): Map<Point, List<Char>> {
        val map = mutableMapOf<Point, List<Char>>()

        input.forEachIndexed { row, line ->
            line.forEachIndexed { x, item ->
                if (x == 0 || x == line.size - 1 || row == 0 || row == input.size - 1) {
                    // no-op
                } else if (item == EMPTY) {
                    // no-op
                } else {
                    val position = Point(x, row)
                    map[position] = listOf(item)
                }
            }
        }

        return map
    }
}

fun main() {
    val inputText = readInput("year_2022/day_24/Day24.txt")
    val day = Day24(text = inputText)

    println("Solution 1: ${day.solutionOne()}")
    println("Solution 2: ${day.solutionTwo()}")
}