package day1

import java.io.File

fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    // Map: sum - x -> x
    val complements = associateBy { sum - it }
    return mapNotNull { number ->
        val complement = complements[number]
        if (complement != null) Pair(number, complement) else null
    }.firstOrNull()
}

fun List<Int>.findTripleOfSum(): Triple<Int, Int, Int>? {
    // Map: x -> (y, z) where y + z = 2020 - x
    val complementPairs: Map<Int, Pair<Int, Int>?> =
        associateWith { findPairOfSum(2020 - it) }
    val (x, pair) = complementPairs.entries
        .firstOrNull { (_, pair) -> pair != null }
        ?: return null
    return Triple(x, pair!!.first, pair.second)
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
    println(triple?.let { (x, y, z) -> x * y * z} )
}
