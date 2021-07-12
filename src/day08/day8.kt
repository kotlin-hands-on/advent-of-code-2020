package day08

import java.io.File

val program = File("src/day08/input.txt")
    .readLines()
    .map {
        val (instr, value) = it.split(" ")
        Instruction(instr, value.toInt())
    }

data class Instruction(val opcode: String, val value: Int)

data class ComputationResult(val accumulator: Int, val terminated: Boolean)

fun executeCode(program: List<Instruction>): ComputationResult {
    var ip = 0
    var acc = 0
    val encounteredIndices = mutableSetOf<Int>()

    while (ip < program.size) {
        if (!encounteredIndices.add(ip)) return ComputationResult(acc, false)

        val currInst = program[ip]

        when (currInst.opcode) {
            "nop" -> ip++
            "jmp" -> ip += currInst.value
            "acc" -> {
                acc += currInst.value
                ip++
            }
        }
    }
    return ComputationResult(acc, true)
}

fun partOne() {
    println(executeCode(program))
}

fun partTwo() {
    for ((index, targetInstruction) in program.withIndex()) {
        val newProgram = program.toMutableList()
        newProgram[index] = when (targetInstruction.opcode) {
            "jmp" -> targetInstruction.copy(opcode = "nop")
            "nop" -> targetInstruction.copy(opcode = "jmp")
            else -> continue
        }
        val result = executeCode(newProgram)
        if (result.terminated) {
            println(result)
            return
        }
    }
}

fun main() {
    partOne()
    partTwo()
}