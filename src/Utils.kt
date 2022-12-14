import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", name)
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Identifies the position in the alphabet starting at 'a' = 1 and 'A' = 27
 */
fun Char.toAlphabetPosition(): Int {
    if (!isLetter()) {
        throw IllegalArgumentException("Passed in non letter.")
    }

    return when (isLowerCase()) {
        true -> code - 'a'.code + 1
        else -> code - 'A'.code + 27
    }
}

fun IntRange.product(other: IntRange) = this.flatMap { i ->
    other.map {
        j -> i to j
    }
}

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
 * Return the Positions below the position
 */
fun Pair<Int, Int>.down(): Pair<Int, Int> {
    return first to second + 1
}

/***
 * Return the Positions below the position and to the left
 */
fun Pair<Int, Int>.downLeft(): Pair<Int, Int> {
    return first - 1 to second + 1
}

/***
 * Return the Positions below the position and to the left
 */
fun Pair<Int, Int>.downRight(): Pair<Int, Int> {
    return first + 1 to second + 1
}