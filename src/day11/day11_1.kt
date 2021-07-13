package day11

import java.io.File

data class Point(val x: Int, val y: Int)

private const val FLOOR = '.'
private const val OCCUPIED = '#'
private const val FREE = 'L'

private var state = File("src/day11/input.txt")
    .useLines { lines ->
        lines
            .flatMapIndexed { lineIndex, line ->
                line
                    .asSequence()
                    .mapIndexed { charIndex, char ->
                        Point(lineIndex, charIndex) to char
                    }
                    .filter { it.second != FLOOR }
            }
            .toMap()
    }


fun main() {
    while (true) {
        val next = tick()
        if (next == state) {
            println(state.values.count { it == OCCUPIED })
            break
        } else state = next
    }
}


private fun tick(): Map<Point, Char> = state
    .map { (seat, seatState) ->
        val neighborStates = adjacent(seat)
        seat to
                if (seatState == FREE && neighborStates.none { it == OCCUPIED }) OCCUPIED
                else if (seatState == OCCUPIED && neighborStates.count { it == OCCUPIED } >= 4) FREE
                else seatState
    }
    .toMap()

private fun adjacent(seat: Point): Sequence<Char> = sequence {
    for (i in seat.x - 1..seat.x + 1)
        for (j in seat.y - 1..seat.y + 1)
            yield(Point(i, j))
}
    .filterNot { it == seat }
    .mapNotNull { state[it] }