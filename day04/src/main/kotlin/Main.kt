data class Assignment (val start: Int, val end: Int ) {
    fun insideOther(other: Assignment) = start <= other.start && end >= other.end
}

data class Section(val first: Assignment, val second: Assignment) {
    fun assignmentContainsOtherAssignment() = first.insideOther(second) || second.insideOther(first)

    fun assignmentsOverlap():Boolean {
        val section = getAssignmentsInOrder()
        return (section.second.start <= section.first.end)
    }

    fun getAssignmentsInOrder() = if (first.start <= second.start) this else Section(second, first)
}

fun String.toSection():Section {
    val values = split(",","-").map(String::toInt)
    return Section(Assignment(values[0], values[1]), Assignment(values[2], values[3]) )
}

fun partOne(data: List<String>) = data.map(String::toSection).count(Section::assignmentContainsOtherAssignment)

fun partTwo(data: List<String>) = data.map(String::toSection).count(Section::assignmentsOverlap)