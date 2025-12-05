package util

fun List<LongRange>.simplifyOverlaps(): List<LongRange> {
    val sorted = sortedBy { it.first }
    val result = mutableListOf<LongRange>()

    var current = sorted.first()
    for (range in sorted.drop(1)) {
        if (range.first <= current.last + 1) {
            current = current.first .. maxOf(current.last(), range.last())
        } else {
            result += current
            current = range
        }
    }

    result += current
    return result
}

val LongRange.size: Long get() = (last - first + 1).coerceAtLeast(1)