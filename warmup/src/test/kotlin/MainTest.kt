import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe


class MainTest: WordSpec( {

    "Part one" should ({
        "add one product to the fixture" {
            val fixture =  fixturePattern.addProductsToFixture(listOf(1))
            fixture shouldBe listOf(1, null, null, null, null, null, null, null, null, null)
        }
        "add two products to the fixture" {
            val fixture =  fixturePattern.addProductsToFixture(listOf(1,2))
            fixture shouldBe listOf(1, null, 2, null, null, null, null, null, null, null)
        }
        "add eight products to the fixture" {
            val fixture =  fixturePattern.addProductsToFixture(listOf(1,2,3,4,5,6,7,8))
            fixture shouldBe listOf(1, null, 2, 3, 4, 5, 6, null, 7, 8)
        }
        "answer for eight products should be [[1],[2,3],[4,5],[6],[7,8]] " {
            val result = addAllProductsToFixtures(listOf(1,2,3,4,5,6,7,8))
            result shouldBe listOf(listOf(1), listOf(2,3), listOf(4,5), listOf(6), listOf(7,8))
        }
        "answer for ten products should be [[1],[2,3],[4,5],[6],[7,8],[9],[10]] " {
            val result = addAllProductsToFixtures(listOf(1,2,3,4,5,6,7,8,9,10))
            result shouldBe listOf(listOf(1), listOf(2,3), listOf(4,5), listOf(6), listOf(7,8), listOf(9), listOf(10))
        }
    })
})