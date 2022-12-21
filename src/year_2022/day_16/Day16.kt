package year_2022.day_16

import readInput
import kotlin.math.max

data class Valve(
    val name: String,
    val flowRate: Int,
    val toValves: List<String>
)
object Day16 {

    var score = 0

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        score = 0
        val valvesMap = parseText(text)
        val shortestPaths = floydWarshall(
            shortestPaths = valvesMap.values.associate { it.name to it.toValves.associateWith { 1 }.toMutableMap() }.toMutableMap(),
            valves = valvesMap
        )

        depthFirstSearch(
            currScore = 0,
            currentValve = "AA",
            visited = emptySet(),
            time = 0,
            totalTime = 30,
            valves = valvesMap,
            shortestPaths = shortestPaths
        )
        return score
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        score = 0
        val valvesMap = parseText(text)
        val shortestPaths = floydWarshall(
                shortestPaths = valvesMap.values.associate { it.name to it.toValves.associateWith { 1 }.toMutableMap() }.toMutableMap(),
                valves = valvesMap
        )

        depthFirstSearch(
            currScore = 0,
            currentValve = "AA",
            visited = emptySet(),
            time = 0,
            totalTime = 26,
            valves = valvesMap,
            shortestPaths = shortestPaths,
            hasElephantHelper = true
        )
        return score
    }

    private fun depthFirstSearch(
        currScore: Int,
        currentValve: String,
        visited: Set<String>,
        time: Int,
        totalTime: Int,
        valves: Map<String, Valve>,
        shortestPaths: MutableMap<String, MutableMap<String, Int>>,
        hasElephantHelper: Boolean = false
    ) {
        score = max(score, currScore)

        for ((valve, dist) in shortestPaths[currentValve]!!) {
            if (!visited.contains(valve) && time + dist + 1 < totalTime) {
                depthFirstSearch(
                    currScore + (totalTime - time - dist - 1) * valves[valve]?.flowRate!!,
                    valve,
                    visited.union(listOf(valve)),
                    time + dist + 1,
                    totalTime,
                    valves,
                    shortestPaths
                )
            }
        }

        if (hasElephantHelper) {
            depthFirstSearch(
                currScore = currScore,
                currentValve = "AA",
                visited = visited,
                time = 0,
                totalTime = totalTime,
                valves = valves,
                shortestPaths = shortestPaths,
                hasElephantHelper = false
            )
        }
    }

    private fun floydWarshall(
        shortestPaths: MutableMap<String, MutableMap<String, Int>>,
        valves: Map<String, Valve>
    ): MutableMap<String, MutableMap<String, Int>> {
        for (k in shortestPaths.keys) {
            for (i in shortestPaths.keys) {
                for (j in shortestPaths.keys) {
                    val ik = shortestPaths[i]?.get(k) ?: 9999
                    val kj = shortestPaths[k]?.get(j) ?: 9999
                    val ij = shortestPaths[i]?.get(j) ?: 9999
                    if (ik + kj < ij)
                        shortestPaths[i]?.set(j, ik + kj)
                }
            }
        }
        //remove all paths that lead to a valve with rate 0
        shortestPaths.values.forEach {
            it.keys.map { key -> if (valves[key]?.flowRate == 0) key else "" }
                .forEach { toRemove -> if (toRemove != "") it.remove(toRemove) }
        }
        return shortestPaths
    }

    //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
    private fun parseText(text: List<String>): Map<String, Valve> {
        return text.map { line ->
            var toValves = line.split("to val")[1].split(" ")
            toValves = toValves.subList(1, toValves.size)
            toValves = toValves.flatMap { it.split(",") }.filter { it.isNotEmpty() }

            Valve(
                name = line.split(" ")[1],
                flowRate = Integer.parseInt(line.split(" ")[4].split("=")[1].split(";")[0]),
                toValves = toValves
            )
        }.associateBy { valve -> valve.name }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_16/Day16.txt")

    val solutionOne = Day16.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day16.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}