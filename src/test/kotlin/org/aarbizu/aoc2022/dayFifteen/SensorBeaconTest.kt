package org.aarbizu.aoc2022.dayFifteen

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.aarbizu.aoc2022.util.collapse
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.system.measureTimeMillis

class SensorBeaconTest {
    private val sample = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent()

    @Test
    fun parseInput() {
        val s = SensorBeacon(sample.lines())
        s.printGrid()
    }

    @Test
    fun `playing with coverage`() {
        measureTimeMillis {
            val sensorBeacon = SensorBeacon(sample.lines())
            sensorBeacon.computeSensorCoverage()
            sensorBeacon.printGrid()
        }.also { println("time (old): $it ms") }

        measureTimeMillis {
            val sensorBeacon = SensorBeacon(sample.lines())
            sensorBeacon.computeSensorCoverageBetter(10) { it in listOf('S','B') }
            sensorBeacon.printGrid()
        }.also { println("time (new): $it ms") }
    }

    @Test
    fun sampleProblem() {
        val sensorBeacon = SensorBeacon(sample.lines())
        val count = sensorBeacon.computeSensorCoverageBetter(10) { it in listOf('S','B') }
        assertThat(count).isEqualTo(26).also { println("at row 10 $count positions can't have a beacon") }
    }

    @Test
    fun `part one`() {
        val sensorBeacon = SensorBeacon(File("$INPUTS_DIR/d15-p1.txt").readLines())
        val count = sensorBeacon.computeSensorCoverageBetter(2000000) { it in listOf('S','B') }
        assertThat(count).isEqualTo(5108096).also { println("at row 2000000 $count positions can't have a beacon") }
    }

    @Test
    fun `part two sample`() {
        val sensorBeacon = SensorBeacon(sample.lines())
        sensorBeacon.findHiddenBeacon(20)
    }

    @Test
    fun `part two`() {
        val sensorBeacon = SensorBeacon(File("$INPUTS_DIR/d15-p1.txt").readLines())
        sensorBeacon.findHiddenBeaconBetter(4000000)
    }

    @Test
    fun `point stuff`() {
        val line = Point(8,7).lineTo(Point(-2, 0))
        val grid = SensorGrid(-10, 10, -10, 10)

        line.map { grid.addSensor(it) }

        grid.print()

        val ranges = listOf((1 .. 10), (-7 .. 25), (4 .. 18), (-5 .. 37))

        val reduced = ranges.collapse()
    }

    @Test
    fun `part two faster sample`() {
        val sensorBeacon = SensorBeacon(sample.lines())
        sensorBeacon.findHiddenBeaconBetter(20)
    }
}
