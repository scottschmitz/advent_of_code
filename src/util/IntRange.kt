package util

fun IntRange.product(other: IntRange) = this.flatMap { i ->
    other.map {
        j -> i to j
    }
}