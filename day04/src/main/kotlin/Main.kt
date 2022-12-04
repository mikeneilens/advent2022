data class Assignment (val start:Int, val end:Int ) {
    fun insideOther(other: Assignment) = start <= other.start && end >= other.end
}

data class Section(val first:Assignment, val second:Assignment) {
    fun assignmentContainsOtherAssignment() =
        first.insideOther(second) || second.insideOther(first)

    fun assignmentsOverlap():Boolean {
        val section = getAssignmentsInOrder()
        return (section.second.start <= section.first.end)
    }

    fun getAssignmentsInOrder():Section =
        if (first.start <= second.start) this else Section(second, first)
}

fun String.toSection():Section {
    val firstAssignment = getAssignment(0)
    val secondAssignment = getAssignment(1)
    return Section(Assignment(firstAssignment.first(), firstAssignment.last()), Assignment(secondAssignment.first(), secondAssignment.last()) )
}

fun String.getAssignment(n: Int) = split(",")[n].split("-").map{it.toInt()}

fun partOne(data: List<String>) = data.map(String::toSection).count(Section::assignmentContainsOtherAssignment)

fun partTwo(data:List<String>) = data.map(String::toSection).count(Section::assignmentsOverlap)