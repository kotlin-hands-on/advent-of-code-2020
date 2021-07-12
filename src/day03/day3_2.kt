import java.io.File

fun main() {
    val lineLength = File("src/day03/input.txt").useLines { it.first().length }
    val result =
        listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
            .map { (moveRight, moveDown) ->
                var x = 0
                var counter = 0
                File("src/day03/input.txt")
                    .bufferedReader()
                    .lineSequence()
                    .forEachIndexed { index, line ->
                        if (moveDown == 2 && index % 2 == 1) return@forEachIndexed
                        if (line[x % lineLength] == '#')
                            counter++
                        x += moveRight
                    }
                counter.toLong().also { println(it) }
            }
            .reduce { a, b -> a * b }
    println(result)
}

//223
//58
//105
//35