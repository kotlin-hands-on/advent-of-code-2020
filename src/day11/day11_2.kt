package day11

import java.io.File

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
private val width = File("src/day11/input.txt").useLines { it.first().length }
private val height = File("src/day11/input.txt").useLines { it.count() }

fun main() {
    while (true) {
        val nextState = tick()
        if (nextState == state) {
            println(state.values.count { it == OCCUPIED })
            break
        } else state = nextState
    }
}

private fun tick(): Map<Point, Char> =
    state
        .map { (seat, seatState) ->
            val neighborStates = adjacent(seat)
            seat to
                    if (seatState == FREE && neighborStates.none { it == OCCUPIED }) OCCUPIED
                    else if (seatState == OCCUPIED && neighborStates.count { it == OCCUPIED } >= 5) FREE
                    else seatState
        }
        .toMap()

private fun adjacent(seat: Point): Sequence<Char> = sequenceOf(
    searchLeft(seat),
    searchRight(seat),
    searchTop(seat),
    searchBottom(seat),
    searchTopLeft(seat),
    searchTopRight(seat),
    searchBottomLeft(seat),
    searchBottomRight(seat),
)
    .map { it.firstNotNullOfOrNull { point -> state[point] } }
    .filterNotNull()

private fun searchLeft(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x - step < 0) break
        else {
            yield(Point(seat.x - step, seat.y))
            step++
        }
    }
}

private fun searchTop(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.y - step < 0) break
        else {
            yield(Point(seat.x, seat.y - step))
            step++
        }
    }
}

private fun searchRight(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x + step > width) break
        else {
            yield(Point(seat.x + step, seat.y))
            step++
        }
    }
}

private fun searchBottom(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.y + step > height) break
        else {
            yield(Point(seat.x, seat.y + step))
            step++
        }
    }
}

private fun searchTopLeft(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x - step < 0 || seat.y - step < 0) break
        else {
            yield(Point(seat.x - step, seat.y - step))
            step++
        }
    }
}

private fun searchTopRight(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x + step > width || seat.y - step < 0) break
        else {
            yield(Point(seat.x + step, seat.y - step))
            step++
        }
    }
}

private fun searchBottomLeft(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x - step < 0 || seat.y + step > height) break
        else {
            yield(Point(seat.x - step, seat.y + step))
            step++
        }
    }
}

private fun searchBottomRight(seat: Point) = sequence {
    var step = 1
    while (true) {
        if (seat.x + step > width || seat.y + step > height) break
        else {
            yield(Point(seat.x + step, seat.y + step))
            step++
        }
    }
}
