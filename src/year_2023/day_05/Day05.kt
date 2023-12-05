package year_2023.day_05

import readInput


data class SourceToDestination(
    val source: Long,
    val destination: Long,
    val length: Long
) {
    fun containsSource(source: Long): Boolean {
        return source >= this.source && source < this.source + length
    }

    fun containsDestination(destination: Long): Boolean {
        return destination >= this.destination && destination < this.destination + length
    }

    fun destinationFor(value: Long): Long {
        return destination + (value - source)
    }

    fun sourceFor(value: Long): Long {
        return source + (value - destination)
    }
}

fun List<SourceToDestination>.getDestination(source: Long): Long {
    return firstOrNull { it.containsSource(source) }?.destinationFor(source) ?: source
}

fun List<SourceToDestination>.getSource(destination: Long): Long {
    return firstOrNull { it.containsDestination(destination) }?.sourceFor(destination) ?: destination
}


data class Almanac(
    val seeds: List<Long>,
    val seedToSoilMap: List<SourceToDestination>,
    val soilToFertilizerMap: List<SourceToDestination>,
    val fertilizerToWaterMap: List<SourceToDestination>,
    val waterToLightMap: List<SourceToDestination>,
    val lightToTemperatureMap: List<SourceToDestination>,
    val temperatureToHumidityMap: List<SourceToDestination>,
    val humidityToLocationMap: List<SourceToDestination>,
) {
    fun containsSeed(seed: Long): Boolean {
        for (i in seeds.indices step 2) {
            if (seed >= seeds[i] && seed < seeds[i] + seeds[i+1]) {
                return true
            }
        }

        return false
    }
}

object Day05 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Long {
        val almanac = parseAlmanac(text)

        return almanac.seeds.map { seed ->
            val soil = almanac.seedToSoilMap.getDestination(seed)
            val fertilizer = almanac.soilToFertilizerMap.getDestination(soil)
            val water = almanac.fertilizerToWaterMap.getDestination(fertilizer)
            val light = almanac.waterToLightMap.getDestination(water)
            val temp = almanac.lightToTemperatureMap.getDestination(light)
            val humidity = almanac.temperatureToHumidityMap.getDestination(temp)
            val location = almanac.humidityToLocationMap.getDestination(humidity)

            location
        }.min()
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Long {
        val almanac = parseAlmanac(text)

        var location = 0L
        while (true) {

            val humidity = almanac.humidityToLocationMap.getSource(location)
            val temp = almanac.temperatureToHumidityMap.getSource(humidity)
            val light = almanac.lightToTemperatureMap.getSource(temp)
            val water = almanac.waterToLightMap.getSource(light)
            val fertilizer = almanac.fertilizerToWaterMap.getSource(water)
            val soil = almanac.soilToFertilizerMap.getSource(fertilizer)
            val seed = almanac.seedToSoilMap.getSource(soil)

            if (almanac.containsSeed(seed)) {
                return location
            }

            location += 1
        }
    }

    private fun parseAlmanac(text: List<String>): Almanac {
        val seedsLine = text.first { it.contains("seeds: ") }.split(" ")
        val seeds = seedsLine.subList(1, seedsLine.size).map { it.toLong() }

        val seedToSoilMap = mutableListOf<SourceToDestination>()
        val soilToFertilizerMap = mutableListOf<SourceToDestination>()
        val fertilizerToWaterMap = mutableListOf<SourceToDestination>()
        val waterToLightMap = mutableListOf<SourceToDestination>()
        val lightToTemperatureMap = mutableListOf<SourceToDestination>()
        val temperatureToHumidityMap = mutableListOf<SourceToDestination>()
        val humidityToLocationMap = mutableListOf<SourceToDestination>()

        var currentList: MutableList<SourceToDestination>? = null
        text.forEach { line ->
            if (line.isEmpty()) {
                currentList = null
            }

            currentList?.let {
                populateList(it, line)
            }

            when (line) {
                "seed-to-soil map:" -> currentList = seedToSoilMap
                "soil-to-fertilizer map:" -> currentList = soilToFertilizerMap
                "fertilizer-to-water map:" -> currentList = fertilizerToWaterMap
                "water-to-light map:" -> currentList = waterToLightMap
                "light-to-temperature map:" -> currentList = lightToTemperatureMap
                "temperature-to-humidity map:" -> currentList = temperatureToHumidityMap
                "humidity-to-location map:" -> currentList = humidityToLocationMap
            }

        }

        return Almanac(
            seeds = seeds,
            seedToSoilMap = seedToSoilMap,
            soilToFertilizerMap = soilToFertilizerMap,
            fertilizerToWaterMap = fertilizerToWaterMap,
            waterToLightMap = waterToLightMap,
            lightToTemperatureMap = lightToTemperatureMap,
            temperatureToHumidityMap = temperatureToHumidityMap,
            humidityToLocationMap = humidityToLocationMap
        )
    }

    private fun populateList(list: MutableList<SourceToDestination>, line: String) {
        val split = line.split(" ").filter { it.isNotEmpty() }

        val destination = split[0].toLong()
        val source = split[1].toLong()
        val length = split[2].toLong()

        list.add(
            SourceToDestination(
                source = source,
                destination = destination,
                length = length
            )
        )
    }
}

fun main() {
    val text = readInput("year_2023/day_05/Day05.txt")

    val solutionOne = Day05.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day05.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
