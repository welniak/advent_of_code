package twentythree.a_one

fun day1a(input: String) = input.lines().sumOf { line ->
    val firstNumber = line.first { character -> character.isDigit() }
    val lastNumber = line.last { character -> character.isDigit() }
    val calibrationValue = "$firstNumber$lastNumber".toInt()
    calibrationValue
}

fun day1b(input: String) = input.lines().sumOf { line ->
    val firstDigitIndex = line.indexOfFirst { character -> character.isDigit() }
    val prefix = line.substring(startIndex = 0, endIndex = if (firstDigitIndex == -1) line.length else firstDigitIndex)
    val firstDigit = prefix.findAnyOf(spelledOutNumbers.keys)
        ?.let { (_, spelledOutNumber) ->
            spelledOutNumbers[spelledOutNumber]
                ?: throw IllegalStateException("Spelled out number not found in the map")
        } ?: line[firstDigitIndex]

    val lastDigitIndex = line.indexOfLast { character -> character.isDigit() }
    val suffix = line.substring(startIndex = lastDigitIndex + 1, endIndex = line.length)
    val lastDigit = suffix.findLastAnyOf(spelledOutNumbers.keys)
        ?.let { (_, spelledOutNumber) ->
            spelledOutNumbers[spelledOutNumber]
                ?: throw IllegalStateException("Spelled out number not found in the map")
        } ?: line[lastDigitIndex]

    val calibrationValue = "$firstDigit$lastDigit".toInt()
    println(calibrationValue)
    calibrationValue
}
