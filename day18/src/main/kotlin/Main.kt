data class Position( val x:Int, val y:Int, val z:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y, z + other.z)
}

typealias WaterMap = MutableSet<Position>

fun Cube(x:Int, y:Int, z:Int) = Cube(Position(x,y,z))

val listOfAdjacentPositions = listOf(Position(1,0,0), Position(-1,0,0)
    , Position(0,1,0), Position(0,-1,0)
    , Position(0,0,1),Position(0,0,-1))

data class Cube(val p:Position, val adjacentCubes:Set<Position> = listOfAdjacentPositions.map{it + p}.toSet())

fun List<String>.toCubes() = map{ Cube(it.toDimension(0),it.toDimension(1),it.toDimension(2))}.toSet()
fun String.toDimension(n:Int) = split(",")[n].toInt()

fun Set<Cube>.unconnectedSides(cube:Cube) = cube.adjacentCubes.count{adjacent -> adjacent !in positionOfOtherCubes(cube)}

fun Set<Cube>.positionOfOtherCubes(cube: Cube): List<Position> = filter { it != cube }.map { it.p }

fun partOne(data:List<String>):Int {
    val cubes = data.toCubes()
    return cubes.sumOf{cube -> cubes.unconnectedSides(cube)}
}

//part two
fun Set<Cube>.xRange() = (minOf{it.p.x}-1)..(maxOf{it.p.x} + 1)
fun Set<Cube>.yRange() = (minOf{it.p.y}-1)..(maxOf{it.p.y} + 1)
fun Set<Cube>.zRange() = (minOf{it.p.z}-1)..(maxOf{it.p.z} + 1)

fun rangeToCheck(xRange:IntRange, yRange:IntRange, zRange:IntRange) = { p:Position -> p.x in xRange && p.y in yRange && p.z in zRange }

data class WaterStatus(val waterMap: WaterMap, val position: Position, val cubes: Set<Position>, val rangeChecker: (Position) -> Boolean)

val fillUp = DeepRecursiveFunction<WaterStatus,Unit>{ waterStatus ->
    val (waterMap, position, cubes, rangeChecker) = waterStatus
    waterMap.add(position)
    val waterToAdd =  listOfAdjacentPositions.map{it + position}
        .filter{ adjacentPosition -> waterMap.waterCanMoveTo(cubes, adjacentPosition, rangeChecker) }
    waterToAdd.forEach { adjacentPosition -> callRecursive(waterStatus.copy(position = adjacentPosition)) }
}

fun WaterMap.waterCanMoveTo(cubes:Set<Position>,position:Position, rangeChecker:(Position)->Boolean) =
       !contains(position) && !cubes.contains(position) && rangeChecker(position)

fun WaterMap.cubesSidesNextToWater(cubes:Set<Cube>) = cubes.sumOf{cube ->  sidesNextToWater(cube)}

fun WaterMap.sidesNextToWater(cube:Cube) = cube.adjacentCubes.count { it in this }

fun partTwo(data:List<String>):Int {
    val cubes = data.toCubes()
    val waterMap = mutableSetOf<Position>()
    fillUp(WaterStatus(waterMap, Position(0,0,0), cubes.map{it.p}.toSet(), rangeToCheck(cubes.xRange(),cubes.yRange(), cubes.zRange())))
    return  waterMap.cubesSidesNextToWater(cubes)
}
