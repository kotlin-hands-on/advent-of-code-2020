package day06

import java.io.File

val newLine = System.lineSeparator()

fun main() {

    val answer = File("src/day06/input.txt")
        .readText()
        .trim()
        .split("$newLine$newLine").sumOf {
            it.replace(newLine, "").toSet().size
        }

    println(answer)

}