import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe


class MainTest: WordSpec( {

    val sampleInput = """
        A Y
        B X
        C Z
    """.trimIndent().split("\n")

    "Part one" should ({
        "give correct round data when parsing a line on the input" {
            parseLine(sampleInput[0]) shouldBe Round(Item.Rock,Item.Paper)
            parseLine(sampleInput[1]) shouldBe Round(Item.Paper,Item.Rock)
            parseLine(sampleInput[2]) shouldBe Round(Item.Scissors,Item.Scissors)
        }
        "give the correct score for each line on the sample input" {
            parseLine(sampleInput[0]).yourScore() shouldBe 8
            parseLine(sampleInput[1]).yourScore() shouldBe 1
            parseLine(sampleInput[2]).yourScore() shouldBe 6
        }
        "part one with sample input gives score of 15" {
            partOne(sampleInput) shouldBe 15
        }
        "part one with puzzle input gives score of 10624" {
            partOne(puzzleInput) shouldBe 10624
        }
    })
    "Part two" should ({
        "give correct round data when parsing a line on the input for part two" {
            parseLine2(sampleInput[0]) shouldBe Round(Item.Rock,Item.Rock)
            parseLine2(sampleInput[1]) shouldBe Round(Item.Paper,Item.Rock)
            parseLine2(sampleInput[2]) shouldBe Round(Item.Scissors,Item.Rock)
        }
        "part two with sample input gives score of 12" {
            partTwo(sampleInput) shouldBe 12
        }
        "part two with puzzle input gives score of 14060" {
            partTwo(puzzleInput) shouldBe 14060
        }
    })
})