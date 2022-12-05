fun helloWorld() = "hello world"

typealias Stack = List<Char>
typealias Stacks = List<Stack>

data class Command(val qty:Int, val from:Int, val to:Int)

//This is all the parsing stuff!
fun String.charAt(index:Int) = get(index * 4 + 1)

fun initialiseStacks(data: List<String>) = data.toStackData().fold(data.toStacks()){ stacks, line ->  line.addToStacks(stacks)}

fun List<String>.toStacks() = (1..noOfStacks()).map{listOf<Char>()}

fun List<String>.noOfStacks() = first{it.startsWith(" 1")}.trim().split("   ").map(String::toInt).last()

fun List<String>.toStackData() = filter { it.startsWith(' ') || it.startsWith('[') }.reversed().drop(1)

fun String.addToStacks(stacks: Stacks) = stacks.mapIndexed{ index, stack -> addLineToStack(index, stack, this)}

fun addLineToStack(index: Int, stack: Stack, line: String):Stack = if (line.charAt(index) != ' ') stack + line.charAt(index) else stack

fun parseIntoCommands(data: List<String>):List<Command> = data.filter{it.startsWith("move")}.map(String::toCommand)

fun String.toCommand(): Command {
    val components = removePrefix("move ").split(" from ", " to ").map(String::toInt)
    return Command(components[0],components[1] -1,components[2] -1)
}

//Actual part one
fun partOne(data:List<String>):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return commands.process(stacks, Stacks::mover).map{it.last()}.joinToString("")
}

fun List<Command>.process(stacks: Stacks, mover: Stacks.(Command)-> Stacks) = fold(stacks){ latestStacks, command -> latestStacks.mover(command)}

fun Stacks.mover(command: Command) = mapIndexed { index, stack -> move(index, stack,command) }

fun Stacks.move(index:Int, stack:Stack, command: Command, shouldReverse:Boolean = true) =  when(true) {
    (command.from == index  ) -> stack.dropLast(command.qty)
    (command.to == index && shouldReverse ) -> stack + this[command.from].takeLast(command.qty).reversed()
    (command.to == index && !shouldReverse ) -> stack + this[command.from].takeLast(command.qty)
    else -> stack
}

//Actual part two
fun partTwo(data:List<String>):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return commands.process(stacks, Stacks::mover2).map{it.last()}.joinToString("")
}

fun Stacks.mover2(command: Command) = mapIndexed { index, stack -> move(index, stack,command, false) }
