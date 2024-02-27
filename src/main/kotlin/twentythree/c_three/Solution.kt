package twentythree.c_three

import java.lang.StringBuilder

fun day3a(input: String): Int {
    val (engineNumbersPerLine, specialCharacterPerLine) = extractEngineNumbersAndSpecialCharactersFrom(input)
    var partNumberSum = 0

    engineNumbersPerLine.forEachIndexed { lineIndex, engineNumbers ->
        engineNumbers.forEach { engineNumber ->
            val previousLineSpecialCharacters = specialCharacterPerLine.getOrNull(lineIndex - 1) ?: emptyList()
            val currentLineSpecialCharacters = specialCharacterPerLine.getOrNull(lineIndex) ?: emptyList()
            val nextLineSpecialCharacters = specialCharacterPerLine.getOrNull(lineIndex + 1) ?: emptyList()

            if (previousLineSpecialCharacters.isAnyNeighbourOf(engineNumber) ||
                currentLineSpecialCharacters.isAnyNeighbourOf(engineNumber) ||
                nextLineSpecialCharacters.isAnyNeighbourOf(engineNumber)
            ) {
                partNumberSum += engineNumber.value
            }
        }
    }

    return partNumberSum
}

fun day3b(input: String): Int {
    val (engineNumbersPerLine, specialCharacterPerLine) = extractEngineNumbersAndSpecialCharactersFrom(input)
    var gearRatioSum = 0

    specialCharacterPerLine.forEachIndexed { lineIndex, specialCharacters ->
        specialCharacters.forEach { specialCharacter ->
            val previousLineEngineNumbers = engineNumbersPerLine.getOrNull(lineIndex - 1) ?: emptyList()
            val currentLineEngineNumbers = engineNumbersPerLine.getOrNull(lineIndex) ?: emptyList()
            val nextLineEngineNumbers = engineNumbersPerLine.getOrNull(lineIndex + 1) ?: emptyList()

            val gearRatio = specialCharacter.getGearRatioOrNull(
                previousLineEngineNumbers,
                currentLineEngineNumbers,
                nextLineEngineNumbers
            )?.also { println("The gear ratio = $it") } ?: 0

            gearRatioSum += gearRatio
        }
    }

    return gearRatioSum
}

private val Char?.isSpecial
    get() = this != null && !isDigit() && this != '.'

private fun List<SpecialCharacter>.isAnyNeighbourOf(engineNumber: EngineNumber) =
    any { engineNumber.isNeighbourOf(it) }

private fun extractEngineNumbersAndSpecialCharactersFrom(input: String):
        Pair<MutableList<List<EngineNumber>>, MutableList<List<SpecialCharacter>>> {
    val engineNumbersPerLine = mutableListOf<List<EngineNumber>>()
    val specialCharacterPerLine = mutableListOf<List<SpecialCharacter>>()

    input.lines().forEachIndexed { lineIndex, line ->
        val lineEngineNumbers = mutableListOf<EngineNumber>()
        val lineSpecialCharacters = mutableListOf<SpecialCharacter>()

        var currentCharacterIndex = 0

        while (currentCharacterIndex < line.length) {
            var stringEndIndex = currentCharacterIndex + 1
            val currentCharacter = line[currentCharacterIndex]

            if (currentCharacter.isDigit()) {
                val numberString = StringBuilder().append(currentCharacter)
                while (stringEndIndex < line.length && line[stringEndIndex].isDigit()) {
                    numberString.append(line[stringEndIndex])
                    stringEndIndex++
                }

                val engineNumber = EngineNumber(
                    value = numberString.toString().toInt(),
                    rowNumber = lineIndex,
                    startIndex = currentCharacterIndex,
                    endIndex = stringEndIndex - 1
                )
                lineEngineNumbers.add(engineNumber)
            } else if (currentCharacter.isSpecial) {
                val specialCharacter = SpecialCharacter(
                    rowNumber = lineIndex,
                    index = currentCharacterIndex,
                    character = currentCharacter
                )
                lineSpecialCharacters.add(specialCharacter)
            }

            currentCharacterIndex = stringEndIndex
        }

        engineNumbersPerLine.add(lineEngineNumbers)
        specialCharacterPerLine.add(lineSpecialCharacters)
    }

    return engineNumbersPerLine to specialCharacterPerLine
}

private data class EngineNumber(
    val value: Int,
    val rowNumber: Int,
    val startIndex: Int,
    val endIndex: Int
) {

    fun isNeighbourOf(specialCharacter: SpecialCharacter) =
        specialCharacter.rowNumber in this.rowNumber - 1..this.rowNumber + 1 &&
                specialCharacter.index in startIndex - 1..endIndex + 1
}

private data class SpecialCharacter(
    val rowNumber: Int,
    val index: Int,
    val character: Char
) {

    fun getGearRatioOrNull(
        previousLineNumbers: List<EngineNumber>,
        currentLineNumbers: List<EngineNumber>,
        nextLineNumbers: List<EngineNumber>
    ): Int? {
        if (character != '*') return null
        val neighboursPair = getTwoNeighbouringNumbersOrNull(
            previousLineNumbers,
            currentLineNumbers,
            nextLineNumbers
        )
        return neighboursPair?.let { (first, second) -> first.value * second.value }
    }

    private fun getTwoNeighbouringNumbersOrNull(
        previousLineNumbers: List<EngineNumber>,
        currentLineNumbers: List<EngineNumber>,
        nextLineNumbers: List<EngineNumber>
    ): Pair<EngineNumber, EngineNumber>? {
        val neighbouringNumbers = mutableListOf<EngineNumber>()

        previousLineNumbers
            .plus(currentLineNumbers)
            .plus(nextLineNumbers).forEach { engineNumber ->
                if (engineNumber.isNeighbourOf(this)) {
                    neighbouringNumbers.add(engineNumber)
                }

                if (neighbouringNumbers.size > 2) {
                    return null
                }
            }

        return if (neighbouringNumbers.size != 2) {
            null
        } else {
            Pair(neighbouringNumbers.first(), neighbouringNumbers.last())
        }
    }
}
