package util

fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (isEmpty()) return emptyList()
    val cols = this[0].size
    return List(cols) { col -> this.map { it[col] } }
}