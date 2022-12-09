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
            val instructions = sampleInput.parse()
            val audit = mutableSetOf(start)
            val (head, tail) = instructions.processAll(start, 1, audit)
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
            val instructions = sampleInput.parse()
            val audit = mutableSetOf(start)
            val (head, tails) = instructions.processAll(start, 9, audit)
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