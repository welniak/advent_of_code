package twentythree.e_five

fun day5a(input: String): Long {
    val allBlocks = input.toBlocks()
    val seeds = allBlocks.getSeeds()
    val almanacMaps = allBlocks.takeLast(allBlocks.size - 1)
        .map { block -> block.toAlmanacMap() }
    val almanacTransformation = AlmanacTransformation(almanacMaps)

    return seeds.map { seed -> almanacTransformation.transform(seed) }.minOf { it }
}

private fun String.toBlocks() = split("\n\n")

private fun List<String>.getSeeds(): List<Long> = first()
    .split(":")
    .last()
    .split(" ")
    .filter { it.isNotBlank() }
    .map { it.toLong() }

private fun String.toAlmanacMap(): AlmanacMap {
    fun String.toRangeNumbers() = split(" ")
        .filter { it.isNotBlank() }
        .map { it.toLong() }

    val lines = split("\n")

    return lines.takeLast(lines.size - 1)
        .map { it.toRangeNumbers() }
        .onEach { assert(it.size == 3) }
        .map { rangeNumbers ->
            MapRange(
                destinationRangeStart = rangeNumbers[0],
                sourceRangeStart = rangeNumbers[1],
                range = rangeNumbers[2]
            )
        }
        .let { mapRanges ->
            AlmanacMap(mapRanges)
        }
}

private class AlmanacTransformation(
    private val almanacMaps: List<AlmanacMap>
) {

    fun transform(seed: Long): Long {
        var currentSource: Long = seed
        almanacMaps.forEach { almanacMap ->
            currentSource = almanacMap.map(currentSource)
        }

        return currentSource
    }
}

private class AlmanacMap(
    private val ranges: List<MapRange>
) {

    fun map(source: Long): Long {
        ranges.forEach { range ->
            if (source.isWithinSourcesRange(range)) {
                return source.toDestination(range)
            }
        }

        return source
    }

    private fun Long.isWithinSourcesRange(mapRange: MapRange): Boolean =
        this in (mapRange.sourceRangeStart..<mapRange.sourceRangeStart + mapRange.range)

    private fun Long.toDestination(mapRange: MapRange): Long {
        val diff = this - mapRange.sourceRangeStart
        return mapRange.destinationRangeStart + diff
    }
}

private data class MapRange(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val range: Long
)
