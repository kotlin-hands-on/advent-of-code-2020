package day09

import java.io.File

const val GROUP_SIZE = 25

fun main() {
    val numbers = File("src/day09/input.txt")
        .readLines()
        .map { it.toLong() }

    val invalidNumber = numbers.findInvalidNumber()
        ?: error("All numbers are valid!")
    println(invalidNumber)

    val sublist = numbers.findSublistOfSum(sum = invalidNumber)
    println(sublist.minOf { it } + sublist.maxOf { it })
}

fun List<Long>.hasPairOfSum(sum: Long): Boolean =
    indices.any { i ->
        indices.any { j ->
            i != j && this[i] + this[j] == sum
        }
    }

fun List<Long>.findInvalidNumber(): Long? =
    asSequence()
        .windowed(GROUP_SIZE + 1)
        .firstOrNull {
            val prevGroup = it.dropLast(1)
            !prevGroup.hasPairOfSum(sum = it.last())
        }
        ?.last()

fun List<Long>.findSublistOfSum(sum: Long): List<Long> =
    (2..size).firstNotNullOf { sublistSize ->
        asSequence()
            .windowed(sublistSize)
            .firstOrNull { sublist ->
                sublist.sum() == sum
            }
    }