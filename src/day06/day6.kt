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

    println("First answer: $firstAnswer") // 6273

    val secondAnswer = groups.map { lines ->
        lines.split(newLine).map(String::toSet)
    }.sumOf { characters ->
        characters.reduce { a, b -> a intersect b }.count()
    }

    println("Second answer: $secondAnswer") // 3254
}
