package day09.findingSublistOfSum

fun main() {
    val numbers = listOf<Long>(
        35, 20, 15, 25, 47, 40, 62,
        55, 65, 95, 102, 117, 150, 182,
        127, 219, 299, 277, 309, 576
    )
    println(numbers.findSublistOfSumV1(127)) // [15, 25, 47, 40]
    println(numbers.findSublistOfSumV2(127)) // [15, 25, 47, 40]
}

fun List<Long>.findSublistOfSumV1(sum: Long): List<Long>? {
    for (fromIndex in indices) {
        for (toIndex in (fromIndex + 1)..size) {
            val subList = subList(fromIndex, toIndex)
            if (subList.sum() == sum) {
                return subList
            }
        }
    }
    return null
}

fun List<Long>.findSublistOfSumV2(sum: Long): List<Long>? =
    indices.firstNotNullOfOrNull { fromIndex ->
        ((fromIndex + 1)..size)
            .firstNotNullOfOrNull { toIndex ->
                val subList = subList(fromIndex, toIndex)
                if (subList.sum() == sum) subList else null
            }
    }