package twentythree.b_two

fun day2a(input: String) = input.lines()
    .map { line -> line.toGame() }
    .filter { game -> game.isValid }
    .sumOf { it.id }

fun day2b(input: String) = input.lines()
    .map { line -> line.toGame() }
    .sumOf { it.minCubeCombination.power }

data class Game(
    val id: Int,
    val draws: List<Draw>
) {

    val isValid = draws.all { it.isValid }

    val minCubeCombination: CubeCombination
        get() {
            var redMax = 0
            var greenMax = 0
            var blueMax = 0

            draws.forEach { draw ->
                if (draw.redCubesCount > redMax) redMax = draw.redCubesCount
                if (draw.greenCubesCount > greenMax) greenMax = draw.greenCubesCount
                if (draw.blueCubesCount > blueMax) blueMax = draw.blueCubesCount
            }

            return CubeCombination(redMax, greenMax, blueMax)
        }
}

data class Draw(
    val redCubesCount: Int,
    val greenCubesCount: Int,
    val blueCubesCount: Int
) {
    val isValid =
        redCubesCount <= Rules.RedCubesCount &&
                greenCubesCount <= Rules.GreenCubesCount &&
                blueCubesCount <= Rules.BlueCubesCount
}

data class CubeCombination(
    val redCubesCount: Int,
    val greenCubesCount: Int,
    val blueCubesCount: Int
) {
    val power = redCubesCount * greenCubesCount * blueCubesCount
}

private fun String.toGame(): Game {
    val gameIndexAndDraws = split(":")
    val gameIndex = gameIndexAndDraws.first().split(" ").last().toInt()
    val draws = gameIndexAndDraws.last()
        .split(";")
        .map { it.toDraw() }
    return Game(gameIndex, draws)
}

private fun String.toDraw(): Draw {
    val cubeCounts = this.split(",")
    val colourCounts = mutableMapOf<String, Int>()
    cubeCounts.forEach { cubeCount ->
        val cubeCountTokens = cubeCount.split(" ").filter { it.isNotBlank() }
        val count = cubeCountTokens.first().toInt()
        val colour = cubeCountTokens.last()
        colourCounts[colour] = count
    }
    return Draw(
        redCubesCount = colourCounts.getOrDefault(Rules.RedColour, 0),
        greenCubesCount = colourCounts.getOrDefault(Rules.GreenColour, 0),
        blueCubesCount = colourCounts.getOrDefault(Rules.BlueColour, 0)
    )
}

object Rules {
    const val RedCubesCount = 12
    const val GreenCubesCount = 13
    const val BlueCubesCount = 14

    const val RedColour = "red"
    const val GreenColour = "green"
    const val BlueColour = "blue"
}
