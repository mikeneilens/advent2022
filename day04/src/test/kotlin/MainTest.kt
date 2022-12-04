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
        "assignment 2-8 fully contains 3-7" {
            Section(Assignment(2,8),Assignment(3,7)).assignmentContainsOtherAssignment() shouldBe true
        }
        "assignment 6-6 fully contained by 4-6" {
            Section(Assignment(6,6),Assignment(4,6)).assignmentContainsOtherAssignment() shouldBe true
        }
        "assignment 2-6 is not fully contained by 4-8" {
            Section(Assignment(2,6),Assignment(4,8)).assignmentContainsOtherAssignment() shouldBe false
        }
        "sampleInput with part one contains two assignments with one inside the other" {
            partOne(sampleInput) shouldBe 2
        }
        "puzzleInput with part one contains 571 assignments with one inside the other" {
            partOne(puzzleInput) shouldBe 571
        }
    })
    "Part two" should ({
        "assignment 2-4 and 6-8 do not overlap and 2-3 and 4-5 do not overlap" {
            Section(Assignment(2,4),Assignment(6,8)).assignmentsOverlap() shouldBe false
            Section(Assignment(4,5),Assignment(2,3)).assignmentsOverlap() shouldBe false
        }
        "assignments 5-7,7-9, 2-8,3-7, 6-6,4-6, and 2-6,4-8) do overlap" {
            Section(Assignment(5,7),Assignment(7,9)).assignmentsOverlap() shouldBe true
            Section(Assignment(2,8),Assignment(3,7)).assignmentsOverlap() shouldBe true
            Section(Assignment(6,6),Assignment(4,6)).assignmentsOverlap() shouldBe true
            Section(Assignment(2,6),Assignment(4,8)).assignmentsOverlap() shouldBe true
        }
        "sampleInput with part two contains 4 pairs with one inside the other" {
            partTwo(sampleInput) shouldBe 4
        }
        "puzzleInput with part two contains 917 pairs with one inside the other" {
            partTwo(puzzleInput) shouldBe 917
        }
    })
})