import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    "Part one" should ({
        "start of packet for mjqjpqmgbljsphdztnvjfqwrcgsmlb is 7" {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findFirstMarker() shouldBe 7
        }
        "start of packet for zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw is 11" {
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".findFirstMarker() shouldBe 11
        }
        "start of packet for puzzle input is 1198 " {
            puzzleInput.findFirstMarker() shouldBe 1198
        }
    })
    "Part two" should ({
        "start of packet for mjqjpqmgbljsphdztnvjfqwrcgsmlb is 19" {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findFirstMarker(14) shouldBe 19
        }
        "start of packet for puzzle input is 3120 " {
            puzzleInput.findFirstMarker(14) shouldBe 3120
        }
    })
})