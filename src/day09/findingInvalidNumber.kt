package day09.findingInvalidNumber

const val GROUP_SIZE = 5

fun main() {
    // In this example, almost every number
    // is the sum of two of the previous 5 numbers;
    // the only number that does not follow this rule is 127.
    val numbers = listOf<Long>(
        35, 20, 15, 25, 47, 40, 62,
        55, 65, 95, 102, 117, 150, 182,
        127, 219, 299, 277, 309, 576)
    println(numbers.findInvalidNumberV1()) // 127
    println(numbers.findInvalidNumberV2()) // 127
}

fun List<Long>.findInvalidNumberV1(): Long? {
    for (index in (GROUP_SIZE + 1)..lastIndex) {
        val prevGroup = subList(index - GROUP_SIZE, index)
        if (!prevGroup.hasPairOfSum(sum = this[index])) {
            return this[index]
        }
    }
    return null
}

fun List<Long>.findInvalidNumberV2(): Long? =
    ((GROUP_SIZE + 1)..lastIndex)
        .firstOrNull { index ->
            val prevGroup = subList(index - GROUP_SIZE, index)
            !prevGroup.hasPairOfSum(sum = this[index])
        }
        ?.let { this[it] }

fun List<Long>.hasPairOfSum(sum: Long): Boolean =
    indices.any { i ->
        indices.any { j ->
            i != j && this[i] + this[j] == sum
        }
    }