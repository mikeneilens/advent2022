import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent().split("\n")
    "Part one" should ({
        "parse sample input into nodes" {
            val nodes = sampleInput.parse()
            (nodes["root"]?.job is Job.MathOperation) shouldBe true
            val rootNode = nodes["root"]?.job as Job.MathOperation
            rootNode.operation(1,2) shouldBe 3
            rootNode.node1 shouldBe nodes.getValue("pppw")
            rootNode.node2 shouldBe nodes.getValue("sjmn")
        }
        "calculate total for sample input as 152" {
            val nodes = sampleInput.parse()
            val root = nodes.getValue("root")
            root.total() shouldBe 152
        }
        "partone using sample input is 152" {
            partOne(sampleInput) shouldBe 152
        }
        "partone using puzzle input is 152" {
            partOne(puzzleInput) shouldBe 309248622142100
        }
    })
    "Part two" should ({
        "part two using puzzle input" {
            partTwo(puzzleInput) shouldBe 3757272361782
            //nb. 3757272361783 is also correct!
        }
    })
})