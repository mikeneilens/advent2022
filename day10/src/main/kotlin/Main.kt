sealed class ProgramStep {
    object NOOP:ProgramStep()
    object AddX:ProgramStep()
    data class Number(val value:Int):ProgramStep() {
        override fun calcDuringAndAfter(x: Int) =  ProgramResult(x, x + value )
    }

    open fun calcDuringAndAfter(x: Int): ProgramResult = ProgramResult(x,x)
}

data class Program(val programSteps:List<ProgramStep>) {
    fun run() = programSteps.fold( listOf(ProgramResult()) ) { programResults, programStep ->
                     programResults + programStep.calcDuringAndAfter(programResults.last().after)
    }.drop(1)
}

data class ProgramResult(val during:Int = 1, val after:Int = 1)

fun List<ProgramResult>.signalStrengthDuring(cycle:Int) = get(cycle -1 ).during * cycle

fun List<String>.parseToProgram() = Program(flatMap(String::toListOfProgramSteps))

fun String.toListOfProgramSteps() = split(" ").map(String::toProgramStep)

fun String.toProgramStep() = if (this == "noop") ProgramStep.NOOP else if (this == "addx") ProgramStep.AddX else ProgramStep.Number(this.toInt())

fun partOne(data:List<String>): Int {
    val program = data.parseToProgram()
    val output = program.run()
    return (20..220).step(40).sumOf { output.signalStrengthDuring(it) }
}

//part two
fun createImage(programResults:List<ProgramResult>) = programResults.chunked(40).map{crtLine(it)}

fun crtLine(programResults:List<ProgramResult>) =
    (0..39).map{ pixelPosition ->
        if (spriteOverlapsPixel(programResults, pixelPosition)) '#'
        else '.'  }.joinToString("")

private fun spriteOverlapsPixel(programResults: List<ProgramResult>, pixelPosition: Int) = pixelPosition in programResults.sprite(pixelPosition)

fun List<ProgramResult>.sprite(pixelPosition: Int) = listOf(get(pixelPosition).during,get(pixelPosition).during - 1, get(pixelPosition).during + 1 )

fun partTwo(data: List<String>):List<String> {
    val results = data.parseToProgram().run()
    val image = createImage(results)
    image.forEach { println(it) }
    return image
}