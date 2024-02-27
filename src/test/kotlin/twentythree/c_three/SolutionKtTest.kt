package twentythree.c_three

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SolutionKtTest {

    @Test
    fun `part numbers should add up to 4361 in the first part`() {
        val expectedSum = 4361

        val actualSum = day3a(TestInput)

        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `gear ratios should add up to 467835 in the second part`() {
        val expectedSum = 467835

        val actualSum = day3b(TestInput)

        assertEquals(expectedSum, actualSum)
    }

    private companion object TestData {
        const val TestInput = "467..114..\n" +
                "...*......\n" +
                "..35..633.\n" +
                "......#...\n" +
                "617*......\n" +
                ".....+.58.\n" +
                "..592.....\n" +
                "......755.\n" +
                "...\$.*....\n" +
                ".664.598.."
    }
}
