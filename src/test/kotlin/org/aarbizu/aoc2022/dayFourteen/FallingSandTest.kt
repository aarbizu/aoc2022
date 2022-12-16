package org.aarbizu.aoc2022.dayFourteen

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource
import kotlin.time.measureTime

class FallingSandTest {
    val sample = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    @Test
    fun `test sample`() {
        val falling = FallingSand(sample.lines())
        falling.sand()
        falling.cavern.print()
    }

    @Test
    fun `test building cavern from sample`() {
        val falling = FallingSand(sample.lines())
        falling.sand()
        println(falling.cavern.atRest())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `part one`() {
        val falling = FallingSand(File("$INPUTS_DIR/d14-p1.txt").readLines())

        TimeSource.Monotonic.measureTime {
            falling.sand()
        }.let { println("took ${it.toDouble(DurationUnit.MILLISECONDS)} ms") }

        val atRest = falling.cavern.atRest()
        assertThat(atRest).isEqualTo(799).also { println(atRest) }
    }

    @Test
    fun `sample with floor`() {
        val falling = FallingSand(sample.lines())
        falling.sandWithFloor()
        falling.cavern.print(true)
        println(falling.cavern.atRest())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `part two`() {
        val falling = FallingSand(File("$INPUTS_DIR/d14-p1.txt").readLines())
        TimeSource.Monotonic.measureTime {
            falling.sandWithFloor()
        }.let { println("took ${it.toDouble(DurationUnit.MILLISECONDS)} ms") }

        val atRest = falling.cavern.atRest()
        println("sand = $atRest")
    }
}
