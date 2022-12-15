fun String.toSymbols():List<String> {
    val symbols = mutableListOf<String>()
    forEachIndexed{index, char ->
        if (char == '[' || char == ']') symbols.add(char.toString())
        else if (char in '0'..'9') {
            if (prevIsNum(index))
                symbols[symbols.lastIndex] = symbols[symbols.lastIndex] + char.toString()
            else  symbols.add(char.toString())
        }
    }
    return symbols
}
fun String.prevIsNum(i:Int) = (i> 0 && get(i -1) in '0'..'9' )

sealed class Item {
    data class Num(val value:Int):Item()
    class SubList(val items:List<Item>):Item()

    override fun toString():String = when (this) {
            is Num -> "num $value"
            is SubList -> "[ ${items.joinToString { item -> item.toString() }} ]"
        }
}

fun List<String>.parse(output:Item.SubList = Item.SubList(listOf()), expressionSize:Int = 0): Pair<Item.SubList, Int> {
    if (isEmpty()) return Pair(output, expressionSize)
    val symbol = first()
    if (symbol == "]") return Pair(output,expressionSize + 1)
    val (item, symbolsToDrop) = if (symbol.toIntOrNull() != null) Pair(Item.Num(symbol.toInt()), 1) else drop(1).parse(expressionSize = 1)
    return drop(symbolsToDrop).parse(Item.SubList(output.items + item ),expressionSize + symbolsToDrop)
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
        val (leftParsed, _) = left.toSymbols().parse(expressionSize = 0)
        val (rightParsed, _) = right.toSymbols().parse(expressionSize = 0)
        val result = compare(leftParsed, rightParsed)
        if (result == true) total += index + 1
    }
    return total
}

fun partTwo(data:List<String>):Int {
    val items = (data + "[[2]]" + "[[6]]"  ). map{it.toSymbols().parse(expressionSize = 0).first}
    val sorted = items.sortedWith{ l:Item.SubList, r:Item.SubList -> if (compare(l,r) == true) -1 else 1 }
    return (sorted.indexOfFirst{it.toString() == "[ [ [ Num(value=2) ] ] ]"} + 1) * (sorted.indexOfFirst{it.toString() == "[ [ [ Num(value=6) ] ] ]"} + 1)
}