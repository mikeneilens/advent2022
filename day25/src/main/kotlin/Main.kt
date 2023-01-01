import kotlin.math.pow

val powerOfFive = (0..20).map{5.0.pow(it).toLong()}

fun String.snafuToDecimalNumber() = reversed().mapIndexed { i, n -> n.snafuDigitToDecimal() * powerOfFive[i]}.sum()

fun Char.snafuDigitToDecimal() = if (this == '=') -2L else if (this == '-') -1L else this.toString().toLong()
fun Long.decimalToSnafuDigit() =  if (this >= 0L) this.toString() else if (this == -1L) "-" else "="

fun Long.decimalNumberToSnafu() = toBase5(powerOfFive.reversed()).correctDigits().toSnafuString()

fun Long.toBase5(powerOfFive:List<Long>, result:List<Long> = emptyList()):List<Long> =
    if (powerOfFive.isEmpty()) result
    else (this % powerOfFive.first()).toBase5(powerOfFive.drop(1), result + this / powerOfFive.first())

fun List<Long>.correctDigits():List<Long> {
    val snafuDigits = this.toMutableList()
    indices.forEach { i ->
        if (snafuDigits[i] in 3L..5L) {
            snafuDigits[i - 1] = snafuDigits[i - 1] + 1
            snafuDigits[i] =  snafuDigits[i] - 5L
        }
    }
    return if (snafuDigits.toList() == this) this else snafuDigits.correctDigits()
}

fun List<Long>.toSnafuString() = joinToString("",transform = Long::decimalToSnafuDigit).trim { it == '0' }

fun partOne(data:List<String>):String = data.sumOf(String::snafuToDecimalNumber).decimalNumberToSnafu()
