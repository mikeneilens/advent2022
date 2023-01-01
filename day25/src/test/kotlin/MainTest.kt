import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    val sampleSNAFU = """
1=-0-2,1747
12111,906
2=0=,198
21,11
2=01,201
111,31
20012,1257
112,32
1=-1=,353
1-12,107
12,7
1=,3
122,37
""".trimIndent().split("\n")
    "Part one" should ({
        "converting SNAFU to decimal" {
            sampleSNAFU.forEach { line ->
                val snafu = line.split(",")[0]
                val number = line.split(",")[1].toLong()
                snafu.snafuToDecimalNumber() shouldBe number
            }
        }
        "convert a number to a base 5 number" {
            55L.toBase5(powerOfFive.reversed()).dropWhile { it == 0L } shouldBe listOf(2L,1L,0L) // i.e. 210
        }
        "convert a decimal number to SNAFU" {
            sampleSNAFU.forEach { line ->
                val snafu = line.split(",")[0]
                val number = line.split(",")[1].toLong()
                number.decimalNumberToSnafu() shouldBe snafu
            }
        }
        "part one with puzzle input gives 2-1-110-=01-1-0-0==2" {
            partOne(puzzleInput) shouldBe "2-1-110-=01-1-0-0==2"
        }
    })

})
