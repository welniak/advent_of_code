package twentythree.a_one

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolutionKtTest {

    @Test
    fun `calibration values should add up to 142 in the first part`() {
        val expectedSum = 142

        val actualSum = day1a(TestInputA)

        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `calibration values should add up to 281 in the second part`() {
        val expectedSum = 281

        val actualSum = day1b(TestInputB)

        assertEquals(expectedSum, actualSum)
    }

    private companion object TestData {
        const val TestInputA = "1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet"

        const val TestInputB =
            "two1nine\n" +
                    "eightwothree\n" +
                    "abcone2threexyz\n" +
                    "xtwone3four\n" +
                    "4nineeightseven2\n" +
                    "zoneight234\n" +
                    "7pqrstsixteen"
    }
}
