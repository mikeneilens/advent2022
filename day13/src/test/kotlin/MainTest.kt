import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent().split("\n").filter { it != "" }
    "Part one" should ({
        "parse string [1,2,3,[4,10],11] into ['1','2','3','[','4','10',']','11',']'" {
            "[1,2,3,[4,10],11]".toSymbols() shouldBe listOf("[","1","2","3","[","4","10","]","11","]")
        }
        "parse 1,2,3 into sublistOf(Item.Num(1), Item.Num(2), Item.Num(3)) " {
            "1,2,3".toSymbols().parse().toString() shouldBe "[ Num(value=1), Num(value=2), Num(value=3) ]"
        }
        "parse 1,[2,3] into sublistOf(Item.Num(1), sublistOf(Item.Num(2), Item.Num(3))) " {
            "1,[2,3]".toSymbols().parse().toString() shouldBe "[ Num(value=1), [ Num(value=2), Num(value=3) ] ]"
        }
        "parse 1,[2,3],4 into sublistOf(Item.Num(1), sublistOf(Item.Num(2), Item.Num(3)), Item.Num(4)) " {
            "1,[2,3],4".toSymbols().parse().toString() shouldBe "[ Num(value=1), [ Num(value=2), Num(value=3) ], Num(value=4) ]"
        }
        "parse [1,[2,[3,[4,[5,6,7]]]],8,9] " {
            "[1,[2,[3,[4,[5,6,7]]]],8,9]".toSymbols().parse().toString() shouldBe
                    "[ [ Num(value=1), [ Num(value=2), [ Num(value=3), [ Num(value=4), [ Num(value=5), Num(value=6), Num(value=7) ] ] ] ], Num(value=8), Num(value=9) ] ]"
        }
        " with 1,2,3 and 2,2,3 they are in the right order because 1 < 2  " {
            val left = "1,2,3".toSymbols().parse()
            val right = "2,2,3".toSymbols().parse()
            compare(left, right) shouldBe true
        }
        "Compare [1,1,3,1,1] vs [1,1,5,1,1] should be true" {
            val left = "[1,1,3,1,1]".toSymbols().parse()
            val right = "[1,1,5,1,1]".toSymbols().parse()
            compare(left, right) shouldBe true
        }
        "Compare [[1],[2,3,4]] vs [[1],4] should be true" {
            val left = "[[1],[2,3,4]]".toSymbols().parse()
            val right = "[[1],4]".toSymbols().parse()
            compare(left, right) shouldBe true
        }
        "Compare [9] vs [[8,7,6]] should be false" {
            val left = "[9]".toSymbols().parse()
            val right = "[[8,7,6]]".toSymbols().parse()
            compare(left, right) shouldBe false
        }
        "Compare [[4,4],4,4] vs [[4,4],4,4,4] should be true" {
            val left = "[[4,4],4,4]".toSymbols().parse()
            val right = "[[4,4],4,4,4]".toSymbols().parse()
            compare(left, right) shouldBe true
        }
        "Compare [7,7,7,7] vs [7,7,7] should be false" {
            val left = "[7,7,7,7]".toSymbols().parse()
            val right = "[7,7,7]".toSymbols().parse()
            compare(left, right) shouldBe false
        }
        "Compare [] vs [3] should be true" {
            val left = "[]".toSymbols().parse()
            val right = "[3]".toSymbols().parse()
            compare(left, right) shouldBe true
        }
        "Compare [[[]]] vs [[]] should be false" {
            val left = "[[[]]]".toSymbols().parse()
            val right = "[[]]".toSymbols().parse()
            compare(left, right) shouldBe false
        }
        "Compare [1,[2,[3,[4,[5,6,7]]]],8,9] vs [1,[2,[3,[4,[5,6,0]]]],8,9] should be false" {
            val left = "[1,[2,[3,[4,[5,6,7]]]],8,9]".toSymbols().parse()
            val right = "[1,[2,[3,[4,[5,6,0]]]],8,9]".toSymbols().parse()
            compare(left, right) shouldBe false
        }
        "part one with sample input" {
            partOne(sampleInput) shouldBe 13
        }
        "part one with puzzle input" {
            partOne(puzzleInput) shouldBe 4734
        }
    })
    "Part two " should ({
        "part two with sample input" {
            partTwo(sampleInput) shouldBe  140
        }
        "part two with puzzle input" {
            partTwo(puzzleInput) shouldBe  21836
        }
    })
})