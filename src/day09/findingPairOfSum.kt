package day09.findingPairOfSum

fun main() {
    val numbers = listOf(1L, 24, 25, 10, 13)
    println(numbers.hasPairOfSumV1(26)) // true; 26 = 1 + 25
    println(numbers.hasPairOfSumV1(49)) // true; 49 = 24 + 25
    println(numbers.hasPairOfSumV1(100)) // false

    // This is wrong:
    println(numbers.hasPairOfSumV1(50)) // true; 50 = 25 * 2

    // This is correct:
    println(numbers.hasPairOfSumV2(50)) // false
}

fun List<Long>.hasPairOfSumV1(sum: Long): Boolean {
    forEach { first ->
        forEach { second ->
            if (first + second == sum) return true
        }
    }
    return false
}

fun List<Long>.hasPairOfSumV2(sum: Long): Boolean {
    forEachIndexed { i, first ->
        forEachIndexed { j, second ->
            if (i != j && first + second == sum) return true
        }
    }
    return false
}