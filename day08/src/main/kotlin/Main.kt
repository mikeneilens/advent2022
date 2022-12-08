
fun List<String>.toColumns():List<String> {
    val columns = (1..size).map{mutableListOf<Char>()}
    forEach { line ->
        line.forEachIndexed { col, char ->
            columns[col].add(char)
        }
    }
    return columns.map{it.joinToString("")}
}

data class HeightMap(val rows:List<String>, val size:Int = rows.size) {
    private val columns = rows.toColumns()
    val highestFromLeft:List<List<Char>> = rows.map{it.highestOnLine()}
    val highestFromRight = rows.map { it.reversed().highestOnLine() }
    val highestFromTop = columns.map{it.highestOnLine()}
    val highestFromBottom = columns.map{it.reversed().highestOnLine()}
}

fun String.highestOnLine() = fold(listOf<Char>()) { total, char -> if (total.isEmpty() || char > total.last()) total + char else total + total.last() }

fun isVisible(treeSize: Char, row: Int, col: Int, hm: HeightMap) =
    isHighest(treeSize, hm.highestFromLeft[row], col)
            || isHighest(treeSize, hm.highestFromRight[row], hm.size - col - 1)
            || isHighest(treeSize, hm.highestFromTop[col], row)
            || isHighest(treeSize, hm.highestFromBottom[col], hm.size - row - 1)

fun isHighest(treeSize: Char, row:List<Char>, col:Int) = ( treeSize == row[col] && (col == 0 || treeSize != row[col - 1]  )   )

fun partOne(rows: List<String>):Int {
    val heightMap = HeightMap(rows)
    return (rows.indices).sumOf { row ->
        (rows.indices).count { col ->
            isVisible(rows[row][col], row, col, heightMap)
        }
    }
}

fun partTwo(rows: List<String>):Int {
    val treeTopViews = TreeTopViews(rows)
    return (rows.indices).flatMap { row ->
        (rows.indices).map { col -> treeTopViews.viewFrom(row, col) }
    }.max()
}

data class TreeTopViews(val rows: List<String>) {
    private val columns= rows.toColumns()
    private val viewFromLeft = rows.map(String::viewFromEachTreeLeft)
    private val viewFromRight = rows.map { it.reversed() }.map(String::viewFromEachTreeLeft)
    private val viewDown = columns.map(String::viewFromEachTreeLeft)
    private val viewUp = columns.map { it.reversed() }.map(String::viewFromEachTreeLeft)
    private val size = rows.size

    fun viewFrom(row:Int, col:Int ):Int =
         viewFromLeft[row][col] * viewFromRight[row][size - col - 1] * viewDown[col][row] * viewUp[col][size - row -1 ]
}

fun String.viewFromEachTreeLeft() = mapIndexed{index, _ -> treesVisibleBefore(index)}

fun String.treesVisibleBefore(index:Int):Int {
    if (index == 0) return 0
    val char = get(index).toString()
    var count = 1
    while (index - count >= 0 && count < index && get(index - count).toString() < char &&(get(index - count).toString() < char))  {
        count++
    }
    return count
}
