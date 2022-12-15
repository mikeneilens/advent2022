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

fun MutableMap<Position, Char>.dropSand(sand:Position, maxY:Int):Position? {
    if (isNotEmpty(sand)) return null
    var (x, y) = sand
    while (y < maxY  && ( spaceBelowIsEmpty(x, y) || spaceBelowLeftIsEmpty(x, y)||spaceBelowLRightIsEmpty(x, y)  )) {
        if (spaceBelowIsEmpty(x, y)) y++
        else if (spaceBelowLeftIsEmpty(x, y)) { x--; y++}
        else if (spaceBelowLRightIsEmpty(x, y)) { x++; y++}
    }
    return if (y < maxY)  {
        placeSand(Position(x, y))
        Position(x, y)
    } else null
}

fun MutableMap<Position, Char>.placeRock(p:Position) {this[p] = '#'}
fun MutableMap<Position, Char>.placeSand(p:Position) {this[p] = 'O'}
fun MutableMap<Position, Char>.isNotEmpty(p:Position) = this[p] != null
fun MutableMap<Position, Char>.spaceBelowIsEmpty(x:Int, y:Int) = !containsKey(Position(x, y + 1))
fun MutableMap<Position, Char>.spaceBelowLeftIsEmpty(x:Int, y:Int) = !containsKey(Position(x - 1, y + 1))
fun MutableMap<Position, Char>.spaceBelowLRightIsEmpty(x:Int, y:Int) = !containsKey(Position(x + 1, y + 1))

fun MutableMap<Position, Char>.process(maxY:Int):Map<Position,Char> {
    while (dropSand(Position(500,0), maxY) != null) { }
    return this
}
/*
fun MutableMap<Position, Char>.toText():String{
    var s=""
    (0..10).forEach{y->
        (480..520).forEach{x ->
            if (this[Position(x,y)] != null) s += this[Position(x,y)]
            else s += "."
        }
        s += "\n"
    }
    return s
}
*/
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