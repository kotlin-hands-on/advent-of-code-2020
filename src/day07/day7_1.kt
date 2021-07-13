package day07

import java.io.File

val contained = hashMapOf<String, Set<String>>()

fun main() {
    buildBagTree()

    var known = setOf("shiny gold")
    var next = setOf("shiny gold") + contained["shiny gold"]!!
    while (true) {
        val toFind = next - known
        if (toFind.isEmpty()) break
        known = known + next
        next = toFind.mapNotNull { contained[it] }.flatten().toSet()
    }
    println(known - "shiny gold")
    println((known - "shiny gold").size)
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
        contained.compute(child) { _, current ->
            if (current == null) setOf(parent) else current + parent
        }
    }
}
