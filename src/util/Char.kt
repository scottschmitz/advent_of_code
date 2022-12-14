package util

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