import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trimIndent().split("\n\n").map{it.split("\n")}
    "Part one" should ({
        "Monkey number of first monkey" {
            sampleInput[0].monkeyNumber() shouldBe 0
        }
        "Starting items of first monkey is 79, 98" {
            sampleInput[0].startingItems() shouldBe listOf(79,98)
        }
        "operation for old * 19 /3" {
            val operation = worryUpdater("*", "19", { n -> n /3})
            operation(2) shouldBe 12
        }
        "operation for old * old /3" {
            val operation = worryUpdater("*", "old", { n -> n /3})
            operation(5) shouldBe 8        }
        "operation for (old + 19) /3" {
            val operation = worryUpdater("+", "19", { n -> n /3})
            operation(5) shouldBe 8
        }
        "operation of first monkey is old * 19 /3" {
            sampleInput[0].worryUpdater({ n -> n /3})(2) shouldBe 12
        }
        "test divisible of first monkey is 23" {
            val trueMonkey = 1
            val falseMonkey = 2
            sampleInput[0].testDivisible()(46, trueMonkey, falseMonkey) shouldBe trueMonkey
            sampleInput[0].testDivisible()(47, trueMonkey, falseMonkey) shouldBe falseMonkey
        }
        "throw to monkey 2 if true and monkey 3 if false" {
            val divisibleRule = sampleInput[0].testDivisible()
            sampleInput[0].monkeyToThrowTo(divisibleRule)(46) shouldBe 2
            sampleInput[0].monkeyToThrowTo(divisibleRule)(47) shouldBe 3
        }
        "sample input toMonkeys creates a list of 4 monkeys" {
            val monkeys = sampleInput.toMonkeys({n -> n /3})
            monkeys.size shouldBe 4
            monkeys[0].monkeyNumber shouldBe 0
            monkeys[3].monkeyNumber shouldBe 3
        }
        "process first monkey in the Sample Input" {
            val monkeys = sampleInput.toMonkeys({n -> n /3})
            monkeys.processAll(monkeys[0])
            monkeys[0].items shouldBe listOf()
            monkeys[0].noOfOperations shouldBe 2
            monkeys[3].items shouldBe listOf(74, 500, 620)
        }
        "processing all monkies for one round in the Sample Input" {
            val monkeys = sampleInput.toMonkeys({n -> n /3})
            monkeys.processRound()
            monkeys[0].items shouldBe listOf(20, 23, 27, 26)
            monkeys[1].items shouldBe listOf(2080, 25, 167, 207, 401, 1046)
            monkeys[2].items shouldBe listOf()
            monkeys[3].items shouldBe listOf()
        }
        "part one with sample input is 10605" {
            partOne(sampleInput) shouldBe 10605
        }
        "part one with puzzle input is 98280" {
            partOne(puzzleInput) shouldBe 98280
        }
    })
    "Part two" should ({
        "processing all monkies for one round in the Sample Input" {
            val lowestCommonMultiplier = sampleInput.map{it.divisibleNo()}.toLowestCommonMultiplier()
            val monkeys = sampleInput.toMonkeys({ n -> n % lowestCommonMultiplier  })
            monkeys.processRound()
            monkeys[0].noOfOperations shouldBe 2
            monkeys[1].noOfOperations shouldBe 4
            monkeys[2].noOfOperations shouldBe 3
            monkeys[3].noOfOperations shouldBe 6
        }
        "processing all monkies for 20 rounds in the Sample Input" {
            val lowestCommonMultiplier = sampleInput.map{it.divisibleNo()}.toLowestCommonMultiplier()
            val monkeys = sampleInput.toMonkeys({ n -> n % lowestCommonMultiplier  })
            repeat(20){monkeys.processRound()}
            monkeys[0].noOfOperations shouldBe 99
            monkeys[1].noOfOperations shouldBe 97
            monkeys[2].noOfOperations shouldBe 8
            monkeys[3].noOfOperations shouldBe 103
        }
        "processing all monkies for 1000 rounds in the Sample Input" {
            val lowestCommonMultiplier = sampleInput.map{it.divisibleNo()}.toLowestCommonMultiplier()
            val monkeys = sampleInput.toMonkeys({ n -> n % lowestCommonMultiplier  })
            repeat(1000){monkeys.processRound()}
            monkeys[0].noOfOperations shouldBe 5204
            monkeys[1].noOfOperations shouldBe 4792
            monkeys[2].noOfOperations shouldBe 199
            monkeys[3].noOfOperations shouldBe 5192
        }
        "part two with sample input is 2713310158" {
           partTwo(sampleInput) shouldBe 2713310158
        }
        "part two with puzzle input is 2713310158" {
           partTwo(puzzleInput) shouldBe 17673687232L
        }
    })
})