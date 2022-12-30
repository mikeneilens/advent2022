data class ElfPosition(val x:Int, val y:Int) {
    fun proposedMove(elfPositions:ElfPositions, round:Int):ElfPosition {
        if (noneAnyWhere(elfPositions)) return this
        val moveIndex = (0..3).firstOrNull { moveSelectors[it.indexFor(round)].positionsAreClear(this, elfPositions) }
        return moveIndex?.let {moveSelectors[it.indexFor(round)].move(this)} ?: this
    }
}

fun Int.indexFor(round:Int) = plus(round) % 4

typealias ElfPositions = Set<ElfPosition>

fun List<String>.toElfPositions() = flatMapIndexed{y, line ->
        line.mapIndexedNotNull{x, char ->
            if (char == '#') ElfPosition(x,y) else null
        }
    }.toSet()

fun ElfPosition.noneNorth(others:ElfPositions) = (-1..1).none{ElfPosition(x + it, y - 1) in others}
fun ElfPosition.noneSouth(others:ElfPositions) = (-1..1).none{ElfPosition(x + it, y + 1) in others}
fun ElfPosition.noneWest(others:ElfPositions) = (-1..1).none{ElfPosition(x - 1, y + it) in others}
fun ElfPosition.noneEast(others:ElfPositions) = (-1..1).none{ElfPosition(x + 1, y + it) in others}
fun ElfPosition.noneAnyWhere(others:ElfPositions) = noneNorth(others) && noneSouth(others) && noneWest(others) && noneEast(others)

data class MoveSelector (val positionsAreClear:ElfPosition.(ElfPositions)-> Boolean, val move:(ElfPosition)->ElfPosition)

val moveSelectors = listOf(
    MoveSelector(ElfPosition::noneNorth) { p -> ElfPosition(p.x, p.y - 1) },
    MoveSelector(ElfPosition::noneSouth) { p -> ElfPosition(p.x, p.y + 1) },
    MoveSelector(ElfPosition::noneWest) { p -> ElfPosition(p.x - 1, p.y) },
    MoveSelector(ElfPosition::noneEast) { p -> ElfPosition(p.x + 1, p.y) }
)

data class ProposedMove(val elfPosition: ElfPosition, val newElfPosition: ElfPosition)

fun ElfPositions.findProposedMoves(round:Int, moveRegister:MutableMap<ElfPosition, Int>) =
    map{ elfPosition ->
        val proposedElfPosition = elfPosition.proposedMove(this, round)
        moveRegister[proposedElfPosition] = moveRegister.getOrDefault(proposedElfPosition,0) + 1
        ProposedMove(elfPosition, proposedElfPosition)
    }

fun ElfPositions.newElfPositions(round:Int):ElfPositions {
    val moveRegister = mutableMapOf<ElfPosition, Int>()
    val proposedMoves = findProposedMoves(round, moveRegister)
    return proposedMoves.map{ proposedMove ->
        if (moveRegister[proposedMove.newElfPosition] == 1) proposedMove.newElfPosition else proposedMove.elfPosition
    }.toSet()
}

fun ElfPositions.moveElves(rounds:Int):ElfPositions =
    (0 until rounds).fold(this) { elfPosition, round -> elfPosition.newElfPositions(round) }

fun partOne(data:List<String>):Int {
    val elfPositions = data.toElfPositions()
    val newElfPositions = elfPositions.moveElves(10)
    return newElfPositions.xSize() * newElfPositions.ySize() - newElfPositions.size
}

fun ElfPositions.xSize() = maxOf { it.x } - minOf { it.x } + 1
fun ElfPositions.ySize() = maxOf { it.y } - minOf { it.y } + 1

//part two
tailrec fun ElfPositions.moveElvesUntilNoMovement(round:Int = 0):Int {
    val newElfPositions = newElfPositions(round )
    return if (newElfPositions == this) round + 1 else newElfPositions.moveElvesUntilNoMovement(round + 1)
}

fun partTwo(data:List<String>):Int {
    val elfPositions = data.toElfPositions()
    return elfPositions.moveElvesUntilNoMovement()
}

