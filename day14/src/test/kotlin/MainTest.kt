import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().split("\n")
    "Part one" should ({
        "part one with sample input" {
              partOne2(sampleInput) shouldBe 24
        }
        "part one with puzzle input" {
              partOne2(puzzleInput) shouldBe 1330
        }
    })
    "Part two" should ({
        "part two with sample input" {
              partTwo2(sampleInput) shouldBe 93
        }
        "part two with puzzle input" {
              partTwo2(puzzleInput) shouldBe 26139
        }
    })
})

fun MutableMap<Position, Char>.toText():String{
    var s=""
    (0..10).forEach{y->
        (480..520).forEach{x ->
            if (this[Position(x,y)] != null) s += this[Position(x,y)]
            else s += "."
        }
        s += "\n"
    }
    return s
}