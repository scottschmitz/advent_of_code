package util

import kotlin.math.abs

typealias PointL = Pair<Long, Long>
fun PointL.x() = this.first
fun PointL.y() = this.second

/**
 * return the Manhattan distance (taxicab geometry) between 2 points
 */
fun Pair<Long, Long>.manhattanDistance(to: Pair<Long, Long>): Long {
  return abs(this.first - to.first) + abs(this.second - to.second)
}
