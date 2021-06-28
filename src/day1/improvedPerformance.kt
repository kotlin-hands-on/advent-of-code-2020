package day1

import java.io.File

fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    // Map: sum - x -> x
    val complements = associateBy { sum - it }
    return firstNotNullOfOrNull { number ->
        val complement = complements[number]
        if (complement != null) Pair(number, complement) else null
    }
}

fun List<Int>.findTripleOfSum(): Triple<Int, Int, Int>? =
    firstNotNullOfOrNull { x ->
        findPairOfSum(2020 - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }

fun main() {
    val numbers = File("src/day1/input.txt")
        .readLines()
        .map { it.toInt() }
    val pair = numbers.findPairOfSum(2020)
    println(pair)
    println(pair?.let { (a, b) -> a * b })

    val triple = numbers.findTripleOfSum()
    println(triple)
    println(triple?.let { (x, y, z) -> x * y * z })
}
