import java.util.PriorityQueue
import kotlin.math.abs

fun partOne(input: List<String>): Int {
    val valley = parse(input)
    val initialState = State(valley.start, 0)
    val final = aStar(initialState, valley, valley.goal)
    return final.time
}

fun partTwo(input: List<String>): Int {
    val valley = parse(input)
    val atTheStart = State(valley.start, 0)
    val atTheEnd = aStar(atTheStart, valley, valley.goal)
    val atTheStartAgain = aStar(atTheEnd, valley, valley.start)
    val atTheEndAgain = aStar(atTheStartAgain, valley, valley.goal)
    return atTheEndAgain.time
}

data class Position(val x: Int, val y: Int)  {
    val manhattanDistance = abs(x) + abs(y)

    operator fun plus(other: Position) = Position(x + other.x,y + other.y)

    operator fun minus(other: Position) = Position(x - other.x, y - other.y)

    companion object {
        private val up = Position(0,-1)
        private val down = Position(0,1)
        private val left = Position(-1,0)
        private val right = Position(1,0)
        private val none = Position(0,0)

        val allDirections = listOf(up, down, left, right, none)
    }
}

fun aStar(initialState: State, valley: Valley, goal: Position): State {
    val cameFrom = mutableMapOf<State, State>()
    val costSoFar = mutableMapOf(initialState to initialState.time)
    fun estimatedCostToGoal(state: State) = (valley.goal - state.position).manhattanDistance
    val frontier = PriorityQueue(compareBy<State> { costSoFar.getValue(it) + estimatedCostToGoal(it) })
    frontier.add(initialState)

    while (frontier.isNotEmpty()) {
        val current = frontier.poll()

        if (current.position == goal) return current

        valley.nextStates(current).forEach { next ->
            val newCost = costSoFar.getValue(current) + 1
            if (newCost < costSoFar.getOrDefault(next, Int.MAX_VALUE)) {
                costSoFar[next] = newCost
                frontier.add(next)
                cameFrom[next] = current
            }
        }
    }

    throw Exception("Frontier was empty before goal was reached")
}

data class State(val position: Position, val time: Int)

interface Flake {
    val x:Int
    val y:Int
    fun after(time:Int):Position
}
data class UpFlake(override val x:Int, override val y:Int, val size:Int):Flake {
    override fun after(time: Int) = Position(x, ((y - time.mod(size)) - 1).mod(size) + 1)
}
data class DownFlake(override val x:Int, override val y:Int, val size:Int):Flake {
    override fun after(time: Int) = Position(x, ((y + time.mod(size)) - 1).mod(size) + 1)
}
data class LeftFlake(override val x:Int, override val y:Int, val size:Int):Flake {
    override fun after(time: Int) = Position( ((x - time.mod(size)) - 1).mod(size) + 1, y)
}
data class RightFlake(override val x:Int, override val y:Int, val size:Int):Flake {
    override fun after(time: Int) = Position( ((x + time.mod(size)) - 1).mod(size) + 1, y)
}

class Valley(
    val flakesOnRow: List<List<Flake>>,
    val flakesOnCol: List<List<Flake>>,
    private val xBounds:IntRange,
    private val yBounds:IntRange
) {
    val start = Position(xBounds.first, yBounds.first - 1)
    val goal = Position(xBounds.last, yBounds.last + 1)

    fun nextStates(state: State): List<State> {
        return Position.allDirections.map {direction ->  state.position + direction }
            .filter { position ->
                (position.isWithinBounds() || position == start || position == goal) && !positionContainsSnow(position, state.time + 1 )
            }
            .map { State(it, state.time + 1) }
    }

    private fun positionContainsSnow(position:Position, time:Int) =
        flakesFromRowOccupyPosition(position, time) || flakesFromColOccupyPosition(position, time)

    private fun flakesFromRowOccupyPosition(position:Position, time:Int) =
        position.y < flakesOnRow.size && flakesOnRow[position.y].any{ it.after(time) == position}

    private fun flakesFromColOccupyPosition(position:Position, time:Int) =
        position.x < flakesOnCol.size && flakesOnCol[position.x].any{ it.after(time) == position}

    private fun Position.isWithinBounds() = (x in xBounds && y in yBounds)
}

fun parse(input: List<String>): Valley {
    val flakes = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, char ->
            when (char) {
                '<' -> LeftFlake(x, y, line.length - 2)
                '>' -> RightFlake(x, y, line.length - 2)
                '^' -> UpFlake(x, y, input.size - 2)
                'v' -> DownFlake(x, y, input.size - 2)
                else -> null
            }
        }
    }
    val flakesOnRow =(0..(input.size - 2)).map { y -> flakes.filter { it.y == y } }
    val flakesOnCol = (0..(input.first().length - 2)).map { x -> flakes.filter { it.x == x } }
    return Valley(flakesOnRow, flakesOnCol, 1..input.first().length -2, 1..input.size -2 )
}
