enum class Direction (val offset:Position, val isDirectionWhen:(Position, Position)->Boolean) {
    None(offset = Position(0,0), isDirectionWhen = { t, h -> t == h}),
    Right(offset = Position(1,0), isDirectionWhen = { t, h -> (t.y == h.y && t.x < h.x)}),
    Left(offset = Position(-1,0), isDirectionWhen = { t, h -> (t.y == h.y && t.x > h.x)}),
    Up(offset = Position(0,-1), isDirectionWhen = { t, h -> (t.x == h.x && t.y > h.y)}),
    Down(offset = Position(0,1),isDirectionWhen = { t, h -> (t.x == h.x && t.y < h.y)}),
    UpRight(offset = Position(1,-1), isDirectionWhen = { t, h -> (t.y > h.y && t.x < h.x)}),
    UpLeft(offset = Position(-1,-1), isDirectionWhen = { t, h -> (t.y > h.y && t.x > h.x)}),
    DownRight(offset = Position(1,1), isDirectionWhen = { t, h -> (t.y < h.y && t.x < h.x)}),
    DownLeft(offset = Position(-1,1), isDirectionWhen = { t, h -> (t.y < h.y && t.x > h.x)}),
}

data class Instruction(val direction:Direction, val qty:Int)

fun String.toInstruction():Instruction {
    val direction = Direction.values().first{it.name.startsWith(split(" ")[0])}
    val qty = split(" ")[1].toInt()
    return Instruction(direction,qty)
}

fun List<String>.parse() = map(String::toInstruction)

data class Position(val x:Int, val y:Int) {
    infix fun isTouching(other:Position):Boolean = (((x - other.x).abs) <= 1 && ((y - other.y).abs) <= 1 )

    infix operator fun plus(other:Position) = Position(x + other.x, y + other.y)

    private fun directionOfHead(head: Position) = Direction.values().first { it.isDirectionWhen(this, head)}

    fun moveTowards(head:Position) = if (isTouching(head)) this else this + directionOfHead(head).offset

    fun moveHead(instruction: Instruction, tails:List<Position>, auditTrail:MutableSet<Position>):Pair<Position, List<Position>> {
        if (instruction.qty == 0) return Pair(this, tails)
        val movedHead = this + instruction.direction.offset
        val newTails = tails.moveTowards(movedHead)
        auditTrail.add(newTails.last())
        return movedHead.moveHead(Instruction(instruction.direction, instruction.qty - 1), newTails, auditTrail )
    }
}

fun List<Position>.moveTowards(head:Position) =
    mapIndexed { index, tail -> if (index == 0) tail.moveTowards(head) else tail.moveTowards(get(index - 1))}


fun List<Instruction>.processAll(start:Position, tails:List<Position>, auditTrail:MutableSet<Position>):Pair<Position,List<Position>> =
    fold(Pair(start, tails)){result, instruction ->
        val (head, tail) = result
        head.moveHead(instruction, tail, auditTrail)
    }


fun partOne(data: List<String>, tailSize:Int = 1):Set<Position> {
    val head = Position(0,4)
    val tails = (1..tailSize).map{head}
    val instructions = data.parse()
    val audit = mutableSetOf<Position>()
    val (_, _) = instructions.processAll(head, tails,audit)
    return audit
}

fun partTwo(data: List<String>):Set<Position> = partOne(data, tailSize = 9)

val Int.abs get() = if (this < 0) -this else this