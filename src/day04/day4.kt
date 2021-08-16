package day04

import java.io.File

val newLine = System.lineSeparator()

val passports = File("src/day04/input.txt")
    .readText()
    .trim()
    .split("$newLine$newLine")
    .map { Passport.fromString(it) }


class Passport(private val map: Map<String, String>) {

    companion object {
        fun fromString(s: String): Passport {
            val fieldsAndValues = s.split(" ", newLine)
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

    private val fieldValidationRules: Map<String, (String) -> Boolean> = mapOf(
        "byr" to { it.length == 4 && it.toIntOrNull() in 1920..2002 },
        "iyr" to { it.length == 4 && it.toIntOrNull() in 2010..2020 },
        "eyr" to { it.length == 4 && it.toIntOrNull() in 2020..2030 },
        "pid" to { it.length == 9 && it.toIntOrNull() != null },
        "ecl" to { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
        "hgt" to {
            when (it.takeLast(2)) {
                "cm" -> it.removeSuffix("cm").toIntOrNull() in 150..193
                "in" -> it.removeSuffix("in").toIntOrNull() in 59..76
                else -> false
            }
        },
        "hcl" to { it matches """#[0-9a-f]{6}""".toRegex() },
    )

    fun hasValidValues(): Boolean {
        return fieldValidationRules.all { (key, validator) ->
            val field = map[key] ?: return@all false
            validator(field)
        }
    }
}

fun String.isDigits(n: Int): Boolean {
    return this.length == n && this.all { it.isDigit() }
}

fun main() {
    partOne()
    partTwo()
}

fun partOne() {
    println(passports.count(Passport::hasAllRequiredFields))
}

fun partTwo() {
    println(passports.count(Passport::hasValidValues))
}