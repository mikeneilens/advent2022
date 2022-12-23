class LinkedList (val value:Long, var next:LinkedList? = null, var prev:LinkedList? = null) {

    fun move(size:Long) {
        remove()
        insert(nodeToInsertAfter = nodeToInsertAfter(size))
    }

    fun remove() {
        prev?.next = next
        next?.prev = prev
    }

    fun insert(nodeToInsertAfter:LinkedList?) {
        prev = nodeToInsertAfter
        next = nodeToInsertAfter?.next
        nodeToInsertAfter?.next = this
        next?.prev = this
    }

    fun nodeToInsertAfter(size: Long): LinkedList? =
        if (value >= 0) prev?.nodeAfter(value % size) else prev?.nodeAfter(size + (value % size))

    fun nodeAfter(n:Long ):LinkedList = if (n == 0L) this else next?.nodeAfter(n - 1) ?: this
}

fun String.toLinkedList(listOfNodes:MutableList<LinkedList> = mutableListOf(), multiplier:Int = 1):LinkedList {
    val numbers = split(", ").map{it.toLong() * multiplier}
    val firstNode = LinkedList(numbers.first())
    listOfNodes.add(firstNode)
    numbers.drop(1).fold(firstNode){ ll, number ->
        val newItem = LinkedList(number, firstNode, listOfNodes.last())
        ll.next = newItem
        listOfNodes.add(newItem)
        newItem
    }
    firstNode.prev = listOfNodes.last()
    return firstNode
}

fun List<LinkedList>.itemWithValue(n:Long) = first{it.value == n}

fun partOne(data:String, noOfRepetitions:Int = 1, multiplier:Int = 1):Long {
    val listOfNodes = mutableListOf<LinkedList>()
    data.toLinkedList(listOfNodes, multiplier)
    repeat(noOfRepetitions) {
        listOfNodes.forEach {
            it.move(listOfNodes.size.toLong() - 1)
        }
    }
    val zeroNode = listOfNodes.itemWithValue(0)
    return (1L..3L).sumOf{zeroNode.nodeAfter(it * 1000).value}
}

fun partTwo(data:String):Long = partOne(data, noOfRepetitions = 10, multiplier = 811589153)
