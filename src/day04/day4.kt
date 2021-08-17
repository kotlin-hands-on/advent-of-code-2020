package day04

import java.io.File

val passports = File("src/day04/input.txt")
    .readText()
    .trim()
    .split("\n\n", "\r\n\r\n")
    .map { Passport.fromString(it) }


class Passport(private val map: Map<String, String>) {

    companion object {
        fun fromString(s: String): Passport {
            val fieldsAndValues = s.split(" ", "\n", "\r\n")
            val map = fieldsAndValues.associate {
                val (key, value) = it.split(":")
                key to value
            }
            return Passport(map)
        }
    }

    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*"cid"*/)

    fun hasAllRequiredFields(): Boolean {
        return map.keys.containsAll(requiredFields)
    }

    fun hasValidValues(): Boolean =
        map.all { (key, value) ->
            when (key) {
                "byr" -> value.length == 4 && value.toIntOrNull() in 1920..2002
                "iyr" -> value.length == 4 && value.toIntOrNull() in 2010..2020
                "eyr" -> value.length == 4 && value.toIntOrNull() in 2020..2030
                "pid" -> value.length == 9 && value.all(Char::isDigit)
                "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "hgt" -> when (value.takeLast(2)) {
                    "cm" -> value.removeSuffix("cm").toIntOrNull() in 150..193
                    "in" -> value.removeSuffix("in").toIntOrNull() in 59..76
                    else -> false
                }
                "hcl" -> value matches """#[0-9a-f]{6}""".toRegex()
                else -> true
            }
        }
}

fun main() {
    partOne()
    partTwo()
}

fun partOne() {
    println(passports.count(Passport::hasAllRequiredFields))
}

fun partTwo() {
    println(passports.count { it.hasAllRequiredFields() && it.hasValidValues() })
}