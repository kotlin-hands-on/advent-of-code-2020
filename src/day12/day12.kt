package day12

import java.io.File
import kotlin.math.absoluteValue

val instructions = File("src/day12/input.txt")
    .readLines()
    .map {
        it.first() to it.drop(1).toInt()
    }

enum class Heading(val deg: Int, val xOff: Int, val yOff: Int) {
    NORTH(0, 0, 1),
    EAST(90, 1, 0),
    SOUTH(180, 0, -1),
    WEST(270, -1, 0);

    fun turn(amount: Int) =
        values().first { it.deg == (deg + amount).mod(360) }
}

fun partOne() {
    var x = 0
    var y = 0
    var heading = Heading.EAST
    for ((instruction, value) in instructions) {
        when (instruction) {
            'N' -> y += value
            'S' -> y -= value
            'E' -> x += value
            'W' -> x -= value
            'L' -> heading = heading.turn(-value)
            'R' -> heading = heading.turn(value)
            'F' -> {
                x += heading.xOff * value
                y += heading.yOff * value
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}

fun partTwo() {
    var x = 0
    var y = 0
    var wayX = 10
    var wayY = 1
    for ((inst, value) in instructions) {
        when (inst) {
            'N' -> wayY += value
            'S' -> wayY -= value
            'E' -> wayX += value
            'W' -> wayX -= value
            //counter clockwise rotation
            'L' -> repeat(value / 90) {
                val x2 = -wayY
                val y2 = wayX
                wayX = x2
                wayY = y2
            }
            //clockwise rotation
            'R' -> repeat(value / 90) {
                val x2 = wayY
                val y2 = -wayX
                wayX = x2
                wayY = y2
            }
            'F' -> {
                x += wayX * value
                y += wayY * value
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}

fun main() {
    partOne()
    partTwo()
}