val lookUpScore = mapOf(
    "A X" to 1 + 3 ,
    "B Y" to 2 + 3,
    "C Z" to 3 + 3,
    "B X" to 1,
    "C X" to 1 + 6,
    "A Y" to 2 + 6,
    "C Y" to 2,
    "A Z" to 3,
    "B Z" to 3 + 6,
)

fun partOne(data: List<String>) = data.sumOf { lookUpScore.getValue(it) }

//part two
val lookUpScore2 = mapOf(
    "A X" to 3 ,
    "B Y" to 2 + 3,
    "C Z" to 1 + 6,
    "B X" to 1 ,
    "C X" to 2 ,
    "A Y" to 1 + 3,
    "C Y" to 3 + 3,
    "A Z" to 2 + 6,
    "B Z" to 3 + 6,
)

fun partTwo(data: List<String>) = data.sumOf { lookUpScore2.getValue(it) }
