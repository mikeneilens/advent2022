data class Position(val x:Int, val y:Int) {
    operator fun plus(other:Position) = Position(x + other.x, y + other.y)
}

typealias Shape = List<Position>
typealias Jet = String
typealias TunnelMap = MutableMap<Position, String>

val hLineShape = listOf(Position(0,0),Position(1,0),Position(2,0),Position(3,0))
val crossShape = listOf(Position(1,0),Position(0,-1),Position(1,-1),Position(2,-1),Position(1,-2))
val lShape = listOf(Position(0,0),Position(1,0),Position(2,0),Position(2,-1),Position(2,-2))
val vLineShape = listOf(Position(0,0),Position(0,-1),Position(0,-2),Position(0,-3))
val squareShape = listOf(Position(0,0),Position(1,0),Position(0,-1),Position(1,-1))

val shapes = listOf(hLineShape,crossShape,lShape,vLineShape,squareShape)
val jetAction = mapOf('>' to {p:Position -> p + Position(1,0) }, '<' to {p:Position -> p + Position(-1,0) })

data class FallingShape(val p:Position, val shape:Shape) {
    fun canBePlacedOnMap(tunnelMap:TunnelMap) = (shape.all{ p.x + it.x in 1..7 }) && (shape.all{ tunnelMap[p + it] == null})
}

fun createMap():TunnelMap {
    val tunnelMap = mutableMapOf<Position, String>()
    (1..7).forEach{tunnelMap[Position(it,0)] = "-"}
    return tunnelMap
}

fun TunnelMap.highestItem() = keys.minOf {it.y}

fun TunnelMap.addShape(fallingShape:FallingShape, symbol:String = "#") {
    fallingShape.shape.forEach { this[fallingShape.p + it] = symbol  }
}

data class Tunnel(val tunnelMap:TunnelMap, val jet:Jet, val fallingShape:FallingShape, val jetNdx:Int = -1, val shapeNdx:Int = 0) {
    fun addShape():Tunnel {
        val nextShapeNdx = (shapeNdx + 1) % shapes.size
        val newFallingShape = FallingShape(Position(3,tunnelMap.highestItem() - 4),shapes[nextShapeNdx])
        return  copy( fallingShape = newFallingShape, shapeNdx = nextShapeNdx )
    }
    fun applyJet():Tunnel {
        val nextJetNdx = (jetNdx + 1) % jet.length
        val newFallingShape = FallingShape(jetAction.getValue(jet[nextJetNdx])(fallingShape.p), fallingShape.shape)
        return if (newFallingShape.canBePlacedOnMap(tunnelMap)) copy(fallingShape = newFallingShape, jetNdx = nextJetNdx )
        else copy(jetNdx = nextJetNdx)
    }
    fun dropShape():Tunnel {
        val newFallingShape = FallingShape(fallingShape.p + Position(0,1), fallingShape.shape)
        return if (newFallingShape.canBePlacedOnMap(tunnelMap))  copy(fallingShape = newFallingShape)
        else this
    }
    fun moveShape():Tunnel {
        val afterJet = applyJet()
        val afterJetAndDrop = afterJet.dropShape()
        if (afterJet.fallingShape == afterJetAndDrop.fallingShape) {
            tunnelMap.addShape(afterJetAndDrop.fallingShape)
            return afterJetAndDrop
        } else return afterJetAndDrop.moveShape()
    }
    fun processShapes(qty:Int):Tunnel = (1..qty).fold(this){tunnel, _ ->  tunnel.moveShape().addShape() }
}

fun setUpGame(jet:Jet) = Tunnel(createMap(), jet, FallingShape(Position(3, -4),shapes.first()))

fun partOne(data:String):Tunnel {
    val tunnel = setUpGame(data)
    return tunnel.processShapes(2022)
}

fun TunnelMap.asString() =
    ((keys.minOf{it.y})..0).map{y -> rowAsString(y) }.joinToString("\n")

fun TunnelMap.rowAsString(y:Int) = (1..7).map{x -> this[Position(x,y)] ?: "." }.joinToString("")

fun TunnelMap.asStrings() = ((keys.minOf{it.y})..0).reversed().map{y -> rowAsString(y) }


fun partTwo(data:String):Pair<Int, Int> {
    val strings = partOne(data).tunnelMap.asStrings()
    strings.take(1000).forEach { println(it) }
    /*
    val primes = 1..2000
    primes.forEach { prime ->
        (1..prime).forEach{
            if (strings.firstChunk(it,prime) == strings.secondChunk(it,prime)) return  Pair(it, prime)
        }
    }

     */
    return Pair(-1,-1)
}

fun List<String>.firstChunk(m:Int, prime:Int) = subList( m,(m + prime))
fun List<String>.secondChunk(m:Int, prime:Int) = subList( m + prime, (m + 2* prime))