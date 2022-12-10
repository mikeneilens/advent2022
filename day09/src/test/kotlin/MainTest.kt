import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().split("\n")
    "Part one" should ({
        "Position[0,0] isTouching Position[-1,-1]" {
            Direction.Touching.isDirectionFor(Position(-1,-1),Position(0,0)) shouldBe true
        }
        "Position[0,0] is not Touching Position[-1,-2]" {
            Direction.Touching.isDirectionFor(Position(0,0),Position(-1,-2)) shouldBe false
        }
        "Direction to move should be Right when tail is [1,1] and head is [3,1]" {
            val tail_1_1 = Position(1,1)
            val head_3_1 = Position(3,1)
            Direction.Right.isDirectionFor(tail_1_1, head_3_1 ) shouldBe true
        }
        "Direction to move should be Left when tail is [3,1] and head is [1,1]" {
            val tail_3_1 = Position(3,1)
            val head_1_1 = Position(1,1)
            Direction.Left.isDirectionFor(tail_3_1, head_1_1 ) shouldBe true
        }
        "Direction to move should be Up when tail is [1,3] and head is [1,1]" {
            val tail_1_3 = Position(1,3)
            val head_1_1 = Position(1,1)
            Direction.Up.isDirectionFor(tail_1_3, head_1_1 ) shouldBe true
        }
        "Direction to move should be Down when tail is [1,1] and head is [1,3]" {
            val tail_1_1 = Position(1,1)
            val head_1_3 = Position(1,3)
            Direction.Down.isDirectionFor(tail_1_1, head_1_3 ) shouldBe true
        }
        "Direction to move should be None when tail is [3,3] and head is [3,3]" {
            val tail_3_3 = Position(3,3)
            val head_3_3 = Position(3,3)
            Direction.None.isDirectionFor(tail_3_3, head_3_3 ) shouldBe true
        }
        "Direction to move should be DownRight when tail is [1,1] and head is [3,2]" {
            val tail_1_1 = Position(1,1)
            val head_3_2 = Position(3,2)
            Direction.DownRight.isDirectionFor(tail_1_1, head_3_2 ) shouldBe true
        }
        "Direction to move should be DownLeft when tail is [1,1] and head is [0,3]" {
            val tail_1_1 = Position(1,1)
            val head_0_3 = Position(0,3)
            Direction.DownLeft.isDirectionFor(tail_1_1, head_0_3 ) shouldBe true
        }
        "Direction to move should be UpRight when tail is [2,2] and head is [3,0]" {
            val tail_2_2 = Position(2,2)
            val head_3_0 = Position(3,0)
            Direction.UpRight.isDirectionFor(tail_2_2, head_3_0 ) shouldBe true
        }
        "Direction to move should be UpLeft when tail is [2,2] and head is [1,0]" {
            val tail_2_2 = Position(2,2)
            val head_1_0 = Position(1,0)
            Direction.UpLeft.isDirectionFor(tail_2_2, head_1_0 ) shouldBe true
        }

        "parse instructions correctly" {
            val instructions = sampleInput.parseToInstructions()
            instructions.size shouldBe 8
            instructions[0] shouldBe Instruction(Direction.Right, 4)
            instructions[1] shouldBe Instruction(Direction.Up, 4)
            instructions[2] shouldBe Instruction(Direction.Left, 3)
            instructions[3] shouldBe Instruction(Direction.Down, 1)
        }
        "partOn with sample input is 13" {
            partOne(sampleInput).size shouldBe 13
        }
        "partOne with puzzle input is 5619" {
            partOne(puzzleInput).size shouldBe 5619
        }
    })

    "Part two" should ({
        "partTwo with sample input is 1" {
            partTwo(sampleInput).size shouldBe 1
        }
        "partTwo with puzzle input is 2376" {
            partTwo(puzzleInput).size shouldBe 2376
        }
    })
})