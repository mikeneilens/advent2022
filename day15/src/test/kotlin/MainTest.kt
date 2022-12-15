import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {

    val sampleInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().split("\n")

    "Part one" should ({
        "parse Sensor at x=2, y=18: closest beacon is at x=-2, y=15" {
            "Sensor at x=2, y=18: closest beacon is at x=-2, y=15".toSensor() shouldBe Sensor( Position(2,18), Position(-2,15) )
        }
        "parse sensors in sample input" {
            sampleInput.toSensors().size shouldBe 14
            sampleInput.toSensors().first() shouldBe Sensor( Position(2,18), Position(-2,15) )
            sampleInput.toSensors().last() shouldBe Sensor( Position(20,1), Position(15,3) )
        }
        "manhatten size of Sensor at x=8, y=7: closest beacon is at x=2, y=10, is 9 " {
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10".toSensor().manhattenSize shouldBe 9
        }
        "xRange on row 7 for Sensor at x=8, y=7: closest beacon is at x=2, y=10, y=15 is -1..17 " {
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10".toSensor().xRange(7) shouldBe -1..17
        }
        "xRange on row 8 for Sensor at x=8, y=7: closest beacon is at x=2, y=10, y=15 is 0..16 " {
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10".toSensor().xRange(8) shouldBe 0..16
        }
        "xRange on row 16 for Sensor at x=8, y=7: closest beacon is at x=2, y=10, y=15 is 8..8 " {
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10".toSensor().xRange(16) shouldBe 8..8
        }
        "xRange on row 17 for Sensor at x=8, y=7: closest beacon is at x=2, y=10, y=15 is null " {
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10".toSensor().xRange(17) shouldBe null
        }
        "xRange on row 10 for Sensor at x=20, y=14: closest beacon is at x=25, y=17 is " {
            "Sensor at x=20, y=14: closest beacon is at x=25, y=17".toSensor().manhattenSize shouldBe 8
            "Sensor at x=20, y=14: closest beacon is at x=25, y=17".toSensor().xRange(14) shouldBe 12..28
            "Sensor at x=20, y=14: closest beacon is at x=25, y=17".toSensor().xRange(13) shouldBe 13..27
            "Sensor at x=20, y=14: closest beacon is at x=25, y=17".toSensor().xRange(10) shouldBe 16..24
        }
        "using the sampe input the sensors in range on line 10 are" {
            sampleInput.toSensors().sensorsInRange(10) shouldBe listOf(
                Sensor(position=Position(x=12, y=14), beacon=Position(x=10, y=16)),
                Sensor(position=Position(x=8, y=7), beacon=Position(x=2, y=10)),
                Sensor(position=Position(x=2, y=0), beacon=Position(x=2, y=10)),
                Sensor(position=Position(x=0, y=11), beacon=Position(x=2, y=10)),
                Sensor(position=Position(x=20, y=14), beacon=Position(x=25, y=17)),
                Sensor(position=Position(x=16, y=7), beacon=Position(x=15, y=3)))
        }
        "using the sample input the number of beacons on row 10 is 1" {
            sampleInput.toSensors().positionsOfBeaconsOnRow(10).size shouldBe 1
        }
        "squashing range 1..10 and 4..15 gives 1..15, is squashed to 1..10 and 20..30 gives 1..10, 20..30" {
            squashRanges(1..10, 4..15) shouldBe listOf(1..15)
        }
        "squashing a list of ranges gives a shorter list of ranges" {
            listOf(-2..2, 2..2, 2..14, 12..12, 14..18, 16..24).squashRanges() shouldBe listOf(-2..24)
        }
        "using the sample input and row 10 part one is 26" {
            partOne(sampleInput, 10) shouldBe 26
        }
        "using the puzzle input and row 2000000 part one is 5083287" {
            partOne(puzzleInput, 2000000) shouldBe 5083287
        }
    })
    "Part two" should ({
        "-2..3, 4..12, 10..14, 14..14, 14..26, 16..16 squashed to -2..26" {
            listOf(-2..3, 4..12, 10..14, 14..14, 14..26, 16..16).squashRanges() shouldBe listOf(-2..26)
        }
        "part two with sample Input" {
            partTwo(sampleInput, 20) shouldBe 56000011L
        }
        "part two with puzzle Input" {
            partTwo(puzzleInput, 4000000) shouldBe 13134039205729L
        }
    })
})