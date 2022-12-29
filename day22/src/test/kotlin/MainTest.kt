import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
________...#
________.#..
________#...
________....
...#.......#
........#...
..#....#....
..........#.
________...#....
________.....#..
________.#......
________......#.

10R5L5R10L4R5L5""".trimIndent().split("\n")

    "Part one" should ({
        "parse instructions corrctly" {
            val instructions = sampleInput.parseToInstructions()
            instructions.size shouldBe 13
            instructions[0] shouldBe Instruction.Move(10)
            instructions[1] shouldBe Instruction.RotateRight
            instructions[2] shouldBe Instruction.Move(5)
            instructions[3] shouldBe Instruction.RotateLeft
            instructions[4] shouldBe Instruction.Move(5)
            instructions[5] shouldBe Instruction.RotateRight
            instructions[6] shouldBe Instruction.Move(10)
            instructions[7] shouldBe Instruction.RotateLeft
            instructions[8] shouldBe Instruction.Move(4)
            instructions[9] shouldBe Instruction.RotateRight
            instructions[10] shouldBe Instruction.Move(5)
            instructions[11] shouldBe Instruction.RotateLeft
            instructions[12] shouldBe Instruction.Move(5)
        }
        "create map" {
            val map = sampleInput.createMap()
            map[Position(9,1)]?.type shouldBe '.'
            map[Position(10,1)]?.type shouldBe '.'
            map[Position(11,1)]?.type shouldBe '.'
            map[Position(12,1)]?.type shouldBe '#'
            map[Position(1,5)]?.type shouldBe '.'
            map[Position(2,5)]?.type shouldBe '.'
            map[Position(3,5)]?.type shouldBe '.'
            map[Position(4,5)]?.type shouldBe '#'
        }

        "turn person right" {
            val person = Person(Tile('.'), "E", mapOf<Position, Tile>())
            person.turnRight().direction shouldBe "S"
            person.turnRight().turnRight().direction shouldBe "W"
            person.turnRight().turnRight().turnRight().direction shouldBe "N"
            person.turnRight().turnRight().turnRight().turnRight().direction shouldBe "E"
        }
        "turn person left" {
            val person = Person(Tile('.'), "E", mapOf<Position, Tile>())
            person.turnLeft().direction shouldBe "N"
            person.turnLeft().turnLeft().direction shouldBe "W"
            person.turnLeft().turnLeft().turnLeft().direction shouldBe "S"
            person.turnLeft().turnLeft().turnLeft().turnLeft().direction shouldBe "E"
        }
        "move person 1 right" {
            val map = sampleInput.createMap()
            val person = Person(map.getValue(Position(9,1)), "E", map)
            val moveddPerson = person.move(1)
            moveddPerson.tile shouldBe map[Position(10,1)]
        }
        "move person 3 right so that it is blocked" {
            val map = sampleInput.createMap()
            val person = Person(map.getValue(Position(9,1)), "E", map)
            val moveddPerson = person.move(3)
            moveddPerson.tile shouldBe map[Position(11,1)]
        }
        "part one with puzzle input" {
            partOne(puzzleInput, edgeMapP1) shouldBe 106094
        }
    })
    "Part two" should ({
        "positions for a line" {
            Line(Position(1,1), Position(4,1)).positions() shouldBe listOf(Position(1,1),Position(2,1),Position(3,1),Position(4,1))
            Line(Position(4,1), Position(1,1)).positions() shouldBe listOf(Position(4,1),Position(3,1),Position(2,1),Position(1,1))
            Line(Position(1,1), Position(1,4)).positions() shouldBe listOf(Position(1,1),Position(1,2),Position(1,3),Position(1,4))
            Line(Position(1,4), Position(1,1)).positions() shouldBe listOf(Position(1,4),Position(1,3),Position(1,2),Position(1,1))
        }
        "update map with all initial next positions for each tile" {
            val map = puzzleInput.createMap()
            map.updateNextPositionOfTiles()
            map.getValue(Position(51,1)).next["E"] shouldBe  Position(52,1)
            map.getValue(Position(51,1)).next["W"] shouldBe  null
            map.getValue(Position(51,1)).next["N"] shouldBe  null
            map.getValue(Position(51,1)).next["S"] shouldBe  Position(51,2)
        }
        "update first line of the map with next positions from line that maps to the top line"{
            val map = puzzleInput.createMap()
            map.updateNextPositionOfTiles()
            map.updateEdge(Line(a,b), Line(r,t,"E"))
            map.getValue(Position(51,1)).next["N"] shouldBe  Position(1,151)
            map.getValue(Position(52,1)).next["N"] shouldBe  Position(1,152)
            map.getValue(Position(53,1)).next["N"] shouldBe  Position(1,153)
            map.getValue(Position(54,1)).next["N"] shouldBe  Position(1,154)
            map.getValue(Position(51,1)).nextDirection["N"] shouldBe "E"
            map.updateEdge(Line(a,g), Line(n,l,"E"))
            map.getValue(Position(51,1)).next["W"] shouldBe  Position(1,150)
            map.getValue(Position(51,1)).nextDirection["W"] shouldBe  "E"
        }
        "udpate all edges" {
            val map = puzzleInput.createMap()
            map.updateNextPositionOfTiles()
            map.updateEdges(edgeMapP2)
            map.values.all { it.next.size == 4 } shouldBe true
            map.getValue(Position(51,1)).next["N"] shouldBe  Position(1,151)
            map.getValue(Position(52,1)).next["N"] shouldBe  Position(1,152)
            map.getValue(Position(53,1)).next["N"] shouldBe  Position(1,153)
            map.getValue(Position(54,1)).next["N"] shouldBe  Position(1,154)
            map.getValue(Position(51,1)).nextDirection["N"] shouldBe "E"
            map.getValue(Position(1,151)).next["W"] shouldBe  Position(51,1)
            map.getValue(Position(1,152)).next["W"] shouldBe  Position(52,1)
            map.getValue(Position(1,153)).next["W"] shouldBe  Position(53,1)
            map.getValue(Position(1,154)).next["W"] shouldBe  Position(54,1)
            map.getValue(Position(1,151)).nextDirection["W"] shouldBe  "S"
            map.getValue(Position(51,1)).next["W"] shouldBe  Position(1,150)
            map.getValue(Position(51,1)).nextDirection["W"] shouldBe  "E"
        }
        "part two with puzzle input" {
            partTwo(puzzleInput) shouldBe 162038
        }
    })
})