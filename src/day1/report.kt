package day1

import java.io.File

fun main() {
    val numbers = File("src/day1/input.txt")
        .readLines()
        .map(String::toLong)
    for (first in numbers.indices) {
        for (second in (first + 1) until numbers.size) {
            for (third in (second + 1) until numbers.size) {
                if (numbers[first] + numbers[second] + number[third] == 2020L) {
                    println(numbers[first] * numbers[second] * number[third])
                    return
                }
            }
        }
    }
}
