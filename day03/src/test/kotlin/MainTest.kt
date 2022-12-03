import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {

    val sampleInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().split("\n")

    "Part one" should ({
        "convert first half of abcd is [a,b]" {
            "abcd".firstHalf() shouldBe listOf('a','b')
        }
        "convert second half of abcd is [c,d]" {
            "abcd".secondHalf() shouldBe listOf('c','d')
        }
        "parse abcd into Rucksack([a,b],[c,d])" {
            "abcd".toRucksack() shouldBe Rucksack(listOf('a','b'), listOf('c','d'),listOf('a','b','c','d'))
        }
        "find first item in vJrwpWtwJgWrhcsFMMfFFhFp contains in both sides is p" {
            val rucksack = "vJrwpWtwJgWrhcsFMMfFFhFp".toRucksack()
            rucksack.findFirstInBothSides() shouldBe  'p'
        }
        "find value of first item in vJrwpWtwJgWrhcsFMMfFFhFp contains in both sides is p" {
            val rucksack = "vJrwpWtwJgWrhcsFMMfFFhFp".toRucksack()
            rucksack.findCodeForFirstInBothSides() shouldBe  16
        }
        "convert a to value of 1" {
            'a'.toValue() shouldBe 1
        }
        "convert A to value of 27" {
            'A'.toValue() shouldBe 27
        }
        "part one with sample input is 157" {
            partOne(sampleInput) shouldBe 157
        }
        "part one with puzzle input is 7763" {
            partOne(puzzleInput) shouldBe 7763
        }
    })
    "Part two" should ({
        "find badge in first 3 bags of puzzle input is r"{
            val elvesBags = sampleInput.take(3).toElvesBags().first()
            elvesBags.findBadge() shouldBe 'r'
        }
        "find badge in second 3 bags of puzzle input is Z"{
            val elvesBags = sampleInput.drop(3).toElvesBags().first()
            elvesBags.findBadge() shouldBe 'Z'
        }
        "find code of badge in first 3 bags of puzzle input is r"{
            val elvesBags = sampleInput.take(3).toElvesBags().first()
            elvesBags.findCodeForBadge() shouldBe 18
        }
        "part two with sample input is 70" {
            partTwo(sampleInput) shouldBe 70
        }
        "part two with sample input is 2569" {
            partTwo(puzzleInput) shouldBe 2569
        }

    })
})