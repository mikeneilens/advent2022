data class Monkey(
    val monkeyNumber:Int,
    val items:MutableList<Long>,
    val operation:(Long)->Long,
    val monkeyToThrowTo:(Long)->Int,
    var worryReducer:(Long)->Long = { n -> n /3},
    var noOfOperations:Int = 0)

fun List<String>.toMonkey(worryReducer: (Long)->Long)
= Monkey(monkeyNumber(),startingItems(), operation(worryReducer), monkeyToThrowTo(testDivisible()),worryReducer)

fun List<String>.monkeyNumber() = first().removePrefix("Monkey ").removeSuffix(":").toInt()

fun List<String>.startingItems() = get(1).removePrefix("  Starting items: ").split(", ").map(String::toLong).toMutableList()

fun List<String>.operation(worryReducer: (Long)->Long): (Long)-> Long {
    val (symbol, param2) = get(2).removePrefix("  Operation: new = ").split(" ").drop(1)
    return operation(symbol, param2, worryReducer)
}
fun operation(symbol:String, param2:String, worryReducer: (Long)->Long): (Long)->Long {
    return if (symbol == "+") {old:Long -> worryReducer(old + param2.toLong()) }
    else if (symbol == "*" && param2 == "old") {old:Long -> worryReducer( old * old) }
    else  {old:Long -> worryReducer(old * param2.toInt()) }
}

fun List<String>.testDivisibleBy(): Long = get(3).removePrefix("  Test: divisible by ").toLong()

fun List<String>.testDivisible(): (Long, Int, Int)-> Int {
    return {n:Long, trueMonkey:Int, falseMonkey:Int -> if ((n % testDivisibleBy()) == 0L) trueMonkey else falseMonkey}
}

fun List<String>.monkeyToThrowTo(divisibleRule:(Long, Int, Int)-> Int): (Long) -> Int {
    val trueMonkey = get(4).removePrefix("    If true: throw to monkey ").toInt()
    val falseMonkey = get(5).removePrefix("    If false: throw to monkey ").toInt()
    return { n:Long -> divisibleRule(n, trueMonkey, falseMonkey)}
}

fun List<List<String>>.toMonkeys(worryReducer: (Long)->Long) = map{it.toMonkey(worryReducer)}

fun List<Monkey>.processRound() = forEach{monkey -> processAll(monkey)}

fun List<Monkey>.processAll(monkey: Monkey) {
    monkey.items.indices.forEach { process(monkey, it) }
    monkey.items.removeAll{true}
}

fun List<Monkey>.process(monkey:Monkey, itemNdx:Int) {
    val item = monkey.items[itemNdx]
    val worryLevel = monkey.operation(item)
    val monkeyToThrowTo = monkey.monkeyToThrowTo( worryLevel)
    get(monkeyToThrowTo).items.add(worryLevel)
    monkey.noOfOperations += 1
}

fun partOne(data: List<List<String>>, worryReducer: (Long) -> Long = { n -> n / 3}, repeats:Int = 20 ):Long {
    val monkeys = data.toMonkeys(worryReducer)
    repeat(repeats){monkeys.processRound()}
    val (m1, m2) = monkeys.sortedBy { it.noOfOperations }.takeLast(2)
    return m1.noOfOperations.toLong() * m2.noOfOperations.toLong()
}

fun partTwo(data: List<List<String>>):Long {
    val lowestCommonMultiplier = data.map{it.testDivisibleBy()}.toLowestCommonMultiplier()
    return partOne(data, {n -> n % lowestCommonMultiplier}, 10000 )
}

fun List<Long>.toLowestCommonMultiplier() = reduce { lcm, n -> lcm * n }