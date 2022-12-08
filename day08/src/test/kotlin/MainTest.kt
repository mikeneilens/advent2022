import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().split("\n")
    "Part one" should ({
        "highest on line of 33549 is 33559" {
            "33549".highestOnLine() shouldBe listOf('3','3','5','5','9')
        }
        "sample Input columns are 32633, 05535, 35353, 71349, 32290" {
            sampleInput.toColumns() shouldBe listOf("32633", "05535", "35353", "71349", "32290")
        }
        "[0][1] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('0',0,1,treeMap ) shouldBe true
        }
        "[2][0] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('6',2,0,treeMap ) shouldBe true
        }
        "[1][1] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('5',1,1,treeMap ) shouldBe true
        }
       "[1][2] in sample data is visible" {
           val treeMap = HeightMap(sampleInput)
            isVisible('5',1,2,treeMap ) shouldBe true
        }
        "[1][3] in sample data is not visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('1',1,3,treeMap ) shouldBe false
        }
        "[2][1] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('5',2,1,treeMap ) shouldBe true
        }
        "[2][2] in sample data is not visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('3',2,2,treeMap ) shouldBe false
        }
        "[2][3] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('3',2,3,treeMap ) shouldBe true
        }
        "[3][2] in sample data is visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('5',3,2,treeMap ) shouldBe true
        }
        "[3][1] in sample data is not visible" {
            val treeMap = HeightMap(sampleInput)
            isVisible('3',3,1,treeMap ) shouldBe false
        }
       "[3][3] in sample data is not visible" {
           val treeMap = HeightMap(sampleInput)
            isVisible('4',3,3,treeMap ) shouldBe false
        }
        "part one with sample data is 21" {
            partOne(sampleInput) shouldBe 21
        }
        "part one with puzzle input is 1807" {
            partOne(puzzleInput) shouldBe 1807
        }

    })
    "Part two" should ({
        "view from each tree when trees are 634545 is 0,1,2,3,1,2" {
            "634545".viewFromEachTreeLeft() shouldBe listOf(0,1,2,3,1,2)
        }
        "scenic score for [1,2] in the sampleData is 4" {
            val treeTopViews = TreeTopViews(sampleInput)
            treeTopViews.viewFrom(1,2) shouldBe 4
        }
        "scenic score for [3,2] in the sampleData is 8" {
            val treeTopViews = TreeTopViews(sampleInput)
            treeTopViews.viewFrom(3,2) shouldBe 8
        }
        "part two with sample data is 8 " {
            partTwo(sampleInput) shouldBe 8
        }
        "part two with puzzle input is " {
            partTwo(puzzleInput) shouldBe 480000
        }
        " 1234.indexOfPrevious before index = 2 where element >=3 is null " {
            "1234".toList().indexOfPrevious(2){ it.toString() >= "3" } shouldBe null
        }
        " 1434.indexOfPrevious before index = 2 element >=3 is 1 " {
            "1434".toList().indexOfPrevious(2){ it.toString() >= "3" } shouldBe 1
        }
        " 1234.indexOfPrevious before index = 0 element >=3 is null " {
            "1234".toList().indexOfPrevious(0){ it.toString() >= "3" } shouldBe null
        }
    })
})