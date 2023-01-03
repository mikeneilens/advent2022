# detekt

## Metrics

* 8 number of properties

* 8 number of functions

* 1 number of classes

* 1 number of packages

* 3 number of kt files

## Complexity Report

* 214 lines of code (loc)

* 201 source lines of code (sloc)

* 188 logical lines of code (lloc)

* 1 comment lines of code (cloc)

* 18 cyclomatic complexity (mcc)

* 12 cognitive complexity

* 5 number of total code smells

* 0% comment source ratio

* 95 mcc per 1,000 lloc

* 26 code smells per 1,000 lloc

## Findings (5)

### style, MagicNumber (5)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /Users/michaelneilens/github/advent2022/day25/src/main/kotlin/Main.kt:3:23
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
1 import kotlin.math.pow
2 
3 val powerOfFive = (0..20).map{5.0.pow(it).toLong()}
!                       ^ error
4 
5 fun String.snafuToBase10Number() = reversed().mapIndexed { i, n -> n.snafuDigitToBase10() * powerOfFive[i]}.sum()
6 

```

* /Users/michaelneilens/github/advent2022/day25/src/main/kotlin/Main.kt:7:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
4  
5  fun String.snafuToBase10Number() = reversed().mapIndexed { i, n -> n.snafuDigitToBase10() * powerOfFive[i]}.sum()
6  
7  fun Char.snafuDigitToBase10() = if (this == '=') -2L else if (this == '-') -1L else this.toString().toLong()
!                                                    ^ error
8  fun Long.base10ToSnafuDigit() =  if (this >= 0L) this.toString() else if (this == -1L) "-" else "="
9  
10 fun Long.base10NumberToSnafu() =

```

* /Users/michaelneilens/github/advent2022/day25/src/main/kotlin/Main.kt:20:31
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
17 fun List<Long>.base5DigitsToSnafuDigits():List<Long> {
18     val snafuDigits = this.toMutableList()
19     indices.forEach { i ->
20         if (snafuDigits[i] in 3L..5L) {
!!                               ^ error
21             snafuDigits[i - 1] = snafuDigits[i - 1] + 1
22             snafuDigits[i] =  snafuDigits[i] - 5L
23         }

```

* /Users/michaelneilens/github/advent2022/day25/src/main/kotlin/Main.kt:20:35
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
17 fun List<Long>.base5DigitsToSnafuDigits():List<Long> {
18     val snafuDigits = this.toMutableList()
19     indices.forEach { i ->
20         if (snafuDigits[i] in 3L..5L) {
!!                                   ^ error
21             snafuDigits[i - 1] = snafuDigits[i - 1] + 1
22             snafuDigits[i] =  snafuDigits[i] - 5L
23         }

```

* /Users/michaelneilens/github/advent2022/day25/src/main/kotlin/Main.kt:22:48
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
19     indices.forEach { i ->
20         if (snafuDigits[i] in 3L..5L) {
21             snafuDigits[i - 1] = snafuDigits[i - 1] + 1
22             snafuDigits[i] =  snafuDigits[i] - 5L
!!                                                ^ error
23         }
24     }
25     return if (snafuDigits.toList() == this) this else snafuDigits.base5DigitsToSnafuDigits()

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-01-03 13:51:21 UTC
