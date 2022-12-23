import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class Main3Test: WordSpec( {
    "Part one" should ({
        "adding sample input to linkedlist" {
            val list = mutableListOf<LinkedList>()
            val linkedList = "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.size shouldBe 7
            linkedList.toList() shouldBe list
        }
        "move first item " {
            val list = mutableListOf<LinkedList>()
            "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.first().move(list.size.toLong() - 1)
            list.first().toList().map{it?.value} shouldBe listOf(1,-3,3,-2,0,4,2)
        }
        "move 2nd item " {
            val list = mutableListOf<LinkedList>()
            "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.take(2).forEach { it.move(list.size.toLong() - 1) }
            list.first().toList().map{it?.value} shouldBe listOf(1,-3,2,3,-2,0,4)
        }
        "move 3rd item " {
            val list = mutableListOf<LinkedList>()
            "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.take(3).forEach { it.move(list.size.toLong() - 1) }
            list.first().toList().map{it?.value} shouldBe listOf(1,2,3,-2,-3,0,4)
        }
        "move 7 items " {
            val list = mutableListOf<LinkedList>()
            "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.take(7).forEach { it.move(list.size.toLong() - 1) }
            list.first().toList().map{it?.value} shouldBe listOf(1, 2, -3, 4, 0, 3, -2)
        }
        "get item 3 positions after zero should be 2" {
            val list = mutableListOf<LinkedList>()
            "1, 2, -3, 3, -2, 0, 4".toLinkedList(list)
            list.itemWithValue(0).nodeAfter(3).value shouldBe 2
        }

        "part one with sample input" {
            partOne("1, 2, -3, 3, -2, 0, 4") shouldBe 3
        }
        "part one with puzzle input" {
            partOne(puzzleInput) shouldBe 23321
        }


    })
    "Part two" should ({
        "part two with sample input" {
            partTwo("1, 2, -3, 3, -2, 0, 4") shouldBe 1623178306
        }
        "part two with puzzle input" {
            partTwo(puzzleInput) shouldBe 1428396909280
        }
    })
})

fun LinkedList.toList():List<LinkedList?> {
    val list = mutableListOf<LinkedList?>(this)
    while (list.last()?.next != this) {
        list.add(list.last()?.next)
    }
    return list
}
