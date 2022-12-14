package util
/**
 * Return all the Positions that would be touching the position
 */
fun Pair<Int, Int>.neighbors() = listOf(
        this.first - 1 to this.second,
        this.first + 1 to this.second,
        this.first     to this.second - 1,
        this.first     to this.second + 1,
)

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