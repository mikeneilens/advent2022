data class Assignment (val start: Int, val end: Int ) {
    infix fun isInside(other: Assignment) = start <= other.start && end >= other.end

    infix fun overlaps(other: Assignment) = (end in other.start..other.end) || (start in other.start..other.end)
}

data class Section(val firstAssignment: Assignment, val secondAssignment: Assignment) {
    fun assignmentContainsOtherAssignment() = (firstAssignment isInside secondAssignment) || (secondAssignment isInside firstAssignment)

    fun assignmentsOverlap() = (firstAssignment overlaps secondAssignment) || (secondAssignment overlaps firstAssignment)

}

fun String.toSection():Section {
    val values = split(",","-").map(String::toInt)
    return Section(Assignment(values[0], values[1]), Assignment(values[2], values[3]) )
}

fun partOne(data: List<String>) = data.map(String::toSection).count(Section::assignmentContainsOtherAssignment)

fun partTwo(data: List<String>) = data.map(String::toSection).count(Section::assignmentsOverlap)