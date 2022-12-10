sealed class ProgramStep {
    object NOOP:ProgramStep()
    object AddX:ProgramStep(){val process = {x:Int, y:Int -> x + y}}
    data class Number(val value:Int):ProgramStep() {val instruction = AddX}
}

data class Program(val programSteps:List<ProgramStep>) {
    fun run() = programSteps.fold(listOf(ProgramResult(1,1))) { programResult, programStep ->
            if (programStep is ProgramStep.Number)
                programResult + calcDuringAndAfter(programStep, programResult.last().after)
            else
                programResult + ProgramResult(programResult.last().after, programResult.last().after)
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

fun createImage(result:List<ProgramResult>):List<String> {
    val results:List<List<ProgramResult>> = result.chunked(40)
    return results.map{crtLine(it)}
}

fun crtLine(result:List<ProgramResult>):String =
    (0..39).map{ pixelPosition ->
        if (resultOverlapsPixel(result, pixelPosition)) '#'
        else '.'  }.joinToString("")

private fun resultOverlapsPixel(result: List<ProgramResult>, pixelPosition: Int) = pixelPosition in result.pixels(pixelPosition)

fun List<ProgramResult>.pixels(pixelPosition: Int) = listOf(get(pixelPosition).during,get(pixelPosition).during - 1, get(pixelPosition).during + 1 )

fun partTwo(data: List<String>):List<String> {
    val results = data.parseToProgram().run()
    val image = createImage(results)
    image.forEach { println(it) }
    return image
}