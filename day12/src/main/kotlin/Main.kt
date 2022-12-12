data class Position(val x:Int, val y:Int) {
    fun height(heightMap:List<String>):Int = when(val char = heightMap[y][x]) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> char.code
    }

    operator fun plus(other:Position) = Position(x + other.x, y + other.y)

    fun surroundingPositions(heightMap: List<String>) = listOf(Position(1,0), Position(0,1), Position(-1,0), Position(0,-1))
        .map{it + this}.filter{(it.y in 0 until heightMap.size) && (it.x in 0 until  heightMap[y].length)}
}

fun Position.atGoal(heightMap: List<String>, goal:Char) = heightMap[y][x] == goal

fun Position.routes(heightMap:List<String>) =
    surroundingPositions(heightMap).filter{ ( it.height(heightMap) - this.height(heightMap)) < 2}

fun Position.routes2(heightMap:List<String>) =
    surroundingPositions(heightMap).filter{  ( this.height(heightMap) - it.height(heightMap)) < 2}

fun findJourneySize(heightMap: List<String>, goal:Char, position:Position, size:Int, journeyStatus: JourneyStatus) {
    if ((journeyStatus.minSizeForPosition[position] ?: Int.MAX_VALUE) <= size || ( journeyStatus.minSize <= size)) return

    journeyStatus.minSizeForPosition[position] = size

    if (position.atGoal(heightMap,goal)) {
        journeyStatus.minSize = size
    } else {
        val routes = if (goal == 'E') position.routes(heightMap) else position.routes2(heightMap)
        if (routes.isNotEmpty()) routes.forEach{ route -> findJourneySize(heightMap, goal,route, size + 1 , journeyStatus) }
    }
}

class JourneyStatus(val minSizeForPosition:MutableMap<Position, Int> = mutableMapOf() , var minSize:Int = Int.MAX_VALUE  )

fun partOne(data:List<String>, start:Char = 'S', end:Char = 'E'):Int {
    val y = data.indexOfFirst { it.contains(start) }
    val x = data[y].indexOfFirst { it == start }
    val journeyStatus = JourneyStatus()
    findJourneySize(data, end, Position(x,y),0, journeyStatus)
    return journeyStatus.minSize
}

fun partTwo(data:List<String>):Int {
    return partOne(data, 'E', 'a')
}