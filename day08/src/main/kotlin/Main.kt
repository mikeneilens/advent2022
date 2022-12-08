fun helloWorld() = "hello world"


fun String.highestOnLine() = fold(listOf<Char>()) {
        total, char -> if (total.size == 0 || char > total.last()) total + char else total + total.last()
}

fun List<String>.toColumns(size:Int):List<String> {
    val columns = (1..size).map{mutableListOf<Char>()}
    forEachIndexed {row, line ->
        line.forEachIndexed { col, char ->
            columns[col].add(char)
        }
    }
    return columns.map{it.joinToString("")}
}

data class HeightMap(val rows:List<String>, val size:Int = rows.size) {
    val columns = rows.toColumns(size)
    val highestFromLeft:List<List<Char>> by lazy { rows.map{it.highestOnLine()}}
    val highestFromRight by lazy { rows.map { it.reversed().highestOnLine() } }
    val highestFromTop by lazy { columns.map{it.highestOnLine()}}
    val highestFromBottom by lazy  {columns.map{it.reversed().highestOnLine()}}
}

fun isVisible(treeSize: Char, row: Int, col: Int, hm: HeightMap) =
    (isHighest(treeSize, hm.highestFromLeft[row], col)
            || isHighest(treeSize, hm.highestFromRight[row], hm.size - col - 1)
            || isHighest(treeSize, hm.highestFromTop[col], row)
            || isHighest(treeSize, hm.highestFromBottom[col], hm.size - row - 1)
    )

fun isHighest(treeSize: Char, row:List<Char>, col:Int) = ( treeSize == row[col] && (col == 0 || treeSize != row[col - 1]  )   )

fun partOne(rows: List<String>):Int {
    val treeMap = HeightMap(rows)
    return (0 until treeMap.size).sumOf { row ->
        (0 until treeMap.size).count { col ->
            isVisible(rows[row][col], row, col, treeMap)
        }
    }
}

fun partTwo(rows: List<String>):Int {
    val treeTopViews = createTreeTopViews(rows)
    return (0 until rows.size).flatMap { row ->
        (0 until rows.size).map { col -> treeTopViews.viewFrom(row, col) }
    }.max()
}

fun String.viewFromEachTreeLeft() = mapIndexed{index, it -> treesVisibleBefore(index)}

fun String.treesVisibleBefore(index:Int):Int {
    if (index == 0) return 0
    val char = get(index).toString()
    var count = 1
    while (index - count >= 0 && count < index && get(index - count).toString() < char &&(get(index - count).toString() < char))  {
        count++
    }
    return count
}

fun createTreeTopViews(rows: List<String>):TreeTopViews {
    val columns= rows.toColumns(rows.size)
    return TreeTopViews(
        rows.map(String::viewFromEachTreeLeft),
        rows.map { it.reversed() }.map(String::viewFromEachTreeLeft),
        columns.map(String::viewFromEachTreeLeft),
        columns.map { it.reversed() }.map(String::viewFromEachTreeLeft),
        rows.size
    )
}

data class TreeTopViews(val viewFromLeft: List<List<Int>>, val viewFromRight: List<List<Int>>, val viewDown: List<List<Int>>, val viewUp: List<List<Int>>, val size:Int) {
    fun viewFrom(row:Int, col:Int ):Int =
         viewFromLeft[row][col] * viewFromRight[row][size - col - 1] * viewDown[col][row] * viewUp[col][size - row -1 ]
}
