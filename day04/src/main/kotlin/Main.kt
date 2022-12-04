data class Assignment (val start: Int, val end: Int ) {
    fun insideOther(other: Assignment) = start <= other.start && end >= other.end
}

data class Section(val firstAssignment: Assignment, val secondAssignment: Assignment) {
    fun assignmentContainsOtherAssignment() = firstAssignment.insideOther(secondAssignment) || secondAssignment.insideOther(firstAssignment)

    fun assignmentsOverlap() = !(secondAssignment.start > firstAssignment.end || firstAssignment.start > secondAssignment.end )

}

fun String.toSection():Section {
    val values = split(",","-").map(String::toInt)
    return Section(Assignment(values[0], values[1]), Assignment(values[2], values[3]) )
}

fun partOne(data: List<String>) = data.map(String::toSection).count(Section::assignmentContainsOtherAssignment)

fun partTwo(data: List<String>) = data.map(String::toSection).count(Section::assignmentsOverlap)