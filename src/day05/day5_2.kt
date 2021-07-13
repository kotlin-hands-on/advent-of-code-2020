package day05

import java.io.File

fun main() {
    val maxOrNull = File("src/day05/input.txt")
        .useLines { lines ->
            lines
                .map(::guess)
                .sorted()
                .windowed(2, 1, partialWindows = false)
                .first() { (left, right) -> right - left == 2 }
        }

    println(maxOrNull[1] - 1)
}

private fun guess(str: String): Int {
    var rows = (0..127).toList()
    var seats = (0..7).toList()
    for (code in str) {
        when (code) {
            'F' -> rows = rows.subList(0, rows.size / 2)
            'B' -> rows = rows.subList(rows.size / 2, rows.size)
            'L' -> seats = seats.subList(0, seats.size / 2)
            'R' -> seats = seats.subList(seats.size / 2, seats.size)
        }
    }
    return rows.single() * 8 + seats.single()
}
