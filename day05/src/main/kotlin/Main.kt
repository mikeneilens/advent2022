typealias InputData = List<String>
typealias Stack = List<Char>
typealias Stacks = List<Stack>

fun Stack() = emptyList<Char>()

data class Command(val qty:Int, val from:Int, val to:Int)

//This is all the parsing stuff!
fun initialiseStacks(data: InputData) = data.findStackData().fold(data.createEmptyStacks()){ stacks, line ->  line.addToStacks(stacks)}

fun InputData.createEmptyStacks() = (1..noOfStacks).toList().map{Stack()}

fun String.addToStacks(stacks: Stacks) = stacks.mapIndexed{ index, stack -> addLineToStack(index, stack, this)}

fun addLineToStack(index: Int, stack: Stack, line: String):Stack = if (line.charAt(index) != ' ') stack + line.charAt(index) else stack

fun String.charAt(index:Int) = get(index * 4 + 1)

fun parseIntoCommands(data: InputData):List<Command> = data.findMoveData().map(String::toCommand)

fun String.toCommand(): Command {
    val components = removePrefix("move ").split(" from ", " to ").map(String::toInt)
    return Command(components[0],components[1] -1,components[2] -1)
}

fun InputData.findStackData() = filter { it.startsWith(' ') || it.startsWith('[') }.reversed().drop(1)
fun InputData.findQtyData() = first{it.startsWith(" 1")}.trim()
fun InputData.findMoveData() = filter{it.startsWith("move")}
val InputData.noOfStacks get() = findQtyData().split("   ").last().toInt()

//Actual part one
fun partOne(data: InputData):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return stacks.process(commands, Stacks::crateMover9000).map(Stack::last).joinToString("")
}

fun Stacks.process(commands: List<Command>, mover: Stacks.(Command)-> Stacks) = commands.fold(this){ stacks, command -> stacks.mover(command)}

fun Stacks.crateMover9000(command: Command) = mapIndexed { index, stack -> move(index, stack,command, true) }

fun Stacks.move(index:Int, stack:Stack, command: Command, shouldReverseStack:Boolean) =  when(true) {
    (command.from == index) -> stack.dropLast(command.qty)
    (command.to == index && shouldReverseStack) -> stack + this[command.from].takeLast(command.qty).reversed()
    (command.to == index) -> stack + this[command.from].takeLast(command.qty)
    else -> stack
}

//Actual part two
fun partTwo(data: InputData):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return stacks.process(commands, Stacks::crateMover9001).map(Stack::last).joinToString("")
}

fun Stacks.crateMover9001(command: Command) = mapIndexed { index, stack -> move(index, stack,command, false) }
