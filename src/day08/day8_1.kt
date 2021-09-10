package day08.one

import java.io.File

val instructions = File("src/day08/input.txt")
    .readLines()
    .map { Instruction(it) }

fun main() {
    println(execute(instructions))
    println(
        mutate(instructions)
            .map { modifiedProgram -> execute(modifiedProgram) }
            .first { state -> state.ip >= instructions.size }
    )
}

data class MachineState(val ip: Int, val acc: Int)

fun execute(instructions: List<Instruction>): MachineState {
    var state = MachineState(0, 0)
    val encounteredIndices = mutableSetOf<Int>()
    while (state.ip < instructions.size) {
        val nextInstruction = instructions[state.ip]
        println("Instruction: $nextInstruction, State: $state" )
        state = nextInstruction.action(state)
        println("=========================> State: $state" )
        if (state.ip in encounteredIndices) return state
        encounteredIndices += state.ip
    }
    println("No loop found – program terminates!")
    return state
}

fun mutate(instructions: List<Instruction>) = sequence<List<Instruction>> {
    for ((index, instruction) in instructions.withIndex()) {
        val newProgram = instructions.toMutableList()
        newProgram[index] = when (instruction) {
            is Jmp -> Nop(instruction.value)
            is Nop -> Jmp(instruction.value)
            is Acc -> continue
        }
        yield(newProgram)
    }
}

sealed class Instruction(val action: (MachineState) -> MachineState)

class Nop(val value: Int) : Instruction({ MachineState(it.ip + 1, it.acc) })

class Acc(val value: Int) : Instruction({ MachineState(it.ip + 1, it.acc + value) })

class Jmp(val value: Int) : Instruction({ MachineState(it.ip + value, it.acc) })

fun Instruction(s: String): Instruction {
    val (instr, immediate) = s.split(" ")
    val value = immediate.toInt()
    return when (instr) {
        "nop" -> Nop(value)
        "acc" -> Acc(value)
        "jmp" -> Jmp(value)
        else -> error("Invalid opcode!")
    }
}