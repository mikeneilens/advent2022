data class Position(val x:Int, val y:Int)

fun yRange(p1:Position, p2:Position ) = minOf(p1.y,p2.y)..maxOf(p1.y,p2.y)
fun xRange(p1:Position, p2:Position ) = minOf(p1.x,p2.x)..maxOf(p1.x,p2.x)

fun String.toPosition() = Position(split(",").first().toInt(),split(",").last().toInt())

fun List<String>.populate(map:MutableMap<Position, Char>) = forEach {it.populate(map)}

fun String.populate(map:MutableMap<Position, Char>) {
    if (split(" -> ").size < 2) return
    val (p1, p2) = split(" -> ").take(2).map(String::toPosition)
    yRange(p1,p2).forEach{ y ->  xRange(p1,p2) .forEach { x ->  map.placeRock(Position(x,y)) } }
    drop(indexOfFirst { it == '>' } + 2).populate(map)
}

enum class PossibleMove(val canMove:(Map<Position,Char>, Int, Int)->Boolean, val newX:(Int)->Int ) {
    Down (canMove={ map, x, y -> !map.containsKey(Position(x, y + 1)) }, newX = { x -> x } ) ,
    DownLeft (canMove={ map, x, y -> !map.containsKey(Position(x - 1, y + 1)) }, newX = { x -> x - 1 } ) ,
    DownRight (canMove={ map, x, y -> !map.containsKey(Position(x + 1, y + 1)) }, newX = { x -> x + 1 } )
}

fun MutableMap<Position, Char>.dropSand(sand:Position, maxY:Int):Position? {
    if (isNotEmpty(sand)) return null
    var (x, y) = sand
    while (y < maxY  && ( PossibleMove.values().any{ it.canMove(this,x,y) }  )) {
        x = PossibleMove.values().first{ it.canMove(this, x,y) }.newX(x)
        y++
    }
    return if (y < maxY) placeSand(Position(x, y)) else null
}

fun MutableMap<Position, Char>.placeRock(p:Position) {this[p] = '#'}
fun MutableMap<Position, Char>.placeSand(p:Position):Position {this[p] = 'O'; return p}
fun MutableMap<Position, Char>.isNotEmpty(p:Position) = this[p] != null

fun MutableMap<Position, Char>.process(maxY:Int):Map<Position,Char> {
    while (dropSand(Position(500,0), maxY) != null) { }
    return this
}

fun partOne2(data:List<String>):Int {
    val map = mutableMapOf<Position, Char>()
    data.populate(map)
    val maxY = map.toList().maxOf { it.first.y }
    map.process(maxY)
    return map.toList().count{it.second == 'O'}
}

fun partTwo2(data:List<String>):Int {
    val map = mutableMapOf<Position, Char>()
    data.populate(map)
    val maxY = map.toList().maxOf { it.first.y }
    val maxX = map.toList().maxOf { it.first.x }
    ((-maxX * 2)..(+maxX * 2)).forEach{ x -> map.placeRock(Position(x,maxY + 2)) }
    map.process(maxY + 2)
    return map.toList().count{it.second == 'O'}
}