import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    "Part one" should ({
        "test" {
            helloWorld() shouldBe "hello world"
        }
    })
    "Part two" should ({
        "test" {
            helloWorld() shouldBe "hello world"
        }
    })
})