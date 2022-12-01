package day01

fun parseInput(data:String) = data.split("\n\n").map{it.split("\n").map(String::toInt)}

fun List<List<Int>>.sumCaloriesForEachElf() = map(List<Int>::sum)

fun List<Int>.findMaxCaroliesCarried() = max()

fun partOne(data:String) = parseInput(data).sumCaloriesForEachElf().findMaxCaroliesCarried()

fun List<Int>.calcTotalCaloriesOfTopThreeElves() = sortedDescending().take(3).sum()

fun partTwo(data:String) = parseInput(data).sumCaloriesForEachElf().calcTotalCaloriesOfTopThreeElves()

