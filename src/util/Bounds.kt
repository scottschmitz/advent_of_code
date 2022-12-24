package util

import kotlin.math.max
import kotlin.math.min

data class Bounds(val min: Point, val max: Point) {

    operator fun plus(other: Bounds): Bounds = Bounds(this.min + other.min, this.max + other.max)

    fun isWithinX(other: Bounds): Boolean =
            this.min.first >= other.min.first && this.min.first <= other.max.first

    fun isWithinY(other: Bounds): Boolean =
            this.min.second >= other.min.second && this.min.second <= other.max.second

    fun isWithin(other: Bounds): Boolean =
            isWithinX(other) && isWithinY(other)

    companion object {

        fun Collection<Bounds>.rebound(): Bounds {
            var minX = Int.MAX_VALUE
            var maxX = Int.MIN_VALUE
            var minY = Int.MAX_VALUE
            var maxY = Int.MIN_VALUE

            forEach { (min, max) ->
                minX = min(minX, min.first)
                maxX = max(maxX, max.first)
                minY = min(minY, min.second)
                maxY = max(maxY, max.second)
            }

            return Bounds(
                    min = Point(minX, minY),
                    max = Point(maxX, maxY)
            )
        }

        fun Collection<Point>.bounds(): Bounds {
            var minX = Int.MAX_VALUE
            var maxX = Int.MIN_VALUE
            var minY = Int.MAX_VALUE
            var maxY = Int.MIN_VALUE

            forEach { (x, y) ->
                minX = min(minX, x)
                maxX = max(maxX, x)
                minY = min(minY, y)
                maxY = max(maxY, y)
            }

            return Bounds(
                    min = Point(minX, minY),
                    max = Point(maxX, maxY)
            )
        }
    }
}