package day1

import java.io.File

fun main() {
    val numbers = File("src/day1/input.txt")
        .readLines()
        .map(String::toLong)
    for (first in numbers) {
        for (second in numbers) {
            for (third in numbers) {
                if (first + second + third == 2020L) {
                    println(first * second * third)
                    return
                }
            }
        }
    }
}