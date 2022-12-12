data class Position(val x:Int, val y:Int) {
    fun height(heightMap:List<String>):Int = when(val char = heightMap[y][x]) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> char.code
    }

    operator fun plus(other:Position) = Position(x + other.x, y + other.y)

    fun surroundingPositions(heightMap: List<String>) = listOf(Position(1,0), Position(0,1), Position(-1,0), Position(0,-1))
        .map{it + this}.filter{(it.y in 0 until heightMap.size) && (it.x in 0 until  heightMap[y].length)}

    fun routes(heightMap:List<String>) =
        surroundingPositions(heightMap).filter{ ( it.height(heightMap) - this.height(heightMap)) < 2}

    fun routes2(heightMap:List<String>) =
        surroundingPositions(heightMap).filter{  ( this.height(heightMap) - it.height(heightMap)) < 2}

    fun atGoal(heightMap: List<String>, goal:Char) = heightMap[y][x] == goal

    fun findJourneySize(heightMap: List<String>, goal: Char, size: Int, journeyStatus: JourneyStatus) {
        if ((journeyStatus.minSizeForPosition[this] ?: Int.MAX_VALUE) <= size || ( journeyStatus.minSize <= size)) return

        journeyStatus.minSizeForPosition[this] = size

        if (atGoal(heightMap, goal)) {
            journeyStatus.minSize = size
        } else {
            val routes = if (goal == 'E') routes(heightMap) else routes2(heightMap)
            if (routes.isNotEmpty()) routes.forEach{ route ->  route.findJourneySize(heightMap, goal, size + 1 , journeyStatus) }
        }
    }
}

class JourneyStatus(val minSizeForPosition:MutableMap<Position, Int> = mutableMapOf() , var minSize:Int = Int.MAX_VALUE  )

fun partOne(data:List<String>, start:Char = 'S', end:Char = 'E'):Int {
    val y = data.indexOfFirst { it.contains(start) }
    val x = data[y].indexOfFirst { it == start }
    val journeyStatus = JourneyStatus()
    Position(x,y).findJourneySize(data, end, 0, journeyStatus)
    return journeyStatus.minSize
}

fun partTwo(data:List<String>):Int {
    return partOne(data, 'E', 'a')
}