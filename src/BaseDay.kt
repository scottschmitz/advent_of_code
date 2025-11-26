abstract class BaseDay<T, U, V>(
    private val filePath: String
) {
    private val rawInput: List<String> by lazy { readInput(filePath) }
    val parsedInput: T by lazy { processFile(rawInput) }

    abstract fun processFile(input: List<String>): T
    abstract fun solutionOne(input: T): U
    abstract fun solutionTwo(input: T): V
}