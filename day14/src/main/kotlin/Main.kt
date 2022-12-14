data class Position(val x:Int, val y:Int)

fun String.toPosition() = Position(split(",").first().toInt(),split(",").last().toInt())


fun List<String>.populate(map:MutableMap<Position, Char>) = forEach {it.populate(map)}

fun String.populate(map:MutableMap<Position, Char>) {
    if (split(" -> ").size < 2) return
    val (p1, p2) = split(" -> ").take(2).map(String::toPosition)

    (minOf(p1.y,p2.y)..maxOf(p1.y,p2.y)).forEach{ y ->
        (minOf(p1.x,p2.x)..maxOf(p1.x,p2.x)) .forEach { x ->
            map[Position(x,y)] = '#'
        }
    }
    drop(indexOfFirst { it == '>' } + 2).populate(map)
}

fun MutableMap<Position, Char>.dropSand(sand:Position, maxY:Int):Position? {
    if (this[sand] != null) return null
    var x = sand.x
    var y = sand.y
    while (y < maxY  && ( !containsKey(Position(x, y + 1)) || !containsKey(Position(x -1, y + 1)) || !containsKey(Position(x + 1, y + 1))  )) {
        if (!containsKey(Position(x , y + 1))) y++
        else if (!containsKey(Position(x - 1 , y + 1))) { x--; y++}
        else if (!containsKey(Position(x  + 1, y + 1))) { x++; y++}
    }
    return if (y >= maxY) null else {
        this[Position(x, y)] = 'O'
        Position(x, y)
    }
}

fun MutableMap<Position, Char>.process(maxY:Int):Map<Position,Char> {
    var sand = dropSand(Position(500,0), maxY)
    while (sand != null) {
        val result  = dropSand(Position(500,0), maxY)
        sand = result
    }
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
    ((-maxX * 2)..(+maxX * 2)).forEach{ x -> map[Position(x,maxY + 2)] = '#' }
    map.process(maxY + 2)
    return map.toList().count{it.second == 'O'}
}