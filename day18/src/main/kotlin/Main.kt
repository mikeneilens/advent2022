fun helloWorld() = "hello world"

data class Position( val x:Int, val y:Int, val z:Int) {
    operator fun plus(other:Position):Position = Position(x + other.x, y + other.y, z + other.z)
}
fun Cube(x:Int, y:Int, z:Int) = Cube(Position(x,y,z))

data class Cube(
    val p:Position,
    val adjacentCubes:Set<Position> = setOf(
        p + Position(1,0,0),p + Position(-1,0,0),
        p + Position(0,1,0),p + Position(0,-1,0),
        p + Position(0,0,1),p + Position(0,0,-1),
    ))

fun List<String>.toCubes() = map{ Cube(it.toDimension(0),it.toDimension(1),it.toDimension(2))}
fun String.toDimension(n:Int) = split(",")[n].toInt()

fun List<Cube>.unconnectedSides(cube:Cube) = cube.adjacentCubes.count{adjacent -> adjacent!in positionOfOtherCubes(cube)}

private fun List<Cube>.positionOfOtherCubes(cube: Cube): List<Position> = filter { it != cube }.map { it.p }

fun partOne(data:List<String>):Int {
    val cubes = data.toCubes()
    return cubes.sumOf{cube -> cubes.unconnectedSides(cube)}
}