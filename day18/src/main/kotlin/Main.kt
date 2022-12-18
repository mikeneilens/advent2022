fun helloWorld() = "hello world"

data class Position( val x:Int, val y:Int, val z:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y, z + other.z)
    fun withinRange(xRange:IntRange, yRange:IntRange, zRange:IntRange) = x in xRange && y in yRange && z in zRange

}
fun Cube(x:Int, y:Int, z:Int) = Cube(Position(x,y,z))

val lisOfAdjacencies = listOf(Position(1,0,0), Position(-1,0,0)
    , Position(0,1,0), Position(0,-1,0)
    , Position(0,0,1),Position(0,0,-1))

data class Cube(
    val p:Position,
    val adjacentCubes:Set<Position> = lisOfAdjacencies.map{it + p}.toSet())

    fun List<String>.toCubes() = map{ Cube(it.toDimension(0),it.toDimension(1),it.toDimension(2))}.toSet()
fun String.toDimension(n:Int) = split(",")[n].toInt()

fun Set<Cube>.unconnectedSides(cube:Cube) = cube.adjacentCubes.count{adjacent -> adjacent!in positionOfOtherCubes(cube)}

private fun Set<Cube>.positionOfOtherCubes(cube: Cube): List<Position> = filter { it != cube }.map { it.p }

fun partOne(data:List<String>):Int {
    val cubes = data.toCubes()
    return cubes.sumOf{cube -> cubes.unconnectedSides(cube)}
}

//part two
fun Set<Cube>.xRange() = -1..(maxOf{it.p.x} + 1)
fun Set<Cube>.yRange() = -1..(maxOf{it.p.y} + 1)
fun Set<Cube>.zRange() = -1..(maxOf{it.p.z} + 1)

fun MutableSet<Position>.fillUp(cubes:Set<Position>, xRange:IntRange, yRange:IntRange, zRange:IntRange) {
    add(Position(0,0,0))
    while(addWater(cubes, xRange ,yRange, zRange)) { }
}

fun MutableSet<Position>.addWater(cubes:Set<Position>, xRange:IntRange, yRange:IntRange, zRange:IntRange ):Boolean {
    val waterToAdd = this.firstOrNull{ p -> lisOfAdjacencies.map{it + p}.any{ waterCanMoveTo(cubes, it, xRange,yRange,zRange) }}
    if (waterToAdd == null) return false
    else {
        val positionsToadd = lisOfAdjacencies.map{it + waterToAdd}.filter{ waterCanMoveTo(cubes, it, xRange,yRange,zRange) }
        this.addAll(positionsToadd)
        return true
    }
}
fun MutableSet<Position>.waterCanMoveTo(cubes:Set<Position>,position:Position, xRange:IntRange, yRange:IntRange, zRange:IntRange) =
    position !in cubes && position !in this && position.withinRange(xRange,yRange,zRange)

fun MutableSet<Position>.cubesSidesNextToWater(cubes:Set<Cube>) =
    cubes.sumOf{cubes ->  cubes.adjacentCubes.count { it in this }}

fun partTwo(data:List<String>):Int {
    val cubes = puzzleInput.toCubes()
    val waterMap = mutableSetOf<Position>()
    waterMap.fillUp(cubes.map{it.p}.toSet(),cubes.xRange(),cubes.yRange(), cubes.zRange())
    return  waterMap.cubesSidesNextToWater(cubes)
}


