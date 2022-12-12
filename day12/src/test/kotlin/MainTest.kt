import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
    """.trimIndent().split("\n")
    "Part one" should ({
        "height of position containing a or S is 97" {
            Position(0,0).height(sampleInput) shouldBe 97
            Position(1,0).height(sampleInput) shouldBe 97
            Position(0,1).height(sampleInput) shouldBe 97
        }
        "height of position containing z or E is 97" {
            Position(4,2).height(sampleInput) shouldBe 122
            Position(5,2).height(sampleInput) shouldBe 122
        }
        "surrounding positions of 0,0" {
            Position(0,0).surroundingPositions(sampleInput) shouldBe listOf(Position(x=1, y=0), Position(x=0, y=1))
        }
        "surrounding positions of 1,1" {
            Position(1,1).surroundingPositions(sampleInput) shouldBe listOf(Position(x=2, y=1), Position(x=1, y=2), Position(x=0, y=1), Position(x=1, y=0))
        }
        "surrounding positions of 7,4" {
            Position(7,4).surroundingPositions(sampleInput) shouldBe listOf(Position(x=6, y=4), Position(x=7, y=3))
        }
        "valid routes from 0,0" {
            Position(0,0).routes(sampleInput) shouldBe listOf(Position(x=1, y=0), Position(x=0, y=1))
        }
        "valid routes from 3,1" {
            Position(3,1).routes(sampleInput).sortedBy{ it.height(sampleInput) } shouldBe listOf(Position(x=3, y=2), Position(x=3, y=0), Position(x=2, y=1)).sortedBy{ it.height(sampleInput) }
        }
        "at goal at position 5,2" {
            Position(5,2).atGoal(sampleInput,'E') shouldBe true
        }
        "find journey size from positon 0,0" {
            val journeyStatus = JourneyStatus()
            val journeys = Position(0,0).findJourneySize(sampleInput, 'E', 0, journeyStatus)
            journeyStatus.minSize shouldBe 31
        }
        "part one with sample input" {
            partOne(sampleInput,'E','S') shouldBe 31
            partOne(sampleInput,'S','E') shouldBe 31
        }
        "part one with puzzle input" {
            partOne(puzzleInput,'S','E') shouldBe 497
        }
    })
    "Part two" should ({
        "part two with sample input" {
           partTwo(sampleInput) shouldBe 29
        }
        "part two with puzzle input" {
           partTwo(puzzleInput) shouldBe 492
        }
    })
})