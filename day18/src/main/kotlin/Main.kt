data class Position( val x:Int, val y:Int, val z:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y, z + other.z)

    fun isWithinRange(xRange:IntRange, yRange:IntRange, zRange:IntRange) = x in xRange && y in yRange && z in zRange
}

typealias WaterMap = MutableSet<Position>

fun Cube(x:Int, y:Int, z:Int) = Cube(Position(x,y,z))

val listOfAdjacentPositions = listOf(Position(1,0,0), Position(-1,0,0)
    , Position(0,1,0), Position(0,-1,0)
    , Position(0,0,1),Position(0,0,-1))

data class Cube(
    val p:Position,
    val adjacentCubes:Set<Position> = listOfAdjacentPositions.map{it + p}.toSet())

    fun List<String>.toCubes() = map{ Cube(it.toDimension(0),it.toDimension(1),it.toDimension(2))}.toSet()
fun String.toDimension(n:Int) = split(",")[n].toInt()

fun Set<Cube>.unconnectedSides(cube:Cube) = cube.adjacentCubes.count{adjacent -> adjacent !in positionOfOtherCubes(cube)}

private fun Set<Cube>.positionOfOtherCubes(cube: Cube): List<Position> = filter { it != cube }.map { it.p }

fun partOne(data:List<String>):Int {
    val cubes = data.toCubes()
    return cubes.sumOf{cube -> cubes.unconnectedSides(cube)}
}

//part two
fun Set<Cube>.xRange() = (minOf{it.p.x}-1)..(maxOf{it.p.x} + 1)
fun Set<Cube>.yRange() = (minOf{it.p.y}-1)..(maxOf{it.p.y} + 1)
fun Set<Cube>.zRange() = (minOf{it.p.z}-1)..(maxOf{it.p.z} + 1)

fun rangeToCheck(xRange:IntRange, yRange:IntRange, zRange:IntRange) = { p:Position -> p.isWithinRange(xRange,yRange,zRange) }

fun WaterMap.fillUp(cubes:Set<Position>, rangeChecker:(Position)->Boolean) {
    add(Position(0,0,0))
    while(addWater(cubes, rangeChecker)) { continue }
}

fun WaterMap.addWater(cubes:Set<Position>, rangeChecker:(Position)->Boolean ):Boolean {
    val waterToAdd = firstOrNull{ p -> listOfAdjacentPositions.map{it + p}.any{ waterCanMoveTo(cubes, it, rangeChecker) }}
    return waterToAdd?.let{
        val positionsToAdd = listOfAdjacentPositions.map{it + waterToAdd}.filter{ waterCanMoveTo(cubes, it, rangeChecker) }
        this.addAll(positionsToAdd)
        true } ?: false
}

fun WaterMap.waterCanMoveTo(cubes:Set<Position>,position:Position, rangeChecker:(Position)->Boolean) =
       !contains(position) && !cubes.contains(position) && rangeChecker(position)

fun WaterMap.cubesSidesNextToWater(cubes:Set<Cube>) = cubes.sumOf{cube ->  sidesNetToWater(cube)}

fun WaterMap.sidesNetToWater(cube:Cube) = cube.adjacentCubes.count { it in this }

fun partTwo(data:List<String>):Int {
    val cubes = data.toCubes()
    val waterMap = mutableSetOf<Position>()
    waterMap.fillUp(cubes.map{it.p}.toSet(), rangeToCheck( cubes.xRange(),cubes.yRange(), cubes.zRange()))
    return  waterMap.cubesSidesNextToWater(cubes)
}


