fun helloWorld() = "hello world"

typealias Stack = List<Char>
typealias Stacks = List<Stack>

data class Command(val qty:Int, val from:Int, val to:Int)

fun String.addToStacks(stacks: Stacks):Stacks =
     stacks.mapIndexed{ index, stack -> addLineToStack(index, stack, this)}

fun addLineToStack(index: Int, stack: Stack, line: String):Stack  {
    val char = line.get(index * 4 + 1)
    return if (char != ' ') stack + char else stack
}

fun initialiseStacks(data: List<String>):Stacks {
    val emptyStacks:Stacks = listOf<Stack>(listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf(),listOf()).take(data.noOfStacks())
    val lines = data.filter { it.startsWith(' ') || it.startsWith('[') }.reversed().drop(1)
    return lines.fold(emptyStacks){ stacks, line ->  line.addToStacks(stacks)}
}

fun List<String>.noOfStacks() = first{it.startsWith(" 1")}.trim().split("   ").map(String::toInt).last()

fun parseIntoCommands(data: List<String>):List<Command> = data.filter{it.startsWith("move")}
    .map {line ->
        val components = line.removePrefix("move ").split(" from ", " to ").map(String::toInt)
        Command(components[0],components[1],components[2])
    }

fun partOne(data:List<String>):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return commands.process(stacks, Stacks::mover).map{it.last()}.joinToString("")
}

fun List<Command>.process(stacks: Stacks, mover: Stacks.(Command)-> Stacks) = fold(stacks){ stacks, command -> stacks.mover(command)}

fun Stacks.mover(command: Command):Stacks = mapIndexed { index, stack -> move(index, stack,command) }

fun Stacks.move(index:Int, stack:Stack, command: Command, shouldReverse:Boolean = true) =  when(true) {
    (command.from - 1 == index  ) -> stack.dropLast(command.qty)
    (command.to -1 == index && shouldReverse ) -> stack + this[command.from -1].takeLast(command.qty).reversed()
    (command.to -1 == index && !shouldReverse ) -> stack + this[command.from -1].takeLast(command.qty)
    else -> stack
}

fun partTwo(data:List<String>):String {
    val commands = parseIntoCommands(data)
    val stacks = initialiseStacks(data)
    return commands.process(stacks, Stacks::mover2).map{it.last()}.joinToString("")
}

fun Stacks.mover2(command: Command):Stacks = mapIndexed { index, stack -> move(index, stack,command, false) }
