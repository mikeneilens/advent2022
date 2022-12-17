import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun readInput() = readFile("input")

fun readTestInput() = readFile("input-test")

fun <T> checkTestResult(actual: T, expected: T) {
    require(actual == expected) { "wrong test result: is $actual, but should have been $expected" }
}

fun runProblem01(test: () -> Any) {
    runTest("result01", test)
}

fun runProblem02(test: () -> Any) {
    runTest("result02", test)
}

@OptIn(ExperimentalTime::class)
fun <T> runTest(name: String, test: () -> T) {
    val (result, duration) = measureTimedValue(test)
    println("$name = $result (in $duration)")
}

fun String.splitByEmptyLines(numberOfEmptyLines: Int = 1): List<String> =
    split(multipleEoL(numberOfEmptyLines + 1))

private fun readFile(fileName: String): String {
    val stackTrace = Thread.currentThread().stackTrace
    val thisPackageName = getPackageName(stackTrace[1].className)
    val callerPackageName = getCallerPackageName(thisPackageName, stackTrace)

    val path = "$callerPackageName/$fileName"
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)
        ?: error("could not find file at classpath resource $path")

    return stream.use {
        stream.reader().readText().trimEnd()
    }
}

private fun getCallerPackageName(thisPackageName: String, stackTrace: Array<StackTraceElement>): String {
    for (i in 2 until stackTrace.size) {
        val stackTracePackageName = getPackageName(stackTrace[i].className)
        if (stackTracePackageName != thisPackageName) {
            return stackTracePackageName
        }
    }

    @Suppress("ThrowingExceptionsWithoutMessageOrCause")
    val cause = Exception()
    cause.stackTrace = stackTrace
    throw IllegalStateException("could not find caller package name for causes stack trace", cause)
}

private fun getPackageName(className: String) =
    className.substringBeforeLast(".").replace(".", "/")

private val eol = Regex("""\r\n|\r|\n""")

@Suppress("RegExpSimplifiable")
private fun multipleEoL(count: Int) = "(?:${eol.pattern}){$count}+".toRegex()

data class Vector(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Vector): Vector = Vector(x + other.x, y + other.y)

    operator fun minus(other: Vector): Vector = Vector(x - other.x, y - other.y)
}