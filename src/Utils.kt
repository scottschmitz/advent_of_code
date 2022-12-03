import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun Char.toAlphabetPosition(): Int {
    if (!isLetter()) {
        throw IllegalArgumentException("Passed in non letter.")
    }

    // a = 97
    // A = 65

    return when (isLowerCase()) {
        true -> code.toByte().toInt() - ('a'.code.toByte().toInt()) + 1
        else -> code.toByte().toInt() - ('A'.code.toByte().toInt()) + 27
    }
}