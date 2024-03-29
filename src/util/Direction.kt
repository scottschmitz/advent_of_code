package util

enum class Direction(val delta: Point) {

    NORTH(Point(0, -1)),
    NORTH_EAST(Point(1, -1)),
    EAST(Point(1, 0)),
    SOUTH(Point(0, 1)),
    SOUTH_EAST(Point(1, 1)),
    SOUTH_WEST(Point(-1, 1)),
    WEST(Point(-1, 0)),
    NORTH_WEST(Point(-1, -1));

    companion object {
        fun from(delta: Point): Direction =
            values().find { it.delta == delta } ?: error("unknown delta $delta")

        fun from(letter: Char): Direction {
            return when (letter) {
                'U' -> NORTH
                'D' -> SOUTH
                'R' -> EAST
                'L' -> WEST
                else -> throw IllegalArgumentException("Unhandled direction")
            }
        }
    }
}