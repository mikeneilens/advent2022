import kotlin.math.pow

val powerOfFive = (0..20).map{5.0.pow(it).toLong()}

fun String.snafuToBase10Number() = reversed().mapIndexed { i, n -> n.snafuDigitToBase10() * powerOfFive[i]}.sum()

fun Char.snafuDigitToBase10() = if (this == '=') -2L else if (this == '-') -1L else this.toString().toLong()
fun Long.base10ToSnafuDigit() =  if (this >= 0L) this.toString() else if (this == -1L) "-" else "="

fun Long.base10NumberToSnafu() =
    base10toBase5(powerOfFive.reversed()).base5DigitsToSnafuDigits().snafuDigitsToSnafuString()

fun Long.base10toBase5(powerOfFive:List<Long>, result:List<Long> = emptyList()):List<Long> =
    if (powerOfFive.isEmpty()) result
    else (this % powerOfFive.first()).base10toBase5(powerOfFive.drop(1), result + this / powerOfFive.first())

fun List<Long>.base5DigitsToSnafuDigits():List<Long> {
    val snafuDigits = this.toMutableList()
    indices.forEach { i ->
        if (snafuDigits[i] in 3L..5L) {
            snafuDigits[i - 1] = snafuDigits[i - 1] + 1
            snafuDigits[i] =  snafuDigits[i] - 5L
        }
    }
    return if (snafuDigits.toList() == this) this else snafuDigits.base5DigitsToSnafuDigits()
}

fun List<Long>.snafuDigitsToSnafuString() = joinToString("",transform = Long::base10ToSnafuDigit).trim { it == '0' }

fun partOne(data:List<String>):String = data.sumOf(String::snafuToBase10Number).base10NumberToSnafu()
