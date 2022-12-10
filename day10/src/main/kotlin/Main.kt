sealed class ProgramStep {
    object NOOP:ProgramStep()
    object AddX:ProgramStep(){val process = {x:Int, y:Int -> x + y}}
    data class Number(val value:Int):ProgramStep() {val instruction = AddX}
}

data class Program(val programSteps:List<ProgramStep>) {
    fun run() = programSteps.fold( listOf(ProgramResult(1,1)) ) { programResults, programStep ->
            if (programStep is ProgramStep.Number)
                programResults + calcDuringAndAfter(programStep, programResults.last().after)
            else
                programResults + ProgramResult(programResults.last().after, programResults.last().after)
        }.drop(1)
    }

data class ProgramResult(val during:Int, val after:Int)

fun calcDuringAndAfter(programStep:ProgramStep.Number, x:Int) = ProgramResult(x, programStep.instruction.process(x, programStep.value) )

fun List<ProgramResult>.signalStringDuring(cycle:Int) = get(cycle -1 ).during * cycle

fun List<String>.parseToProgram():Program {
    val programSteps  = flatMap { line ->
        if (line.split(" ")[0] == "addx") line.asAddxProgramSteps() else  listOf(ProgramStep.NOOP)
     }
    return Program(programSteps)
}

fun String.asAddxProgramSteps() = listOf(ProgramStep.AddX ,ProgramStep.Number(split(" ")[1].toInt()))

fun partOne(data:List<String>): Int {
    val program = data.parseToProgram()
    val output = program.run()
    return (20..220).step(40).sumOf { output.signalStringDuring(it) }
}

//part two
fun createImage(programResults:List<ProgramResult>) = programResults.chunked(40).map{crtLine(it)}

fun crtLine(programResults:List<ProgramResult>):String =
    (0..39).map{ pixelPosition ->
        if (resultOverlapsPixel(programResults, pixelPosition)) '#'
        else '.'  }.joinToString("")

private fun resultOverlapsPixel(programResults: List<ProgramResult>, pixelPosition: Int) = pixelPosition in programResults.pixels(pixelPosition)

fun List<ProgramResult>.pixels(pixelPosition: Int) = listOf(get(pixelPosition).during,get(pixelPosition).during - 1, get(pixelPosition).during + 1 )

fun partTwo(data: List<String>):List<String> {
    val results = data.parseToProgram().run()
    val image = createImage(results)
    image.forEach { println(it) }
    return image
}