import java.lang.Math.abs

enum class Direction (val offset:Position, val isDirectionFor:(Position, Position)->Boolean) {
    None(offset = Position(0,0), isDirectionFor = { t, h -> t == h}),
    Touching(offset = Position(0,0), isDirectionFor = { t, h -> ( abs(t.x - h.x)) <= 1 && (abs(t.y - h.y)) <= 1 }),
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

data class Knot(var pos:Position, var next:Knot? = null) {

    fun move(direction:Direction, audit:MutableSet<Position>) {
        pos = pos + direction.offset
        next?.let { next ->
            val nextDirection = Direction.values().first { direction -> direction.isDirectionFor(next.pos, pos)}
            next.move(nextDirection, audit)
        } ?: audit.add(pos)
    }
}

fun process(knot:Knot, direction:Direction, qty:Int, audit:MutableSet<Position>) {
    if (qty > 0)  {
        knot.move(direction, audit)
        process(knot, direction,qty -1, audit)
    }
}

fun partOne(data:List<String>, knot:Knot = Knot(Position(0,0), Knot(Position(0,0),null)) ):Set<Position> {
    val instructions = data.parseToInstructions()
    val audit = mutableSetOf<Position>()
    instructions.forEach { process(knot, it.direction, it.qty, audit)}
    return audit
}

fun partTwo(data:List<String>):Set<Position> {
    val knots = (0..9).map{Knot(Position(0,0))}
    knots.windowed(2,1).forEach { (a, b) -> a.next = b }
    return partOne(data, knots.first())
}
