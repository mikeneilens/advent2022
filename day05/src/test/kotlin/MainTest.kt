import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {

    val sampleData = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent().split("\n")
    "Part one" should ({
        "add a line of text to a list of stacks" {
            val line = "[G] [L] [M] [S] [S] [C]     [T] [V]"
            val stacks:Stacks = listOf(listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf())
            line.addToStacks(stacks) shouldBe listOf(listOf('G'),listOf('L'),listOf('M'),listOf('S'),listOf('S'),listOf('C'),listOf(),listOf('T'),listOf('V'))
        }
        "calculate no of stacks on sample data is 3" {
            sampleData.noOfStacks() shouldBe 3
        }
        "initialise stacks from puzzle input" {
            initialiseStacks(puzzleInput)[0] shouldBe listOf('R','N','P','G')
            initialiseStacks(puzzleInput)[8] shouldBe listOf('L','P','B','V','M','J','F')
        }
        "parse sample data into commands" {
            val commands = parseIntoCommands(sampleData)
            commands.size shouldBe 4
            commands[0] shouldBe Command(1,1,0)
            commands[1] shouldBe Command(3,0,2)
            commands[2] shouldBe Command(2,1,0)
            commands[3] shouldBe Command(1,0,1)
        }
        "one crate is moved from stack 2 to stack 1" {
            val stacks = initialiseStacks(sampleData)
            val command = Command(1, 1, 0)
            val newStacks  = stacks.mover(command)
            newStacks[0] shouldBe listOf('Z','N','D')
            newStacks[1] shouldBe listOf('M','C')
            newStacks[2] shouldBe listOf('P')
        }
        "process all stacks for sample data should be CMZ" {
            val stacks = listOf(listOf('Z', 'N'), listOf('M', 'C', 'D'), listOf('P'))
            val commands = listOf(Command(qty=1, from=1, to=0), Command(qty=3, from=0, to=2), Command(qty=2, from=1, to=0), Command(qty=1, from=0, to=1))
            commands.process(stacks, Stacks::mover) shouldBe listOf(listOf('C'),listOf('M'), listOf('P', 'D', 'N', 'Z'))
        }
        "answer for part one using sample data is CMZ " {
            partOne(sampleData) shouldBe "CMZ"
        }
        "answer for part one using puzzle input is HBTMTBSDC " {
            partOne(puzzleInput) shouldBe "HBTMTBSDC"
        }

    })
    "Part two" should ({
        "answer for part two using sample data is MCD " {
            partTwo(sampleData) shouldBe "MCD"
        }
        "answer for part two using puzzle input is PQTJRSHWS " {
            partTwo(puzzleInput) shouldBe "PQTJRSHWS"
        }
    })
})