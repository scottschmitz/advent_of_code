package year_2023.day_20

import readInput
import util.lcm

enum class Pulse {
    HIGH,
    LOW
}

sealed class Module(open val name: String, open val destinations: List<String>) {
    data class Broadcaster(
        override val name: String,
        override val destinations: List<String>,
    ): Module(name, destinations)

    data class FlipFlop(
        override val name: String,
        override val destinations: List<String>,
        var isOn: Boolean = false
    ): Module(name, destinations)

    data class Conjunction(
        override val name: String,
        override val destinations: List<String>,
        val recentPulses: MutableMap<String, Pulse> = mutableMapOf()
    ): Module(name, destinations) {
        fun addInput(from: String) {
            recentPulses[from] = Pulse.LOW
        }
    }

    // From to (Pulse to Destinations)
    fun receivePulse(from: String, pulse: Pulse): Triple<String, Pulse, List<String>> {
        return when (this) {
            is Broadcaster -> {
                Triple(this.name, pulse, this.destinations)
            }

            is FlipFlop -> {
                when (pulse) {
                    Pulse.HIGH -> {
                        /* do nothing */
                        Triple(this.name, pulse, emptyList())
                    }

                    Pulse.LOW -> {
                        if (this.isOn) {
                            this.isOn = false
                            Triple(this.name, Pulse.LOW, this.destinations)
                        } else {
                            this.isOn = true
                            Triple(this.name, Pulse.HIGH, this.destinations)
                        }
                    }
                }
            }

            is Conjunction -> {
                this.recentPulses[from] = pulse
                if (this.recentPulses.values.all { it == Pulse.HIGH }) {
                    Triple(this.name, Pulse.LOW, this.destinations)
                } else {
                    Triple(this.name, Pulse.HIGH, this.destinations)
                }
            }
        }
    }
}





object Day20 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Int {
        var cables = parseCables(text)
        var emissions = mutableListOf<Triple<String, Pulse,String>>()

        var lowPulseCount = 0
        var highPulseCount = 0

        val mem = mutableMapOf<List<Module>, Triple<List<Module>, Int, Int>>()


        for (i in 0 until 1_000) {
            // press button
            lowPulseCount += 1
            cables["broadcaster"]!!.destinations.forEach {  destination ->
                emissions.add(Triple("broadcaster", Pulse.LOW, destination))
            }


            if (mem.containsKey(cables.values)) {
                val result = mem[cables.values]!!

                cables = result.first.associateBy { it.name }
                lowPulseCount += result.second
                highPulseCount += result.third
            } else {
                while (emissions.isNotEmpty()) {
                    val newEmissions = mutableListOf<Triple<String, Pulse, String>>()

                    emissions.forEach { emission ->
                        if (emission.second == Pulse.HIGH) {
                            highPulseCount += 1
                        } else {
                            lowPulseCount += 1
                        }

                        cables[emission.third]?.receivePulse(emission.first, emission.second)?.let { result ->
                            result.third.forEach { destination ->
                                newEmissions.add(Triple(result.first, result.second, destination))
                            }
                        }
                    }

                    emissions = newEmissions
                }
            }

            println("$i low $lowPulseCount high $highPulseCount")
        }

        return lowPulseCount * highPulseCount
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Int {
        val cables = parseCables(text)

        val vr = findCycle("vr", Pulse.LOW, cables)
        val nl = findCycle("nl", Pulse.LOW, cables)
        val lr = findCycle("lr", Pulse.LOW, cables)

        return listOf(vr, nl, lr).lcm()
    }

    private fun parseCables(text: List<String>): Map<String, Module> {
        val theMap = text.associate { line ->
            val split = line.split(" -> ")

            val input = split[0]
            val destinations = split[1].split(", ")

            when (input[0]) {
                '%' -> {
                    val name = input.substring(1)
                    name to Module.FlipFlop(name, destinations)
                }
                '&' -> {
                    val name = input.substring(1)
                    name to Module.Conjunction(name, destinations)
                }
                else -> {
                    input to Module.Broadcaster(input, destinations)
                }
            }
        }

        theMap.values.filterIsInstance<Module.Conjunction>()
            .forEach { conjunction ->
                theMap.values.filter { it.destinations.contains(conjunction.name) }.forEach { input ->
                    conjunction.addInput(input.name)
                }
            }

        return theMap.toMutableMap()
    }

    val mem = mutableMapOf<List<Module>, List<Module>>()
    private fun findCycle(destination: String, pulse: Pulse, originalCables: Map<String, Module>): Int {
        var cables = originalCables
        var emissions = mutableListOf<Triple<String, Pulse,String>>()

        var buttonPresses = 0

        while (true) {
            // press button
            buttonPresses += 1
            cables["broadcaster"]!!.destinations.forEach { destination ->
                emissions.add(Triple("broadcaster", Pulse.LOW, destination))
            }

            if (mem.containsKey(cables.values)) {
                cables = mem[cables.values]!!.associateBy { it.name }
            } else {
                while (emissions.isNotEmpty()) {
                    val newEmissions = mutableListOf<Triple<String, Pulse, String>>()

                    emissions.forEach { emission ->
                        cables[emission.third]?.receivePulse(emission.first, emission.second)?.let { result ->
                            result.third.forEach { destination ->
                                newEmissions.add(Triple(result.first, result.second, destination))
                            }
                        }
                    }

                    if (emissions.any { it.second == pulse && it.third == destination}) {
                        return buttonPresses
                    }

                    emissions = newEmissions
                }
            }
        }
    }
}

fun main() {
    val text = readInput("year_2023/day_20/Day20.txt")

    val solutionOne = Day20.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day20.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
