import javafx.geometry.Pos

fun helloWorld() = "hello world"

enum class Direction (val offset:Position) {
    Right(Position(1,0)),
    Left(Position(-1,0)),
    Up(Position(0,-1)),
    Down(Position(0,1)),
    UpRight(Position(1,-1)),
    UpLeft(Position(-1,-1)),
    DownRight(Position(1,1)),
    DownLeft(Position(-1,1)),
    None(Position(0,0))
}

data class Instruction(val direction:Direction, val qty:Int)

fun String.toInstruction():Instruction {
    val direction = when (split(" ")[0]) {
        "L" -> Direction.Left
        "R" -> Direction.Right
        "U" -> Direction.Up
        "D" -> Direction.Down
        else -> Direction.None
    }
    val qty = split(" ")[1].toInt()
    return Instruction(direction,qty)
}

fun List<String>.parse() = map(String::toInstruction)

data class Position(val x:Int, val y:Int) {
    infix fun isTouching(other:Position):Boolean = (((x - other.x).abs) <= 1 && ((y - other.y).abs) <= 1 )

    infix operator fun plus(other:Position) = Position(x + other.x, y + other.y)

    fun directionOfHead(head: Position):Direction = when(true) {
        (y == head.y && x <  head.x) -> Direction.Right
        (y == head.y && x >  head.x) -> Direction.Left
        (y >  head.y && x == head.x) -> Direction.Up
        (y <  head.y && x == head.x) -> Direction.Down
        (y >  head.y && x <  head.x)  -> Direction.UpRight
        (y >  head.y && x >  head.x)  -> Direction.UpLeft
        (y <  head.y && x <  head.x)  -> Direction.DownRight
        (y <  head.y && x >  head.x)  -> Direction.DownLeft
        else -> Direction.None
    }

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


fun List<Instruction>.processAll(start:Position, tailSize:Int, auditTrail:MutableSet<Position>):Pair<Position,List<Position>> {
    val headAndTail = Pair(start, (1..tailSize).map{start})
    auditTrail.add(start)
    return fold(headAndTail){result, instruction ->
        val (head, tail) = result
        head.moveHead(instruction, tail, auditTrail)
    }
}

fun partOne(data: List<String>):Set<Position> {
    val start = Position(0,4)
    val instructions = data.parse()
    val audit = mutableSetOf(start)
    val (_, _) = instructions.processAll(start,1, audit)
    return audit
}

fun partTwo(data: List<String>):Set<Position> {
    val start = Position(0,4)
    val instructions = data.parse()
    val audit = mutableSetOf(start)
    val (_, _) = instructions.processAll(start,9, audit)
    return audit
}



val Int.abs get() = if (this < 0) -this else this