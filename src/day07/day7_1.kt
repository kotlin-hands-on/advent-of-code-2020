package day07

import java.io.File

val containersOfBag = hashMapOf<String, Set<String>>()

private const val SHINY_GOLD = "shiny gold"

fun main() {
    buildBagTree()

    var known = setOf(SHINY_GOLD)
    var next = setOf(SHINY_GOLD) + containersOfBag[SHINY_GOLD]!!
    while (true) {
        val toFind = next - known
        if (toFind.isEmpty()) break
        known = known + next
        next = toFind.mapNotNull { containersOfBag[it] }.flatten().toSet()
    }
    println(known - SHINY_GOLD)
    println((known - SHINY_GOLD).size)
}

private fun buildBagTree() {
    File("src/day07/input.txt")
        .forEachLine {
            val parentWithChildren = it
                .replace(Regex("\\d+"), "")
                .replace(Regex("bags?\\.?"), "")
                .split("contain")
            val parent = parentWithChildren[0].trim()
            val children = parentWithChildren[1].split(',').map { z -> z.trim() }.toSet()
            addBag(parent, children)
        }
}

private fun addBag(parent: String, children: Set<String>) {
    for (child in children) {
        containersOfBag.compute(child) { _, current ->
            if (current == null) setOf(parent) else current + parent
        }
    }
}
