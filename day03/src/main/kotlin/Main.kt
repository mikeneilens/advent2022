data class Rucksack(val left:List<Char>, val right:List<Char>, val all:List<Char> = left + right) {
    fun findFirstInBothSides() = left.first{ right.contains(it)}
    fun findCodeForFirstInBothSides() = findFirstInBothSides().toValue()
}

fun String.toRucksack() = Rucksack(firstHalf(), secondHalf())

fun String.firstHalf() = toList().take(length/2)

fun String.secondHalf() = toList().drop(length/2)

fun Char.toValue() = if (this >= 'a') code - 96 else code - 38

fun partOne(data:List<String>) = data.map(String::toRucksack).sumOf(Rucksack::findCodeForFirstInBothSides)

//part two

data class ElvesBags(val ruckSacks:List<Rucksack>) {
    fun findBadge() = ruckSacks.first().all.first{ ruckSacks[1].all.contains(it) && ruckSacks[2].all.contains(it)}
    fun findCodeForBadge() = findBadge().toValue()
}

fun List<String>.toElvesBags() = map(String::toRucksack).chunked(3).map(::ElvesBags)

fun partTwo(data:List<String>) = data.toElvesBags().sumOf( ElvesBags::findCodeForBadge )

