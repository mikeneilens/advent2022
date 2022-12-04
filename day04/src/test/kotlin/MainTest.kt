import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().split("\n")
    "Part one" should ({
        "sampleInput with part one contains two assignments with one inside the other" {
            partOne(sampleInput) shouldBe 2
        }
        "puzzleInput with part one contains 571 assignments with one inside the other" {
            partOne(puzzleInput) shouldBe 571
        }
    })
    "Part two" should ({
        "sampleInput with part two contains 4 pairs with one inside the other" {
            partTwo(sampleInput) shouldBe 4
        }
        "puzzleInput with part two contains 917 pairs with one inside the other" {
            partTwo(puzzleInput) shouldBe 917
        }
    })
})