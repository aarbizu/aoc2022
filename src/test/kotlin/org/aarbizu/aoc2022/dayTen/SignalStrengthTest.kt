package org.aarbizu.aoc2022.dayTen

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class SignalStrengthTest {

    @Test
    fun `sample data`() {
        val finalState = SignalStrength().runProgram(sample.lines())
        assertThat(finalState.registers["x"]?.value).isEqualTo(-1)
        assertThat(finalState.clock.tick).isEqualTo(6) // fifth cycle is completed
    }

    @Test
    fun `sample data two`() {
        val finalState = SignalStrength().runProgram(sample2.lines())
        assertThat(finalState.registers["x"]!!.log).isNotEmpty()
        val signalSum = finalState.registers["x"]!!.log
            .filter { it.first in signalPoints }
            .sumOf { it.first * it.second }
        assertThat(signalSum).isEqualTo(13140).also { println("signal sum is $signalSum") }

        println(finalState.crt.slice(0..39).joinToString(""))
        println(finalState.crt.slice(40..79).joinToString(""))
        println(finalState.crt.slice(80..119).joinToString(""))
        println(finalState.crt.slice(120..159).joinToString(""))
        println(finalState.crt.slice(160..199).joinToString(""))
        println(finalState.crt.slice(200..239).joinToString(""))
    }

    @Test
    fun `part one`() {
        val program = File("$INPUTS_DIR/d10-p1.txt").readLines()
        val finalState = SignalStrength().runProgram(program)
        val signalSum = finalState.registers["x"]!!.log
            .filter { it.first in signalPoints }
            .sumOf { it.first * it.second }
        assertThat(signalSum).isEqualTo(12740).also { println("Signal sum is $signalSum") }

        println(finalState.crt.slice(0..39).joinToString(""))
        println(finalState.crt.slice(40..79).joinToString(""))
        println(finalState.crt.slice(80..119).joinToString(""))
        println(finalState.crt.slice(120..159).joinToString(""))
        println(finalState.crt.slice(160..199).joinToString(""))
        println(finalState.crt.slice(200..239).joinToString(""))
    }

    val sample = """
        noop
        addx 3
        addx -5
    """.trimIndent()

    val sample2 = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()
}