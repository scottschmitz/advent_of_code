package util

import java.util.PriorityQueue

object Pathfinding {
    /**
     * Perform DFS to determine the number of unique paths from the [start] to the [end].
     * If [requiredNodes] are provided then they must be included on the path for the path
     * to be valid, but may be in any order.
     *
     * @param start         The node to begin the search from
     * @param end           The node to end the search at
     * @param nodes         The available nodes and their neighbors
     * @param requiredNodes The nodes that must be used in any order along the path
     *
     * @return The count of available paths
     */
    fun <T : Any> availablePaths(
        start: T,
        end: T,
        nodes: Map<T, List<T>>,
        requiredNodes: List<T> = emptyList()
    ): Long {
        // Map each required node to a bit position
        val indexByNode: Map<T, Int> = requiredNodes.withIndex().associate { (i, n) -> n to i }

        // Bitmask where all required nodes have been seen
        val allRequiredMask: Int = if (requiredNodes.isEmpty()) 0 else (1 shl requiredNodes.size) - 1

        // Memo now keyed by (current node, visitedMask)
        val memo = mutableMapOf<Pair<T, Int>, Long>()

        fun dfs(current: T, visitedMask: Int): Long {
            val currentMask = indexByNode[current]?.let { bit ->
                visitedMask or (1 shl bit)
            } ?: visitedMask

            val key = current to currentMask
            memo[key]?.let { return it }

            if (current == end){
                val pathsFromHere = if (currentMask == allRequiredMask) 1L else 0L
                memo[key] = pathsFromHere
                return pathsFromHere
            }

            val paths = nodes[current].orEmpty()
                .sumOf { neighbor -> dfs(neighbor, currentMask) }

            memo[key] = paths
            return paths
        }

        return dfs(start, 0)
    }




    /**
     * Performs a search.
     * If a [heuristic] is given, it is A*, otherwise, Dijkstra's algorithm.
     *
     * @param start        The node or state to begin the search from.
     * @param neighbours   A function that returns all possible transitions from a node and their associated cost.
     *                     The cost _must_ be a non-negative value.
     * @param goalFunction A predicate that determines whether a state is the search destination.
     *                     Search stops upon reaching the first node that evaluates to `true`.
     * @param heuristic    A function that estimates the lower bound cost of reaching a destination from a given node.
     *                     Must never overestimate, otherwise the search result might not be the most cost-effective.
     * @param onVisit      An optional callback invoked on each node visit, useful for debugging.
     * @param maximumCost  An optional upper bound, prevents any transitions that would exceed this value.
     * @param trackPath    If `true`, keeps track of intermediary nodes to be able to construct a search path.
     *                     If `false` _(the default)_, only the costs to reach the nodes are computed.
     *
     * @return The search result, or `null` if a suitable destination couldn't be reached.
     */
    fun <T : Any> optimalPath(
        start: T,
        neighbours: (T) -> Iterable<Pair<T, Int>>,
        goalFunction: (T) -> Boolean,
        heuristic: (T) -> Int = { 0 },
        onVisit: (T) -> Unit = {},
        maximumCost: Int = Int.MAX_VALUE,
        trackPath: Boolean = false,
    ): SearchResult<T>? {
        require(maximumCost > 0) { "Maximum cost must be positive." }

        val previous = mutableMapOf<T, T>()
        val distance = mutableMapOf(start to 0)
        val visited = mutableSetOf<Pair<T, Int>>()

        @Suppress("UNUSED_DESTRUCTURED_PARAMETER_ENTRY")
        val queue = PriorityQueue(compareBy<Triple<T, Int, Int>> { (node, costSoFar, priority) -> priority })
        queue.add(Triple(start, 0, 0))

        if (trackPath) previous[start] = start

        while (queue.isNotEmpty()) {
            val (node, costSoFar, _) = queue.poll()
            if (!visited.add(node to costSoFar)) continue
            onVisit(node)
            if (goalFunction(node)) return SearchResult(start, node, distance, previous)

            for ((nextNode, nextCost) in neighbours(node)) {
                check(nextCost >= 0) { "Transition cost between nodes cannot be negative." }
                if (maximumCost - nextCost < costSoFar) continue

                val totalCost = costSoFar + nextCost

                if (totalCost > (distance[nextNode] ?: Int.MAX_VALUE)) continue

                distance[nextNode] = totalCost
                if (trackPath) previous[nextNode] = node

                val heuristicValue = heuristic(node)
                check(heuristicValue >= 0) { "Heuristic value must be positive." }
                queue.add(Triple(nextNode, totalCost, totalCost + heuristicValue))
            }
        }

        return null
    }

    /**
     * The result of a [Pathfinding] search.
     *
     * @property start    The node the search started from.
     * @property end      The destination node, or the last visited node if an exhaustive flood search was requested.
     * @property cost     The cost from [start] to [end], or the maximum cost if an exhaustive flood search was requested.
     * @property path     The path from [start] to [end], each node associated with the running cost.
     * @property distance The cost from the [start] to all the visited intermediary nodes.
     * @property previous The previous node in the path of all the visited intermediary nodes.
     *                    Following it recursively will lead back to the [start] node.
     */
    class SearchResult<out T> internal constructor(
        val start: T,
        val end: T,
        private val distance: Map<T, Int>,
        private val previous: Map<T, T>,
    ) {
        val cost: Int get() = distance.getValue(end)

        val path: List<Pair<T, Int>> by lazy {
            check(previous.isNotEmpty()) { "Cannot generate path as search was performed with `trackPath = false`." }
            buildList {
                var current = end
                while (true) {
                    add(current to distance.getValue(current))
                    val previous = previous.getValue(current)
                    if (previous == current) break
                    current = previous
                }
            }.asReversed()
        }
    }
}