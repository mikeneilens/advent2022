import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    "Part one" should ({
        "creating ElfPositions from small sample data" {
            val elfPositions = smallSample.toElfPositions()
            elfPositions.contains(ElfPosition(2,1)) shouldBe true
            elfPositions.contains(ElfPosition(3,1)) shouldBe true
            elfPositions.contains(ElfPosition(2,2)) shouldBe true
            elfPositions.contains(ElfPosition(2,4)) shouldBe true
            elfPositions.contains(ElfPosition(3,4)) shouldBe true
        }
        "elf has noneWest if there are not any elves to the west, north west or north east"{
            val elfPositions = setOf(ElfPosition(3,3), ElfPosition(2,2), ElfPosition(2,4))
            ElfPosition(3,3).noneWest(elfPositions) shouldBe false
            ElfPosition(2,3).noneWest(elfPositions) shouldBe true
            moveSelectors[2].positionsAreClear(ElfPosition(3,3), elfPositions) shouldBe false
        }
        "proposed move for elve in small sample data " {
            val elfPositions = smallSample.toElfPositions()
            val elfPosition21 = ElfPosition(2,1)
            val elfPosition31 = ElfPosition(3,1)
            val elfPosition22 = ElfPosition(2,2)
            val elfPosition24 = ElfPosition(2,4)
            val elfPosition34 = ElfPosition(3,4)
            elfPosition21.proposedMove(elfPositions, 0) shouldBe ElfPosition(2,0)
            elfPosition31.proposedMove(elfPositions, 0) shouldBe ElfPosition(3,0)
            elfPosition22.proposedMove(elfPositions, 0) shouldBe ElfPosition(2,3)
            elfPosition24.proposedMove(elfPositions, 0) shouldBe ElfPosition(2,3)
            elfPosition34.proposedMove(elfPositions, 0) shouldBe ElfPosition(3,3)
        }
        "find proposed moves for all elves in small sample data" {
            val elfPositions = smallSample.toElfPositions()
            val elfPosition21 = ElfPosition(2,1)
            val elfPosition22 = ElfPosition(2,2)
            val elfPosition24 = ElfPosition(2,4)
            val moveRegister = mutableMapOf<ElfPosition, Int>()
            val proposedMoves = elfPositions.findProposedMoves(0, moveRegister)
            proposedMoves[0] shouldBe ProposedMove(elfPosition21, ElfPosition(2,0))
            moveRegister[ElfPosition(2,0)] shouldBe 1
            proposedMoves[2] shouldBe ProposedMove(elfPosition22, ElfPosition(2,3))
            proposedMoves[3] shouldBe ProposedMove(elfPosition24, ElfPosition(2,3))
            moveRegister[ElfPosition(2,3)] shouldBe 2
        }
        "find new elf positions for all elves in small sample data" {
            val elfPositions = smallSample.toElfPositions()
            val newElfPositions = elfPositions.newElfPositions(0)
            newElfPositions.contains(ElfPosition(2,0)) shouldBe true
            newElfPositions.contains(ElfPosition(3,0)) shouldBe true
            newElfPositions.contains(ElfPosition(2,2)) shouldBe true
            newElfPositions.contains(ElfPosition(3,3)) shouldBe true
            newElfPositions.contains(ElfPosition(2,4)) shouldBe true
        }
        "process small sample data over 10 rounds" {
            val elfPositions = smallSample.toElfPositions()
            val newElfPositions = elfPositions.moveElves(10)
            newElfPositions.contains(ElfPosition(2, 0)) shouldBe true
            newElfPositions.contains(ElfPosition(4, 1)) shouldBe true
            newElfPositions.contains(ElfPosition(0, 2)) shouldBe true
            newElfPositions.contains(ElfPosition(4, 3)) shouldBe true
            newElfPositions.contains(ElfPosition(2, 5)) shouldBe true
        }
        "process large sample data over 10 rounds" {
            val elfPositions = sampleInput.toElfPositions()
            val newElfPositions = elfPositions.moveElves(10)
            newElfPositions.toString(0) shouldBe "......#....."
            newElfPositions.toString(1) shouldBe "..........#."
            newElfPositions.toString(2) shouldBe ".#.#..#....."
            newElfPositions.toString(3) shouldBe ".....#......"
            newElfPositions.toString(4) shouldBe "..#.....#..#"
            newElfPositions.toString(5) shouldBe "#......##..."
            newElfPositions.toString(6) shouldBe "....##......"
            newElfPositions.toString(7) shouldBe ".#........#."
            newElfPositions.toString(8) shouldBe "...#.#..#..."
            newElfPositions.toString(9) shouldBe "............"
            newElfPositions.toString(10) shouldBe "...#..#..#.."
        }
        "partone with sample input is 110" {
            partOne(sampleInput) shouldBe 110
        }
        "partone with puzzle input is 3906" {
            partOne(puzzleInput) shouldBe 3906
        }
    })
    "Part two" should ({
        "part two with sample input is 20" {
            partTwo(sampleInput) shouldBe 20
        }
        "parttwo with puzzle input is 895" {
            partTwo(puzzleInput) shouldBe 895
        }
    })
})

fun ElfPositions.toString(row:Int) =
    (minOf { it.x }..maxOf { it.x }).joinToString("") { if (contains(ElfPosition(it, row))) "#" else "." }

