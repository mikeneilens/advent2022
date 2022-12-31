import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    "Part one" should ({
        "parsing sample input into snowflakes for each row" {
            val flakesForRow = parse(sampleInput).flakesOnRow
            flakesForRow[1].size shouldBe 5
            flakesForRow[2].size shouldBe 3
            flakesForRow[3].size shouldBe 5
            flakesForRow[4].size shouldBe 6
        }
        "parsing sample input into snowflakes for each columnn" {
            val flakesForColumn = parse(sampleInput).flakesOnCol
            flakesForColumn[1].size shouldBe 3
            flakesForColumn[2].size shouldBe 4
            flakesForColumn[3].size shouldBe 1
            flakesForColumn[4].size shouldBe 3
            flakesForColumn[5].size shouldBe 4
            flakesForColumn[6].size shouldBe 4
        }
        "part one with sample input" {
            partOne(sampleInput) shouldBe 18
        }
        "part one with puzzle input is 288" {
            partOne(puzzleInput) shouldBe 288
        }
    })
    "Part two" should ({
        "part two with sample input is 54" {
            partTwo(sampleInput) shouldBe 54
        }
        "part two with puzzle input is 861" {
            partTwo(puzzleInput) shouldBe 861
        }

    })
})