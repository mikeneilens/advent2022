import kotlin.math.*

enum class Direction (val offset:Position, val isDirectionFor:(Position, Position)->Boolean) {
    Touching(offset = Position(0,0), isDirectionFor = { t, h -> (abs(t.x - h.x)) <= 1 && (abs(t.y - h.y)) <= 1 }),
    Right(offset = Position(1,0), isDirectionFor = { t, h -> (t.y == h.y && t.x < h.x)}),
    Left(offset = Position(-1,0), isDirectionFor = { t, h -> (t.y == h.y && t.x > h.x)}),
    Up(offset = Position(0,-1), isDirectionFor = { t, h -> (t.x == h.x && t.y > h.y)}),
    Down(offset = Position(0,1),isDirectionFor = { t, h -> (t.x == h.x && t.y < h.y)}),
    UpRight(offset = Position(1,-1), isDirectionFor = { t, h -> (t.y > h.y && t.x < h.x)}),
    UpLeft(offset = Position(-1,-1), isDirectionFor = { t, h -> (t.y > h.y && t.x > h.x)}),
    DownRight(offset = Position(1,1), isDirectionFor = { t, h -> (t.y < h.y && t.x < h.x)}),
    DownLeft(offset = Position(-1,1), isDirectionFor = { t, h -> (t.y < h.y && t.x > h.x)}),
}

data class Instruction(val direction:Direction, val qty:Int)

fun List<String>.parseToInstructions() = map(String::toInstruction)

fun String.toInstruction():Instruction = Instruction(toDirection(), toQty())

fun String.toDirection() = Direction.values().first{it.name.startsWith(split(" ")[0])}
fun String.toQty() = split(" ")[1].toInt()

data class Position(val x:Int, val y:Int) {
    infix operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

data class Knot(var position:Position, val nextKnot:Knot? = null) {
    fun attachKnots(qty: Int):Knot = if (qty == 0) this else Knot(Position(0, 0), this).attachKnots(qty - 1)

    fun move(direction:Direction, audit:MutableSet<Position>) {
        position += direction.offset
        nextKnot?.let { nextKnot ->
            val nextDirection = Direction.values().first { direction -> direction.isDirectionFor(nextKnot.position, position)}
            nextKnot.move(nextDirection, audit)
        } ?: audit.add(position)
    }
}

fun List<Instruction>.process(knot:Knot, audit:MutableSet<Position>) =
    forEach {(direction, qty) -> repeat(qty){ knot.move(direction, audit) }}

fun partOne(data:List<String>, knot:Knot = Knot(Position(0,0)).attachKnots(1) ):Set<Position> {
    val instructions = data.parseToInstructions()
    val audit = mutableSetOf<Position>()
    instructions.process(knot,audit)
    return audit
}

fun partTwo(data:List<String>):Set<Position> {
    val knot = Knot(Position(0, 0)).attachKnots(qty = 9)
    return partOne(data, knot)
}
