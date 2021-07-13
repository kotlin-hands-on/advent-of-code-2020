package day07

import java.io.File

val containedInBag = hashMapOf<String, Collection<String>>()

private const val SHINY_GOLD = "shiny gold"

fun main() {
    buildBagTree()
    println(SHINY_GOLD.childrenCount)
}

private fun buildBagTree() {
    File("src/day07/input.txt")
        .forEachLine { raw ->
            val parentWithChildren = raw
                .replace(Regex("bags?\\.?"), "")
                .split("contain")
            val parent = parentWithChildren[0].trim()
            val children =
                if (!raw.contains("no other"))
                    parentWithChildren[1].split(',').map { it.trim() }
                else setOf()
            containedInBag[parent] = children
        }
}

val digits = "\\d+".toRegex()

private val String.childrenCount: Int
    get() {
        val children = containedInBag.getOrDefault(this, setOf())
        if (children.isEmpty()) return 0
        var total = 0
        for (child in children) {
            val count = digits.findAll(child).first().value.toInt()
            val bag = digits.replace(child, "").trim()
            total += count + count * bag.childrenCount
        }
        return total
    }

