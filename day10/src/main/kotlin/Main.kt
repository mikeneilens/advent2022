sealed class ProgramStep {
    object NOOP:ProgramStep()
    object AddX:ProgramStep(){val process = {x:Int, y:Int -> x + y}}
    data class Number(val value:Int):ProgramStep() {val instruction = AddX}
}

data class Program(val programSteps:List<ProgramStep>) {
    fun run():List<Pair<Int, Int>> {
        var x = 1
        return programSteps.map{programStep -> if (programStep is ProgramStep.Number) {
            val duringAndAfter = calcDuringAndAfter(programStep, x)
            x = duringAndAfter.second
            duringAndAfter
        } else Pair(x,x) }
    }
}

fun calcDuringAndAfter(programStep:ProgramStep.Number, x:Int):Pair<Int, Int> = Pair(x, programStep.instruction.process(x, programStep.value) )

fun List<Pair<Int,Int>>.signalStringDuring(cycle:Int) = get(cycle -1 ).first * cycle

fun List<String>.parseToProgram():Program {
    val programSteps  = mutableListOf<ProgramStep>()
    forEach { line ->
        when (line.split(" ")[0]) {
            "addx" -> {
                programSteps.add(ProgramStep.AddX )
                programSteps.add(ProgramStep.Number(line.split(" ")[1].toInt()))
            }
            else -> programSteps.add(ProgramStep.NOOP)
        }
    }
    return Program(programSteps)
}

fun partOne(data:List<String>): Int {
    val program = data.parseToProgram()
    val output = program.run()
    return output.signalStringDuring(20) + output.signalStringDuring(60) +
            output.signalStringDuring(100) + output.signalStringDuring(140) +
            output.signalStringDuring(180) + output.signalStringDuring(220)
}

fun createImage(result:List<Pair<Int, Int>>):List<String> {
    val results:List<List<Pair<Int, Int>>> = result.chunked(40)
    return results.map{crtLine(it)}
}

fun crtLine(result:List<Pair<Int, Int>>):String =
    (0..39).map{ pixelPosition ->
        if (result[pixelPosition].first == pixelPosition || result[pixelPosition].first - 1 == pixelPosition || result[pixelPosition].first + 1 == pixelPosition      )
            '#'
        else '.'  }.joinToString("")

fun partTwo(data: List<String>):List<String> {
    val results = data.parseToProgram().run()
    val image = createImage(results)
    image.forEach { println(it) }
    return image
}