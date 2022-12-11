package org.aarbizu.aoc2022.dayTen

val signalPoints = listOf(20, 60, 100, 140, 180, 220)

class SignalStrength {
    private val registers = mutableMapOf<String, Register>()
    private var clock = Clock()
    private var crt = CharArray(640) { 0.toChar() }

    init {
        registers["x"] = Register("x", 1)
    }

    fun runProgram(instructions: List<String>): SystemState {
        val systemState = SystemState(registers, clock, crt)

        instructions.map {
            val cmd = when (val nextCmd = it) {
                "noop" -> NoOp()
                in Regex("addx [\\d-]+") -> parseAddCmd(nextCmd)
                else -> NullCmd
            }
            cmd.process(systemState)
        }

        return systemState
    }

    private fun parseAddCmd(str: String): Instruction {
        val (regName, arg) = str.split(" ")
        val registerId = regName.last().toString()
        return Add(register = registers[registerId]!!, value = arg.toInt())
    }

    operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)
}

data class SystemState(var registers: Map<String, Register>, val clock: Clock, val crt: CharArray) {
    private var crtPtr = 0
    private fun spriteVisible(): Boolean {
        val xValue = registers["x"]!!.value
        return (crtPtr % 40) in listOf(xValue - 1, xValue, xValue + 1)
    }

    fun writePixel() {
        if (spriteVisible()) {
            crt[crtPtr++] = '#'
        } else {
            crt[crtPtr++] = '.'
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemState

        if (registers != other.registers) return false
        if (clock != other.clock) return false
        if (!crt.contentEquals(other.crt)) return false
        if (crtPtr != other.crtPtr) return false

        return true
    }

    override fun hashCode(): Int {
        var result = registers.hashCode()
        result = 31 * result + clock.hashCode()
        result = 31 * result + crt.contentHashCode()
        result = 31 * result + crtPtr
        return result
    }
}

data class Clock(var tick: Int = 1)
data class Register(val label: String, var value: Int) {
    var log = mutableListOf<Pair<Int, Int>>()
}

sealed interface Instruction {
    fun process(state: SystemState)
}

class Add(val register: Register, val value: Int) : Instruction {
    override fun process(state: SystemState) {
        register.log.add(Pair(state.clock.tick, register.value))
        state.writePixel()
        state.clock.tick++
        register.log.add(Pair(state.clock.tick, register.value))
        state.writePixel()
        state.clock.tick++
        register.value = register.value + value
    }
}

class NoOp : Instruction {
    override fun process(state: SystemState) {
        state.registers.values.map { reg -> reg.log.add(Pair(state.clock.tick, reg.value)) }
        state.writePixel()
        state.clock.tick++
    }
}

object NullCmd : Instruction {
    override fun process(state: SystemState) {
        throw Exception("shouldn't have processed a null cmd!")
    }
}
