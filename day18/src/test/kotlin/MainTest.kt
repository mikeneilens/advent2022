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
            cubes[0] shouldBe Cube(Position(2,2,2))
            cubes[12] shouldBe Cube(Position(2,3,5))
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
            val cubes = listOf(Cube(1,1,1), Cube(2,1,1))
            cubes.unconnectedSides(Cube(1,1,1)) shouldBe 5
        }
        "no of sides not connected for cube(2,1,1) in [cube(1,1,1), cube(2,1,1)] "{
            val cubes = listOf(Cube(2,1,1), Cube(2,1,1))
            cubes.unconnectedSides(Cube(1,1,1)) shouldBe 5
        }
        "part one with sample input is 64" {
            partOne(sampleInput) shouldBe 64
        }
        "part one with puzzle input is 4400" {
            partOne(puzzleInput) shouldBe 4400
        }
    })
    "Part two" should ({
        "test" {
            helloWorld() shouldBe "hello world"
        }
    })
})