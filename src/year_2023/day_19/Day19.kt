package year_2023.day_19

import readInput

data class System(
    val workflows: Map<String, Workflow>,
    val parts: List<Part>
)

data class Workflow(
    val name: String,
    val conditions: List<String>
)

data class Part(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int
)

object Day19 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        val system = parseWorkflows(text)
        return system.parts.sumOf { part ->
            when (evaluatePart(part, system.workflows)) {
                true -> part.x + part.m + part.a + part.s
                else -> 0
            }
        }
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        return -1
    }

    private fun parseWorkflows(text: List<String>): System {
        val workflowMap = mutableMapOf<String, Workflow>()
        val parts = mutableListOf<Part>()

        var parsingWorkflows = true
        text.forEach { line ->
            if (parsingWorkflows) {
                if (line.isEmpty()) {
                    parsingWorkflows = false
                } else {

                    val split = line.split('{')
                    val name = split[0]

                    workflowMap[name] = Workflow(
                        name = name,
                        conditions = split[1].replace("}", "").split(",")
                    )
                }
            } else {
                val split = line.replace("{", "").replace("}", "").split(",")
                parts.add(
                    Part(
                        x = split[0].split("=")[1].toInt(),
                        m = split[1].split("=")[1].toInt(),
                        a = split[2].split("=")[1].toInt(),
                        s = split[3].split("=")[1].toInt(),
                    )
                )
            }
        }

        return System(
            workflows = workflowMap,
            parts = parts
        )
    }

    private fun evaluatePart(part: Part, workflows: Map<String, Workflow>): Boolean {
        var currentWorkflow = "in"
        while (currentWorkflow != "A" && currentWorkflow != "R") {
            val workflow = workflows[currentWorkflow]!!
            currentWorkflow = workflow.conditions.firstNotNullOf { condition ->
                evaluateCondition(part, condition)
            }
        }

        return currentWorkflow == "A"
    }

    private fun evaluateCondition(part: Part, condition: String): String? {
        if (!condition.contains(":")) {
            // no condition always happen
            return condition
        }

        val split = condition.split(":")
        val resultingCondition = split.last()

        if (split[0].contains(">")) {
            val equationSplit = split[0].split('>')
            val value = equationSplit[1].toInt()
            val passes = when (equationSplit[0].first()) {
                'x' -> part.x > value
                'm' -> part.m > value
                'a' -> part.a > value
                's' -> part.s > value
                else -> throw IllegalArgumentException()
            }
            return when (passes) {
                true -> resultingCondition
                else -> null
            }
        } else {
            val equationSplit = split[0].split('<')
            val value = equationSplit[1].toInt()
            val passes = when (equationSplit[0].first()) {
                'x' -> part.x < value
                'm' -> part.m < value
                'a' -> part.a < value
                's' -> part.s < value
                else -> throw IllegalArgumentException()
            }
            return when (passes) {
                true -> resultingCondition
                else -> null
            }
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_19/Day19.txt")

    val solutionOne = Day19.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day19.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
