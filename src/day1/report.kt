package day1

import java.io.File

fun main() {
    val numbers = File("src/day1/input.txt")
        .readLines()
        .map(String::toInt)
    for (first in numbers) {
        for (second in numbers) {
            for (third in numbers) {
                if (first + second + third == 2020) {
                    println("$first, $second, $third")
                    println(first * second * third)
                    return
                }
            }
        }
    }

    /* FP solution */

    numbers.asSequence()
        .flatMapIndexed { idx, first ->
            numbers.subList(idx + 1, numbers.size).asSequence().flatMapIndexed { idx2, second ->
                numbers.subList(idx + idx2 + 2, numbers.size).asSequence().map { third ->
                    Triple(first, second, third)
                }
            }
        }
        .filter { it.first + it.second + it.third == 2020L }
        .onEach { println(it.first * it.second * it.third) }
        .first()
    
    /* with generators */

    sequence { 
        for (first in numbers.indices)
            for (second in (first+1) until numbers.size)
                for (third in (second+1) until numbers.size)
                    yield(Triple(numbers[first], numbers[second], numbers[third]))
    }
        .filter { it.first + it.second + it.third == 2020L }
        .map { it.first * it.second * it.third }
        .onEach { println(it) }
        .first()

}

