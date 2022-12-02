enum class Item(val resultValue: Int) {
    Rock(1), Paper(2), Scissors(3)
}

val itemMap = mapOf("A" to Item.Rock, "B" to Item.Paper, "C" to Item.Scissors, "X" to Item.Rock, "Y" to Item.Paper, "Z" to Item.Scissors)

val resultMap = mapOf(
    Pair(Item.Rock, Item.Scissors) to 6,
    Pair(Item.Paper, Item.Rock) to 6,
    Pair(Item.Scissors, Item.Paper) to 6,
    Pair(Item.Rock, Item.Rock) to 3,
    Pair(Item.Paper, Item.Paper) to 3,
    Pair(Item.Scissors, Item.Scissors) to 3,
)

data class Round(val theirs: Item, val yours: Item) {
    fun yourScore() = yours.resultValue + (resultMap[Pair(yours, theirs)] ?: 0)
}

fun parseInput(data: List<String>, lineParser: (String)->Round ) = data.map(lineParser)

fun parseLine(line: String ) =
   Round(itemMap.getValue(line.split(" ")[0]), itemMap.getValue(line.split(" ")[1]))

fun List<Round>.totalScore() = sumOf(Round::yourScore)

fun partOne(data: List<String>) = parseInput(data, ::parseLine).totalScore()

//part two

val translateTheirToYours = mapOf(
    Pair("X", Item.Rock) to Item.Scissors,
    Pair("X", Item.Paper) to Item.Rock,
    Pair("X", Item.Scissors) to Item.Paper,
    Pair("Z", Item.Rock) to Item.Paper,
    Pair("Z", Item.Paper) to Item.Scissors,
    Pair("Z", Item.Scissors) to Item.Rock)

fun parseLine2(line: String):Round {
    val theirs = itemMap.getValue(line.split(" ")[0])
    val instruction = line.split(" ")[1]
    return Round(theirs, calculateYours(instruction, theirs))
}

fun calculateYours(instruction: String, theirs: Item) = translateTheirToYours[Pair(instruction,theirs)] ?: theirs

fun partTwo(data:List<String>) = parseInput(data, ::parseLine2).totalScore()