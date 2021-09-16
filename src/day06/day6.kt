package day06

import java.io.File

val newLine = System.lineSeparator()

fun main() {

    val groups: List<String> = File("src/day06/input.txt")
        .readText()
        .trim()
        .split("$newLine$newLine")

    val firstAnswer = groups.sumOf {
        it.replace(newLine, "").toSet().size
    }

    println("First answer: $firstAnswer") // 6273

    val secondAnswer = transformAndReduce(groups, Set<Char>::intersect)

    println("Second answer: $secondAnswer") // 3254
}

/**
 * This is the generalized function that accepts the set operation as a parameter.
 *
 * You can use it as follows:
 *
 * val groups: List<String> = File("src/day06/input.txt")
 *     .readText()
 *     .trim()
 *     .split("$newLine$newLine")
 *
 * val firstAnswer = transformAndReduce(groups, Set<Char>::union)
 * val secondAnswer = transformAndReduce(groups, Set<Char>::intersect)
 *
 * println("First answer: $firstAnswer")
 * println("Second answer: $secondAnswer")
 */
private fun transformAndReduce(groups: List<String>, operation: (Set<Char>, Set<Char>) -> Set<Char>) =
    groups.map { lines ->
        lines.split(newLine).map(String::toSet)
    }.sumOf { characters ->
        characters.reduce { a, b -> operation(a , b) }.count()
    }

