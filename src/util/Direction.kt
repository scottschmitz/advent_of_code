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

  fun toChar(): Char {
    return when (this) {
      NORTH -> '^'
      EAST -> '>'
      SOUTH -> 'v'
      WEST -> '<'
      else -> '?'
    }
  }

  fun turn90Clockwise(): Direction {
    return when (this) {
      NORTH -> EAST
      NORTH_EAST -> SOUTH_EAST
      EAST -> SOUTH
      SOUTH_EAST -> SOUTH_WEST
      SOUTH -> WEST
      SOUTH_WEST -> NORTH_WEST
      WEST -> NORTH
      NORTH_WEST -> NORTH_EAST
    }
  }

  fun turn90CounterClockwise(): Direction {
    return when (this) {
      NORTH -> WEST
      NORTH_EAST -> NORTH_WEST
      EAST -> NORTH
      SOUTH_EAST -> NORTH_EAST
      SOUTH -> EAST
      SOUTH_WEST -> SOUTH_EAST
      WEST -> SOUTH
      NORTH_WEST -> SOUTH_WEST
    }
  }
}