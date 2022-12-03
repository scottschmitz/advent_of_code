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
