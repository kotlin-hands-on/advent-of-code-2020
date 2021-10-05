package day08.one

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

val instructions = File("src/day08/input.txt")
    .readLines()
    .map { Instruction(it) }

@OptIn(ExperimentalTime::class)
fun main() {
    println(measureTimedValue { execute(instructions) })
    println(measureTimedValue { executeMutably(instructions) })
    println(
        measureTimedValue {
            mutate(instructions)
                .map { modifiedProgram -> execute(modifiedProgram) }
                .first { state -> state.ip !in instructions.indices }
        }
    )
    println(
        measureTimedValue {
            mutate(instructions)
                .map { modifiedProgram -> executeMutably(modifiedProgram) }
                .first { state -> state.ip >= instructions.size }
        }
    )
}

data class MachineState(val ip: Int, val acc: Int)

fun execute(instructions: List<Instruction>): MachineState {
    var state = MachineState(0, 0)
    val encounteredIndices = mutableSetOf<Int>()
    while (state.ip in instructions.indices) {
        val nextInstruction = instructions[state.ip]
        state = nextInstruction.action(state)
        if (state.ip in encounteredIndices) return state
        encounteredIndices += state.ip
    }
    println("No loop found – program terminates!")
    return state
}

fun executeMutably(instructions: List<Instruction>): MachineState {
    var ip: Int = 0
    var acc: Int = 0
    val encounteredIndices = mutableSetOf<Int>()
    while (ip in instructions.indices) {
        when (val nextInstr = instructions[ip]) {
            is Acc -> {
                ip++; acc += nextInstr.value
            }
            is Jmp -> ip += nextInstr.value
            is Nop -> ip++
        }
        if (ip in encounteredIndices) return MachineState(ip, acc)
        encounteredIndices += ip
    }
    return MachineState(ip, acc)
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