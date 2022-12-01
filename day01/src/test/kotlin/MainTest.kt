import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import day01.*

class MainTest: WordSpec( {

    val sampleInput:String = """
    1000
    2000
    3000

    4000

    5000
    6000

    7000
    8000
    9000

    10000
""".trimIndent()

    "Part one" should ({
        "calaories of food for first elf is [1000,2000,3000]" {
            val result = parseInput(sampleInput)
            result.first() shouldBe listOf(1000,2000,3000)
        }
        "calories of food for each elf is [6000, 4000, 11000, 24000, 10000]" {
            val elvesFood = listOf(listOf(1000,2000,3000), listOf(4000),listOf(5000,6000),listOf(7000,8000,9000),listOf(10000) )
            elvesFood.sumCaloriesForEachElf() shouldBe listOf(6000, 4000, 11000, 24000, 10000)
        }
        "max calaroies carried is 24000" {
            val totalFoodForEachElf = listOf(6000, 4000, 11000, 24000, 10000)
            totalFoodForEachElf.findMaxCaroliesCarried() shouldBe 24000
        }
        "part one using sample data is 24000" {
            partOne(sampleInput) shouldBe 24000
        }
        "part one answer is 66487" {
            partOne(puzzleInput) shouldBe 66487
        }
    })
    "Part two" should ({
        "total calcories of the three elves carrying most food is 45000" {
            val totalFoodForEachElf = listOf(6000, 4000, 11000, 24000, 10000)
            totalFoodForEachElf.calcTotalCaloriesOfTopThreeElves() shouldBe 45000
        }
        "part two using sample data is 45000" {
            partTwo(sampleInput) shouldBe 45000
        }
        "part two answer is 197301" {
            partTwo(puzzleInput) shouldBe 197301
        }

    })
})