import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe


class MainTest: WordSpec( {

    val sampleInput = """
        A Y
        B X
        C Z
    """.trimIndent().split("\n")

    "Part one" should ({
        "part one with sample input gives score of 15" {
            partOne(sampleInput) shouldBe 15
        }
        "part one with puzzle input gives score of 10624" {
            partOne(puzzleInput) shouldBe 10624
        }
    })
    "Part two" should ({
        "part two with sample input gives score of 12" {
            partTwo(sampleInput) shouldBe 12
        }
        "part two with puzzle input gives score of 14060" {
            partTwo(puzzleInput) shouldBe 14060
        }
    })
})