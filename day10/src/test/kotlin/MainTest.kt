import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {

    val sampleInput = """
        noop
        addx 3
        addx -5
    """.trimIndent().split("\n")
    "Part one" should ({
        "parse sample input into a program" {
            val program = sampleInput.parseToProgram()
            program.programSteps[0] shouldBe ProgramStep.NOOP
            program.programSteps[1] shouldBe ProgramStep.AddX
            program.programSteps[2] shouldBe ProgramStep.Number(3)
            program.programSteps[3] shouldBe ProgramStep.AddX
            program.programSteps[4] shouldBe ProgramStep.Number(-5)
        }
        "running program from sample input gves x values of  1,1,4,4,-1 " {
            val program = sampleInput.parseToProgram()
            program.run() shouldBe listOf(ProgramResult(1, 1), ProgramResult(1, 1), ProgramResult(1, 4), ProgramResult(4, 4), ProgramResult(4, -1))
        }
        "running program with large sample gives signal strength of 420 at cycle 20" {
            val program = largeSample.parseToProgram()
            val result = program.run()
            result.signalStringDuring(cycle = 20) shouldBe 420
        }
        "running program with large sample gives signal strength of 1140 at cycle 60" {
            val program = largeSample.parseToProgram()
            val result = program.run()
            result.signalStringDuring(cycle = 60) shouldBe 1140
        }
        "running program with large sample gives signal strength of 1800 at cycle 100" {
            val program = largeSample.parseToProgram()
            val result = program.run()
            result.signalStringDuring(cycle = 100) shouldBe 1800
        }
        "running program with large sample gives signal strength of 2880 at cycle 180" {
            val program = largeSample.parseToProgram()
            val result = program.run()
            result.signalStringDuring(cycle = 180) shouldBe 2880
        }
        "running program with large sample gives signal strength of 3960 at cycle 220" {
            val program = largeSample.parseToProgram()
            val result = program.run()
            result.signalStringDuring(cycle = 220) shouldBe 3960
        }
        "part one with large sample gives total signal strength of 13140" {
            partOne(largeSample) shouldBe 13140
        }
        "part one with puzzle input gives total signal strength of 13920" {
            partOne(puzzleInput) shouldBe 13920
        }
    })
    "Part two" should ({
        "draw first line of CRT using large sample data" {
            val program = largeSample.parseToProgram()
            val result = program.run().take(40)
            val crtLine = crtLine(result)
            crtLine shouldBe "##..##..##..##..##..##..##..##..##..##.."
        }
        "draw 2nd line of CRT using large sample data" {
            val program = largeSample.parseToProgram()
            val result = program.run().drop(40).take(40)
            val crtLine = crtLine(result)
            crtLine shouldBe "###...###...###...###...###...###...###."
        }
        "draw crt image using large sample data" {
            val expectedResult = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent().split("\n")
            val program = largeSample.parseToProgram()
            createImage(program.run()) shouldBe expectedResult
        }
        "part two using puzzle input" {
            val expectedResult = """
                ####..##..#....#..#.###..#....####...##.
                #....#..#.#....#..#.#..#.#....#.......#.
                ###..#....#....####.###..#....###.....#.
                #....#.##.#....#..#.#..#.#....#.......#.
                #....#..#.#....#..#.#..#.#....#....#..#.
                ####..###.####.#..#.###..####.#.....##..
            """.trimIndent().split("\n")
            partTwo(puzzleInput) shouldBe expectedResult
        }
    })
})