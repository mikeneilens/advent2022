import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().split("\n")
    "Part one" should ({
        "partOne using sampleInput" {
            partOne(sampleInput) shouldBe 95437
        }
        "partOne using puzzleInput" {
            partOne(puzzleInput) shouldBe 2031851
        }
    })
    "Part two" should ({
        "partTwo using sampleInput should free up 24933642" {
            partTwo(sampleInput).size shouldBe  24933642
        }
        "partTwo using puzzleInput should free up 2568781" {
            partTwo(puzzleInput).size shouldBe  2568781
        }
    })
})