import kotlin.math.abs

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

infix fun IntRange.squashWith(range2:IntRange):List<IntRange> =
    if (range2.start > last + 1) listOf(this, range2) else listOf(start..maxOf(last, range2.last))

fun List<IntRange>.squash():List<IntRange> {
    val sorted = sortedWith(compareBy ({it.first},{it.last}))
    return sorted.drop(1)
        .fold(listOf(sorted.first())){ list, range ->
            val squashedRanges = list.last() squashWith range
            if (squashedRanges.size == 1) list.dropLast(1) + squashedRanges else list + listOf(squashedRanges[1])
        }
}

fun partOne(data:List<String>,row:Int = 10):Int {
    val sensors = data.toSensors()
    val scannedPositionsOnRow = sensors.sensorsInRange(row)
    val ranges = scannedPositionsOnRow.map{it.xRange(row) ?: 0..-1}.squash()
    val  positionOfBeaconsOnRow = sensors.positionsOfBeaconsOnRow(row)
    return (ranges.sumOf { it.count() } - positionOfBeaconsOnRow.size)
}

fun partTwo(data:List<String>, size:Int = 20):Long {
    val sensors = data.toSensors()
    (0..size).forEach { row ->
        val scannedPositionsOnRow = sensors.sensorsInRange(row)
        val ranges = scannedPositionsOnRow.map{it.xRange(row) ?: 0..-1}.squash()
        if (ranges.size > 1) {
            return row + (ranges[0].last + 1) * 4000000L
        }
    }
    return 0
}