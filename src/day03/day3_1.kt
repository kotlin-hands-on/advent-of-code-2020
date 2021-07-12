import java.io.File

fun main() {
    var x = 0
    var treeCount = 0
    val lineLength = File("src/day03/input.txt").useLines { it.first().length }
    File("src/day03/input.txt")
        .forEachLine {
            if (it[x % lineLength] == '#')
                treeCount++
            x += 3
        }
    println(treeCount)
}
