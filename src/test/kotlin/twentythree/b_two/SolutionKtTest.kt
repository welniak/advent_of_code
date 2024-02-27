package twentythree.b_two

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SolutionKtTest {

    @Test
    fun `ids of the possible games should add up to 8 in the first part`() {
        val expectedSum = 8

        val actualSum = day2a(TestInput)

        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `power of sets should add up to 2286 in the second part`() {
        val expectedSum = 2286

        val actualSum = day2b(TestInput)

        assertEquals(expectedSum, actualSum)
    }

    private companion object TestData {

        const val TestInput = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    }
}
