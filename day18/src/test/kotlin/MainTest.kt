import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

val sampleInput = """
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
""".trimIndent().split("\n")

class MainTest: WordSpec( {
    "Part one" should ({
        "parse sample input into cubes" {
            val cubes = sampleInput.toCubes()
            cubes.size shouldBe 13
            cubes shouldContain Cube(Position(2,2,2))
            cubes shouldContain  Cube(Position(2,3,5))
        }
        "cubes adjacent to Cube(1,1,1) should be " {
            val cube = Cube(1,1,1)
            cube.adjacentCubes shouldContain  Position(0,1,1)
            cube.adjacentCubes shouldContain  Position(2,1,1)
            cube.adjacentCubes shouldContain  Position(1,0,1)
            cube.adjacentCubes shouldContain  Position(1,2,1)
            cube.adjacentCubes shouldContain  Position(1,1,0)
            cube.adjacentCubes shouldContain  Position(1,1,2)
        }
        "no of sides not connected for cube(1,1,1) in [cube(1,1,1), cube(2,1,1)] "{
            val cubes = setOf(Cube(1,1,1), Cube(2,1,1))
            cubes.unconnectedSides(Cube(1,1,1)) shouldBe 5
        }
        "no of sides not connected for cube(2,1,1) in [cube(1,1,1), cube(2,1,1)] "{
            val cubes = setOf(Cube(2,1,1), Cube(2,1,1))
            cubes.unconnectedSides(Cube(1,1,1)) shouldBe 5
        }
        "part one with sample input is 64" {
            partOne(sampleInput) shouldBe 64
        }
        "part one with puzzle input is 4400" {
//            partOne(puzzleInput) shouldBe 4400
        }
    })
    "Part two" should ({
        "water can not move to a space occupied by a cube" {
            val waterMap = mutableSetOf<Position>()
            val cubes = setOf(Position(1,1,1), Position(1,2,3))
            val waterPosition = Position(1,1,1)
            waterMap.waterCanMoveTo( cubes, waterPosition, rangeToCheck(0..10,0..10,0..10)) shouldBe false
        }
        "water can not move to a space occupied by water" {
            val waterMap = mutableSetOf(Position(1,1,1))
            val cubes = setOf(Position(1,2,1), Position(1,2,3))
            val waterPosition = Position(1,1,1)
            waterMap.waterCanMoveTo( cubes, waterPosition, rangeToCheck(0..10,0..10,0..10)) shouldBe false
        }
        "water can not move to a space not in range" {
            val waterMap = mutableSetOf(Position(1,1,1))
            val cubes = setOf(Position(1,2,1), Position(1,2,3))
            val waterPosition = Position(1,-1,1)
            waterMap.waterCanMoveTo( cubes, waterPosition, rangeToCheck(0..10,0..10,0..10)) shouldBe false
        }
        "water can move to a space occupied by cubes or water and in range" {
            val waterMap = mutableSetOf(Position(1,1,1))
            val cubes = setOf(Position(1,2,1), Position(1,2,3))
            val waterPosition = Position(1,1,2)
            waterMap.waterCanMoveTo( cubes, waterPosition, rangeToCheck(0..10,0..10,0..10)) shouldBe true
        }
        "filling up a water map using simple input leaves 58 sides facing water" {
            val cubes = sampleInput.toCubes()
            val waterMap = mutableSetOf<Position>()
            fillUp(WaterStatus(
                waterMap,
                Position(0,0,0),
                cubes.map{it.p}.toSet(),
                rangeToCheck(cubes.xRange(),cubes.yRange(), cubes.zRange())
            ))
            val wetSides = waterMap.cubesSidesNextToWater(cubes)
            wetSides shouldBe 58
        }
        "part two with puzzle input gives 2522" {
            partTwo(puzzleInput) shouldBe 2522
        }
    })
})