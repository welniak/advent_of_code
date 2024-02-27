package twentythree.d_four

import kotlin.math.pow

fun day4a(input: String): Int {
    return input.lines()
        .map { line -> line.toScratchCard() }
        .sumOf { scratchCard -> scratchCard.points }
}

fun day4b(input: String): Int {
    val scratchCards =  input.lines().map { line -> line.toScratchCard() }
    scratchCards.forEachIndexed { scratchCardIndex, scratchCard ->
        repeat(scratchCard.numberOfMatchingNumbers) { index ->
            scratchCards[scratchCardIndex + index + 1].numberOfCopies += scratchCard.numberOfCopies
        }
    }

    return scratchCards.sumOf { it.numberOfCopies }
}

private fun String.toScratchCard(): ScratchCard {
    fun String.toNumbersList() = split(" ").filter { it.isNotBlank() }.map { it.toInt() }

    val numbersSansPrefixString = split(":").last()
    val (winningNumbersString, yourNumbersString) = numbersSansPrefixString.split("|")
    val winningNumbers = winningNumbersString.toNumbersList()
    val yourNumbers = yourNumbersString.toNumbersList()

    return ScratchCard(winningNumbers, yourNumbers)
}

private data class ScratchCard(
    val winningNumbers: List<Int>,
    val yourNumbers: List<Int>,
    var numberOfCopies: Int = 1
) {

    val numberOfMatchingNumbers = winningNumbers.intersect(yourNumbers.toSet()).size
    val points = 2.toDouble().pow(numberOfMatchingNumbers - 1).toInt()
}
