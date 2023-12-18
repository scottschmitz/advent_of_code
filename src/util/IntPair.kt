package util

import kotlin.math.abs

typealias Point = Pair<Int, Int>
fun Point.x() = this.first
fun Point.y() = this.second

operator fun Pair<Int, Int>.plus(other: Point): Point = Point(this.first + other.first, this.second + other.second)

fun Pair<Int, Int>.isWithin(bounds: Bounds): Boolean = isWithinX(bounds) && isWithinY(bounds)

fun Pair<Int, Int>.isWithinX(bounds: Bounds): Boolean =
        this.first >= bounds.min.first && this.first <= bounds.max.first

fun Pair<Int, Int>.isWithinY(bounds: Bounds): Boolean =
        this.second >= bounds.min.second && this.second <= bounds.max.second

/**
 * Return all the Positions that would be touching the position
 */
fun Pair<Int, Int>.neighbors(includeDiagonals: Boolean = false) = buildList {
    add(this@neighbors.first - 1 to this@neighbors.second)
    add(this@neighbors.first + 1 to this@neighbors.second)
    add(this@neighbors.first     to this@neighbors.second - 1)
    add(this@neighbors.first     to this@neighbors.second + 1)

    if (includeDiagonals) {
        add(this@neighbors.first - 1 to this@neighbors.second - 1)
        add(this@neighbors.first - 1 to this@neighbors.second + 1)
        add(this@neighbors.first + 1 to this@neighbors.second - 1)
        add(this@neighbors.first + 1 to this@neighbors.second + 1)
    }
}

/***
 * Return all the Positions between two positions (inclusive)
 */
fun Pair<Int, Int>.connect(other: Pair<Int, Int>): List<Pair<Int, Int>> {
    return if (this.first == other.first) {
        if (this.second < other.second) {
            (this.second..other.second).map { this.first to it }
        } else {
            (this.second downTo other.second).map { this.first to it }
        }
    }  else if (this.second == other.second) {
        if (this.first < other.first) {
            (this.first..other.first).map { it to this.second }
        } else {
            (this.first downTo other.first).map { it to this.second }
        }
    } else {
        throw IllegalArgumentException("Only horizontal and vertical supported.")
    }
}

/**
 * return the Manhattan distance (taxicab geometry) between 2 points
 */
fun Pair<Int, Int>.manhattanDistance(to: Pair<Int, Int>): Int {
    return abs(this.first - to.first) + abs(this.second - to.second)
}

fun Pair<Int, Int>.pointsWithinRadius(radius: Int, minValue: Int, maxValue: Int): List<Pair<Int, Int>> {
    val withinRadius = mutableListOf<Pair<Int, Int>>()
    val minX = maxOf(minValue, this.first - radius)
    val maxX = minOf(maxValue, this.first + radius)

    for (col in minX ..  maxX) {
        val rowOffset = radius - abs(this.first - col)

        val minY = this.second - rowOffset
        val maxY = this.second + rowOffset
        for (row in minY .. maxY) {
            withinRadius.add(col to row)
        }
    }

    return withinRadius
}

/***
 * Return the Position below the position
 */
fun Pair<Int, Int>.down(): Pair<Int, Int> {
    return first to second + 1
}

/***
 * Return the Position below the position and to the left
 */
fun Pair<Int, Int>.downLeft(): Pair<Int, Int> {
    return first - 1 to second + 1
}

/***
 * Return the Position below the position and to the left
 */
fun Pair<Int, Int>.downRight(): Pair<Int, Int> {
    return first + 1 to second + 1
}

/***
 * Return the Position above the position
 */
fun Pair<Int, Int>.up(): Pair<Int, Int> {
    return first to second - 1
}

/***
 * Return the Position above the position and to the left
 */
fun Pair<Int, Int>.upLeft(): Pair<Int, Int> {
    return first - 1 to second - 1
}

/***
 * Return the Position above the position and to the left
 */
fun Pair<Int, Int>.upRight(): Pair<Int, Int> {
    return first + 1 to second - 1
}

/***
 * Return the Position to the left
 */
fun Pair<Int, Int>.left(): Pair<Int, Int> {
    return first - 1 to second
}

/***
 * Return the Position to the right
 */
fun Pair<Int, Int>.right(): Pair<Int, Int> {
    return first + 1 to second
}

fun List<Point>.onPointsInRectangle(onPoint: (Point) -> Unit, onRowChange: (Int) -> Unit) {
    val minX = minOfOrNull { it.first }!!
    val maxX = maxOfOrNull { it.first }!!
    val minY = minOfOrNull { it.second }!!
    val maxY = maxOfOrNull { it.second }!!

    for (row in minY..maxY) {
        onRowChange(row)

        for (col in minX..maxX) {
            onPoint(col to row)
        }
    }
}