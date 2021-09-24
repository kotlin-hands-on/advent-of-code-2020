package day07

import java.io.File

private const val SHINY_GOLD = "shiny gold"

fun main() {
    val rules = buildBagTree()
    val result = rules.getChildrenCountBFS(SHINY_GOLD)
    println(result)
}

val digits = "\\d+".toRegex()

private fun Map<Color, Rule>.getChildrenCountBFS(color: Color): Int {
    val children = getOrDefault(color, setOf())
    if (children.isEmpty()) return 0
    var total = 0
    for (child in children) {
        val count = digits.findAll(child).first().value.toInt()
        val bag = digits.replace(child, "").trim()
        total += count + count * getChildrenCountBFS(bag)
    }
    return total
}

private fun buildBagTree(): Map<Color, Rule> {
    val rules = hashMapOf<Color, Rule>()
    File("src/day07/input.txt")
        .forEachLine { line ->
            val (parent, allChildren) = line.replace(Regex("bags?\\.?"), "")
                .split("contain")
                .map { it.trim() }
            val rule =
                if (allChildren.contains("no other")) emptySet()
                else allChildren.split(',').map { it.trim() }.toSet()
            rules[parent] = rule
        }
    return rules
}
