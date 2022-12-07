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

    val root = Directory("root", files = mutableListOf(File("f1",1)))
    val d1 = Directory("d1", files = mutableListOf(File("f2",2)), parent = root)
    root.children.add(d1)
    val d2 = Directory("d2", files = mutableListOf(File("f3",3)), parent = d1)
    d1.children.add(d2)
    val d3 = Directory("d3", files = mutableListOf(File("f4",4)), parent = d1)
    d1.children.add(d3)

    "Part one" should ({
        "return all directories correctly" {
            root.allDirectories() shouldBe listOf(d2,d3,d1,root)
        }
        "return size of directories correctly" {
            root.size shouldBe 10
            d1.size shouldBe 9
            d2.size shouldBe 3
            d3.size shouldBe 4
        }
        "return directories below a certain size correctly" {
            root.totalSizeOfDirectoriesSmallerThan(4) shouldBe 7
        }
        "partOne using sampleInput" {
            partOne(sampleInput) shouldBe 95437
        }
        "partOne using puzzleInput" {
            partOne(puzzleInput) shouldBe 2031851
        }
    })
    "Part two" should ({
        "directory to delete to remove 8 should be d1 which has a size of 8" {
            root.directoryToDelete(8) shouldBe d1
        }
        "directory to delete to remove 2 should be d2 which has a size of 3" {
            root.directoryToDelete(2) shouldBe d2
        }
        "partTwo using sampleInput should free up 24933642" {
            partTwo(sampleInput).size shouldBe  24933642
        }
        "partTwo using puzzleInput should free up 2568781" {
            partTwo(puzzleInput).size shouldBe  2568781
        }
    })
})