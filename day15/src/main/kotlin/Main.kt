import kotlin.math.abs

fun helloWorld() = "hello world"

data class Position(val x:Int, val y:Int)

data class Sensor(val position:Position, val beacon:Position) {
    val manhattenSize = abs(position.x - beacon.x) + abs(position.y - beacon.y)
    fun xRange(y:Int):IntRange? {
        val span = manhattenSize - abs(y - position.y)
        return if (span >= 0) (position.x - span)..(position.x + span) else null
    }
    fun yRange(x:Int):IntRange? {
        val span = manhattenSize - abs(x - position.x)
        return if (span >= 0) (position.y - span)..(position.y + span) else null
    }
}

fun List<String>.toSensors() = map(String::toSensor)

fun String.toSensor():Sensor {
    val chunks = split("=")
    val sensorX = chunks[1].removeSuffix(", y").toInt()
    val sensorY = chunks[2].removeSuffix(": closest beacon is at x").toInt()
    val beaconX = chunks[3].removeSuffix(", y").toInt()
    val beaconY = chunks[4].toInt()
    return Sensor(Position(sensorX,sensorY), Position(beaconX,beaconY))
}

fun List<Sensor>.sensorsInRange(y:Int) = filter {val yRange = it.yRange(it.position.x);  yRange != null && y in yRange}
fun List<Sensor>.positionsOfBeaconsOnRow(y:Int) = filter {it.beacon.y == y}.map{it.beacon.x}.toSet()

fun consolidateRanges(range1:IntRange, range2:IntRange):List<IntRange> =
    if (range2.start > range1.last) listOf(range1, range2)
    else listOf(range1.start..maxOf(range1.last, range2.last))

fun List<IntRange>.consolidateRanges():List<IntRange> {
    val sorted = sortedWith(compareBy ({it.first},{it.last}))
    return sorted.drop(1)
        .fold(listOf(sorted.first())){ list, range ->
            val consolidated:List<IntRange> = consolidateRanges(list.first(), range)
            println("$range ${list.first()} $consolidated")
            if (consolidated.size == 1) list.dropLast(1) + consolidated else list + listOf(consolidated[1])
        }
}

fun partOne(data:List<String>,row:Int = 10):Int {
    val sensors = data.toSensors()
    val scannedPositionsOnRow = sensors.sensorsInRange(row)
    val consolidated = scannedPositionsOnRow.map{it.xRange(row) ?: 0..-1}.consolidateRanges()
    println(consolidated)
    println(consolidated.sumOf { (it.last - it.first + 1) })
    val  positionOfBeaconsOnRow = sensors.positionsOfBeaconsOnRow(row)

    return (consolidated.sumOf { it.count() } - positionOfBeaconsOnRow.size)
}