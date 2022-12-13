fun String.toSymbols():List<String> {
    val symbols = mutableListOf<String>()
    var prevWasNum = false
    forEach{char ->
        if (char == '[' || char == ']') symbols.add(char.toString())
        if (char in '0'..'9') {
            if (prevWasNum)
                symbols[symbols.lastIndex] = symbols[symbols.lastIndex] + char.toString()
            else  symbols.add(char.toString())
            prevWasNum = true
        } else prevWasNum = false
    }
    return symbols
}

sealed class Item {
    data class Num(val value:Int):Item()
    class SubList(val items:List<Item>):Item()

    override fun toString():String {
        return if (this is Num) return "num $value"
        else if (this is SubList) return "[ ${ items.map{it.toString() }.joinToString()} ]"
        else ""
    }
}

fun List<String>.parse(output:Item.SubList = Item.SubList(listOf())):Item.SubList {
    if (isEmpty()) return output
    val symbol = first()
    if (symbol == "]") return output
    val (item, dropQty)  = if ( symbol.toIntOrNull() != null) Pair(Item.Num(symbol.toInt()),1)
                            else Pair( drop(1).parse(), drop(1).nextIndex())
    return drop(dropQty).parse(Item.SubList(output.items + item ))
}

fun List<String>.nextIndex():Int {
    var open = 1
    var ndx = 0
    while (open > 0) {
        if (this[ndx] == "[") open++ else if (this[ndx] == "]") open--
        ndx++
    }
    return ndx + 1
}

fun compare(_left:Item, _right:Item):Boolean? {
    val left = if (_left is Item.Num) Item.SubList(listOf(_left)) else _left as Item.SubList
    val right = if (_right is Item.Num ) Item.SubList(listOf(_right)) else _right as Item.SubList

    if (left.items.isEmpty() && right.items.isNotEmpty()) return true
    if (right.items.isEmpty() && left.items.isNotEmpty()) return false
    if (right.items.isEmpty() && left.items.isEmpty()) return null

    val leftFirst = left.items.first()
    val rightFirst = right.items.first()
    if (leftFirst is Item.Num && rightFirst is Item.Num) {
        if (leftFirst.value < rightFirst.value) return true
        if (rightFirst.value < leftFirst.value) return false
    }
    if (leftFirst is Item.SubList || rightFirst is Item.SubList) {
        val compare = compare(leftFirst, rightFirst)
        if (compare != null) return compare
    }
    return compare(Item.SubList(left.items.drop(1)), Item.SubList(right.items.drop(1)) )
}

fun partOne(data:List<String>):Int {
    var total = 0
    data.chunked(2).forEachIndexed { index, pair ->
        val (left, right) = pair
        val result = compare(left.toSymbols().parse(), right.toSymbols().parse())
        if (result == true) total += index + 1
    }
    return total
}

fun partTwo(data:List<String>):Int {
    val items = (data + "[[2]]" + "[[6]]"  ). map{it.toSymbols().parse()}
    val sorted = items.sortedWith{ l:Item.SubList, r:Item.SubList -> if (compare(l,r) == true) -1 else 1 }
    return (sorted.indexOfFirst{it.toString() == "[ [ [ Num(value=2) ] ] ]"} + 1) * (sorted.indexOfFirst{it.toString() == "[ [ [ Num(value=6) ] ] ]"} + 1)

}