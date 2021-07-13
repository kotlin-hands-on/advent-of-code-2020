package day15

private fun sequenceFromStarting(vararg input: Int) = sequence {
    val lastIndexes = input.dropLast(1).mapIndexed { index, i -> i to index }.toMap(hashMapOf())
    yieldAll(input.dropLast(1))
    var curIndex = input.lastIndex
    var current = input.last()
    while (true) {
        yield(current)

        val value = when (val prevIndex = lastIndexes[current]) {
            null -> 0
            else -> curIndex - prevIndex
        }
        lastIndexes[current] = curIndex
        current = value
        curIndex++
    }
}

fun main() {
//    println(sequenceFromStarting(1, 20, 8, 12, 0, 14).elementAt(2019))
    println(sequenceFromStarting(1, 20, 8, 12, 0, 14).elementAt(30000000 - 1))
}
