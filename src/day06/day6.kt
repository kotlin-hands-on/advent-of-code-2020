package day06

import java.io.File


fun main() {

    val newLine = System.lineSeparator()

    val groups: List<String> = File("src/day06/input.txt")
        .readText()
        .trim()
        .split("$newLine$newLine")

    val firstAnswer = groups.sumOf {
        it.replace(newLine, "").toSet().size
    }

    println("First answer: $firstAnswer")

    val secondAnswer = groups.map { it.split(newLine).map { s -> s.toSet() } }
        .fold(0) { sum, group -> sum + group.fold(group.first()) { a, b -> a intersect b }.count() }

    println("Second answer: $secondAnswer")
}
