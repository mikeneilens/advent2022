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
            Position(0,0) isTouching Position(-1,-1) shouldBe true
        }
        "Position[0,0] is not Touching Position[-1,-2]" {
            Position(0,0) isTouching Position(-1,-2) shouldBe false
        }
        "Direction to move should be Right when tail is [1,1] and head is [3,1]" {
            val tail_1_1 = Position(1,1)
            val head_3_1 = Position(3,1)
            Direction.Right.isDirectionWhen(tail_1_1, head_3_1 ) shouldBe true
        }
        "Direction to move should be Left when tail is [3,1] and head is [1,1]" {
            val tail_3_1 = Position(3,1)
            val head_1_1 = Position(1,1)
            Direction.Left.isDirectionWhen(tail_3_1, head_1_1 ) shouldBe true
        }
        "Direction to move should be Up when tail is [1,3] and head is [1,1]" {
            val tail_1_3 = Position(1,3)
            val head_1_1 = Position(1,1)
            Direction.Up.isDirectionWhen(tail_1_3, head_1_1 ) shouldBe true
        }
        "Direction to move should be Down when tail is [1,1] and head is [1,3]" {
            val tail_1_1 = Position(1,1)
            val head_1_3 = Position(1,3)
            Direction.Down.isDirectionWhen(tail_1_1, head_1_3 ) shouldBe true
        }
        "Direction to move should be None when tail is [3,3] and head is [3,3]" {
            val tail_3_3 = Position(3,3)
            val head_3_3 = Position(3,3)
            Direction.None.isDirectionWhen(tail_3_3, head_3_3 ) shouldBe true
        }
        "Direction to move should be DownRight when tail is [1,1] and head is [3,2]" {
            val tail_1_1 = Position(1,1)
            val head_3_2 = Position(3,2)
            Direction.DownRight.isDirectionWhen(tail_1_1, head_3_2 ) shouldBe true
        }
        "Direction to move should be DownLeft when tail is [1,1] and head is [0,3]" {
            val tail_1_1 = Position(1,1)
            val head_0_3 = Position(0,3)
            Direction.DownLeft.isDirectionWhen(tail_1_1, head_0_3 ) shouldBe true
        }
        "Direction to move should be UpRight when tail is [2,2] and head is [3,0]" {
            val tail_2_2 = Position(2,2)
            val head_3_0 = Position(3,0)
            Direction.UpRight.isDirectionWhen(tail_2_2, head_3_0 ) shouldBe true
        }
        "Direction to move should be UpLeft when tail is [2,2] and head is [1,0]" {
            val tail_2_2 = Position(2,2)
            val head_1_0 = Position(1,0)
            Direction.UpLeft.isDirectionWhen(tail_2_2, head_1_0 ) shouldBe true
        }
        "when Tail[1,1] and Head[3,1] tail should move to [2,1]" {
            val head = Position(3,1)
            val tail = Position(1,1)
            tail.moveTowards(head) shouldBe Position(2,1)
        }
        "when Tail[1,1] and Head[1,3] tail should move to [1,2]" {
            val head = Position(1,3)
            val tail = Position(1,1)
            tail.moveTowards(head) shouldBe Position(1,2)
        }
        "when Tail[1,3] and Head[2,1] tail should move to [2,2]" {
            val head = Position(2,1)
            val tail = Position(1,3)
            tail.moveTowards(head) shouldBe Position(2,2)
        }
        "when Tail[1,3] and Head[3,2] tail should move to [2,2]" {
            val head = Position(3,2)
            val tail = Position(1,3)
            tail.moveTowards(head) shouldBe Position(2,2)
        }
        "parse instructions correctly" {
            val instructions = sampleInput.parse()
            instructions.size shouldBe 8
            instructions[0] shouldBe Instruction(Direction.Right, 4)
            instructions[1] shouldBe Instruction(Direction.Up, 4)
            instructions[2] shouldBe Instruction(Direction.Left, 3)
            instructions[3] shouldBe Instruction(Direction.Down, 1)
        }
        "move head and tail at [0,4] Right 4 to [4,4] " {
            val head = Position(0,4)
            val tail = listOf(Position(0,4))
            val instruction = Instruction(Direction.Right,4)
            val audit = mutableSetOf(tail.last())
            val (newHead, newTail) = head.moveHead(instruction,tail,audit)
            newHead shouldBe Position(4,4)
            newTail shouldBe listOf(Position(3,4))
            audit shouldBe setOf(Position(0,4),Position(1,4),Position(2,4),Position(3,4))
        }
        "following all instructions causes tail to visit 13 places" {
            val start = Position(0,4)
            val tails = (1..1).map{start}
            val instructions = sampleInput.parse()
            val audit = mutableSetOf(start)
            val (head, tail) = instructions.processAll(start, tails, audit)
            head shouldBe Position(2,2)
            tail shouldBe listOf(Position(1,2))
            audit.size shouldBe 13
        }
        "partOne with sample input is 13" {
            partOne(sampleInput).size shouldBe 13
        }
        "partOne with puzzle input is 5619" {
            partOne(puzzleInput).size shouldBe 5619
        }
    })
    "Part two" should ({
        "following all instructions causes tail to visit 1 place" {
            val start = Position(0,4)
            val startTails = (1..9).map{start}
            val instructions = sampleInput.parse()
            val audit = mutableSetOf(start)
            val (head, tails) = instructions.processAll(start, startTails, audit)
            head shouldBe Position(2,2)
            tails[0] shouldBe Position(1,2)
            tails[1] shouldBe Position(2,2)
            tails[2] shouldBe Position(3,2)
            tails[3] shouldBe Position(2,2)
            tails[4] shouldBe Position(1,3)
            tails[5] shouldBe Position(0,4)
            tails[6] shouldBe Position(0,4)
            tails[7] shouldBe Position(0,4)
            tails[8] shouldBe Position(0,4)
            audit.size shouldBe 1
        }
        "partTwo with sample input is 1" {
            partTwo(sampleInput).size shouldBe 1
        }
        "partTwo with puzzle input is 2376" {
            partTwo(puzzleInput).size shouldBe 2376
        }
    })
})