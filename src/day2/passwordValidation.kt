package day2

import java.io.File

fun main() {
    val passwords = File("src/day2/input.txt")
        .readLines()
        .map(PasswordWithPolicy::parse)
    println(passwords.count { it.validatePartOne() })
    println(passwords.count { it.validatePartTwo() })
}

data class PasswordWithPolicy(
    val password: String,
    val range: IntRange,
    val letter: Char
) {
    fun validatePartOne() =
        password.count { it == letter } in range

    fun validatePartTwo() =
        (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)

    companion object {
        fun parse(line: String) = PasswordWithPolicy(
            password = line.substringAfter(": "),
            letter = line.substringAfter(" ").substringBefore(":").single(),
            range = line.substringBefore(" ").let {
                val (start, end) = it.split("-")
                start.toInt()..end.toInt()
            },
        )

        private val regex = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")
        fun parseUsingRegex(line: String): PasswordWithPolicy =
            regex.matchEntire(line)!!
                .destructured
                .let { (start, end, letter, password) ->
                    PasswordWithPolicy(password, start.toInt()..end.toInt(), letter.single())
                }
    }
}
