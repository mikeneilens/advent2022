typealias Worry = Long
typealias WorryUpdater = (Worry) -> Worry
typealias WorryReducer = (Worry) -> Worry
typealias MonkeyData = List<String>

data class Monkey(
    val monkeyNumber: Int,
    val items: MutableList<Long>,
    val worryUpdater: WorryUpdater,
    val monkeyToThrowTo: (Worry)->Int,
    var worryReducer: WorryReducer,
    var noOfOperations: Long = 0L )

fun List<MonkeyData>.toMonkeys(worryReducer: WorryReducer) = map{it.toMonkey(worryReducer)}

fun MonkeyData.toMonkey(worryReducer: WorryReducer)
   = Monkey(monkeyNumber(),startingItems(), worryUpdater(worryReducer), monkeyToThrowTo(divisibleRule()), worryReducer)

fun MonkeyData.monkeyNumber() = first().removePrefix("Monkey ").removeSuffix(":").toInt()

fun MonkeyData.startingItems() = get(1).removePrefix("  Starting items: ").split(", ").map(String::toLong).toMutableList()

fun MonkeyData.worryUpdater(worryReducer: WorryReducer): WorryUpdater {
    val (symbol, param2) = get(2).removePrefix("  Operation: new = ").split(" ").drop(1)
    return worryUpdater(symbol, param2, worryReducer)
}

fun worryUpdater(symbol:String, param2:String, worryReducer:WorryReducer): WorryUpdater =
    if (symbol == "+") {old -> worryReducer(old + param2.toLong()) }
    else if (symbol == "*" && param2 == "old") {old -> worryReducer( old * old) }
    else  {old -> worryReducer(old * param2.toLong()) }

fun MonkeyData.divisibleNo() = get(3).removePrefix("  Test: divisible by ").toLong()

fun MonkeyData.divisibleRule() =
    {worry:Worry, trueMonkey:Int, falseMonkey:Int -> if ((worry % divisibleNo()) == 0L) trueMonkey else falseMonkey}

fun MonkeyData.monkeyToThrowTo(divisibleRule: (Worry, Int, Int)-> Int): (Worry) -> Int {
    val trueMonkeyIndex = get(4).removePrefix("    If true: throw to monkey ").toInt()
    val falseMonkeyIndex = get(5).removePrefix("    If false: throw to monkey ").toInt()
    return { worry:Worry -> divisibleRule(worry, trueMonkeyIndex, falseMonkeyIndex)}
}

//partOne
fun List<Monkey>.processRound() = forEach{monkey -> processAll(monkey)}

fun List<Monkey>.processAll(monkey: Monkey) {
    monkey.items.forEach { item-> process(monkey, item) }
    monkey.items.removeAll{true}
}

fun List<Monkey>.process(monkey:Monkey, item:Worry) {
    val worryLevel = monkey.worryUpdater(item)
    val indexOfMonkeyToThrowTo = monkey.monkeyToThrowTo(worryLevel)
    get(indexOfMonkeyToThrowTo).items.add(worryLevel)
    monkey.noOfOperations += 1
}

fun partOne(data: List<MonkeyData>, worryReducer: WorryReducer = { worry -> worry / 3}, repeats:Int = 20 ):Long {
    val monkeys = data.toMonkeys(worryReducer)
    repeat(repeats){monkeys.processRound()}
    val (busiestMonkey, secondBusiestMonkey) = monkeys.sortedBy { it.noOfOperations }.takeLast(2)
    return busiestMonkey.noOfOperations * secondBusiestMonkey.noOfOperations
}

//part two
fun partTwo(data: List<MonkeyData>):Long {
    val lowestCommonMultiplier = data.map(List<String>::divisibleNo).toLowestCommonMultiplier()
    val worryReducer:WorryReducer =  {worry -> worry % lowestCommonMultiplier}
    return partOne(data, worryReducer, 10000 )
}

fun List<Long>.toLowestCommonMultiplier() = reduce { lcm, n -> lcm * n }