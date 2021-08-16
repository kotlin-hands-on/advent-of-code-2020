package day04.one

import java.io.File

val newLine: String = System.lineSeparator()

val passports = File("src/day04/input.txt")
    .readText()
    .trim()
    .split("$newLine$newLine")
    .map { Passport(it) }


class Passport(private val text: String) {
    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*"cid"*/)

    fun hasAllRequiredFields(): Boolean {
        val fieldsWithValues = text.split(" ", newLine)
        val fieldNames = fieldsWithValues.map { it.substringBefore(":") }
        return fieldNames.containsAll(requiredFields)
    }
}

fun main() {
    println(passports.count(Passport::hasAllRequiredFields))
}