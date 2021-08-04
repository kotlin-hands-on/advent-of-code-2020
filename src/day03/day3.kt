package day03

import java.io.File

private fun solve(field: List<String>, vector: Pair<Int, Int>): Int {
	val (dx, dy) = vector
	val width = field[0].length
	return field.indices.count { y ->
		y % dy == 0 && field[y][y * dx / dy % width] == '#'
	}
}

fun main() {
	val field = File("src/day03/input.txt").readLines()
	println(solve(field, 3 to 1))
	val vectors = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
	println(vectors.map { solve(field, it).toBigInteger() }.reduce { a, b -> a * b })
}
