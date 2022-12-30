sealed class Instruction {
    data class Move(val distance: Int):Instruction()
    object RotateLeft:Instruction()
    object RotateRight:Instruction()
}

data class Position(val x:Int, val y:Int) {
    operator fun plus(other: Position) = Position(x + other.x, y + other.y)
}

data class Tile(val type:Char, val next:MutableMap<String, Position> = mutableMapOf(), val nextDirection:MutableMap<String, String> = mutableMapOf() )

fun List<String>.parseToInstructions() = last().parseToInstructions()

tailrec fun String.parseToInstructions(instructions: List<Instruction> = listOf()): List<Instruction>  =
    when (true) {
        isEmpty() -> instructions
        (first() == 'L') -> drop(1).parseToInstructions(instructions + Instruction.RotateLeft)
        (first() == 'R') -> drop(1).parseToInstructions(instructions + Instruction.RotateRight)
        else -> {
            val numberString = takeWhile { it != 'R' && it != 'L' }
            val move = Instruction.Move(numberString.toInt() )
            drop(numberString.length ).parseToInstructions(instructions + move)
        }
    }

fun List<String>.createMap():Map<Position, Tile> =
    flatMapIndexed { y, line ->
        line.mapIndexedNotNull{ x, char ->
            if (char == '.' || char == '#') Pair(Position(x +1,y + 1), Tile(char)) else null
        }
    }.toMap()


val rightMap = mapOf("E" to "S", "S" to "W", "W" to "N", "N" to "E" )
val leftMap = mapOf("E" to "N", "N" to "W", "W" to "S", "S" to "E" )
val offsets = mapOf("E" to Position(1,0), "W" to Position(-1,0), "S" to Position(0,1), "N" to Position(0,-1))

data class Person(val tile:Tile, val direction:String, val map:Map<Position, Tile>) {
    fun turnRight() = copy( direction =   rightMap.getValue(direction) )
    fun turnLeft() = copy( direction = leftMap.getValue(direction) )

    fun move(qty:Int):Person {
        if (qty == 0) return this
        val nextTile = map[tile.next[direction]]
        if (nextTile == null || nextTile.type == '#') return this
        val newDirection = tile.nextDirection.getValue(direction)
        return copy(tile = nextTile, direction = newDirection).move(qty - 1)
    }

    fun process(instructions: List<Instruction>) =
         instructions.fold(this) { newPerson, instruction ->
            when (instruction) {
                is Instruction.RotateLeft -> newPerson.turnLeft()
                is Instruction.RotateRight -> newPerson.turnRight()
                is Instruction.Move -> newPerson.move(instruction.distance)
            }
        }
    fun positionOnMap() = map.filter { it.value == tile }.keys.first()
}

fun Map<Position, Tile>.toPerson() = Person(getValue(Position(keys.filter{it.y == 1 }.minOf { it.x },1)),"E", this)

fun partOne(data:List<String>, edgeMap:Map<Line, Line>):Int {
    val instructions = data.parseToInstructions()
    val map = prepareMap(data, edgeMap)
    val movedPerson = map.toPerson().process(instructions)
    val (x,y ) = movedPerson.positionOnMap()
    return y * 1000 + 4 * x + rightMap.keys.indexOfFirst { it == movedPerson.direction }
}

//part two
data class Line(val start:Position, val end:Position, val perpendicularDirection:String = "") {
    val incX = (end.x - start.x) % 2
    val incY = (end.y - start.y) % 2

    fun positions():List<Position> {
        var (x,y) = start
        val result = mutableListOf(start)
        while (x != end.x || y != end.y) {
            x += incX; y += incY
            result.add(Position(x,y))
        }
        return result
    }
}

fun prepareMap(data:List<String>, edgeMap:Map<Line, Line>):Map<Position, Tile> =
    data.createMap().toMap().apply{
        updateNextPositionOfTiles()
        updateEdges(edgeMap)}

fun Map<Position, Tile>.updateNextPositionOfTiles() {
    forEach { (position, tile) ->
        offsets.forEach { (direction, offset) ->
            if ( contains(position + offset)) {
                tile.next[direction] = position + offset
                tile.nextDirection[direction] = direction
            }
        }
    }
}

fun Map<Position, Tile>.updateEdges(edgeMap: Map<Line, Line>) {
    edgeMap.forEach { (fromLine, toLine) -> updateEdge(fromLine, toLine) }
}

fun Map<Position, Tile>.updateEdge(fromLine:Line, toLine:Line) {
    val direction = if (fromLine.start.x == fromLine.end.x) {
        if (getValue(fromLine.start).next.containsKey("E")) "W" else "E"
    } else {
        if (getValue(fromLine.start).next.containsKey("S")) "N" else "S"
    }
    updateNextPositionsOnEdge(fromLine, toLine, direction)
}

fun Map<Position,Tile>.updateNextPositionsOnEdge(fromLine:Line, toLine:Line, direction:String) {
    fromLine.positions().zip(toLine.positions()).forEach { (fromPosition, toPosition) ->
        getValue(fromPosition).next[direction] = toPosition
        getValue(fromPosition).nextDirection[direction] = toLine.perpendicularDirection
    }
}

fun partTwo(data:List<String>):Int = partOne(data, edgeMapP2)


//data describing the edges of the layout.
val a = Position(51,1)
val b = Position(100,1)
val c = Position(101,1)
val d = Position(150,1)
val e = Position(150,50)
val f = Position(101,50)
val g = Position(51,50)
val h = Position(51,51)
val j = Position(100,51)
val k = Position(100,100)
val i = Position(51,100)
val l = Position(1,101)
val m = Position(50,101)
val o = Position(100,101)
val n = Position(1,150)
val q = Position(51,150)
val p = Position(100,150)
val r = Position(1,151)
val s = Position(50,151)
val t = Position(1,200)
val u = Position(50,200)

val edgeMapP2 = mapOf(
    Line(a,b) to Line(r,t,"E"),
    Line(c,d) to Line(t,u,"N"),
    Line(d,e) to Line(p,o,"W"),
    Line(e,f) to Line(k,j,"W"),
    Line(j,k) to Line(f,e, "N"),
    Line(o,p) to Line(e,d,"W"),
    Line(p,q) to Line(u,s,"W"),
    Line(s,u) to Line(q,p, "N"),
    Line(t,u) to Line(c,d,"S"),
    Line(r,t) to Line(a,b,"S"),
    Line(l,n) to Line(g,a,"E"),
    Line(l,m) to Line(h,i,"E"),
    Line(i,h) to Line(m,l,"S"),
    Line(a,g) to Line(n,l,"E"),
)
val edgeMapP1 = mapOf(
    Line(a,b) to Line(q,p,"N"),
    Line(c,d) to Line(f,e,"N"),
    Line(d,e) to Line(a,g,"E"),
    Line(e,f) to Line(d,c,"S"),
    Line(j,k) to Line(h,i, "E"),
    Line(o,p) to Line(l,n,"E"),
    Line(p,q) to Line(b,a,"S"),
    Line(s,u) to Line(r,t, "E"),
    Line(t,u) to Line(l,m,"S"),
    Line(r,t) to Line(s,u,"W"),
    Line(l,n) to Line(o,p,"W"),
    Line(l,m) to Line(t,u,"N"),
    Line(i,h) to Line(k,j,"W"),
    Line(a,g) to Line(d,e,"W"),
)
