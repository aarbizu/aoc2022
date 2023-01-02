package org.aarbizu.aoc2022.dayFifteen

import org.aarbizu.aoc2022.util.SimpleGrid
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class SensorBeacon(input: List<String>) {
    private val inputRegex = """
        ^Sensor at x=([\d-]+), y=([\d-]+): closest beacon is at x=([\d-]+), y=([\d-]+)
    """.trimIndent().toRegex()

    var locations = input.map {
        val (sx, sy, bx, by) = inputRegex.find(it)!!.destructured
        Pair(Point(sx.toInt(), sy.toInt()), Point(bx.toInt(), by.toInt()))
    }.toList()

    var distances = mutableMapOf<Point, Int>()

    private val sensors: SensorGrid

    init {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE

        locations.forEach {
            val maybeNewMinX = min(it.first.x, it.second.x)
            val maybeNewMaxX = max(it.first.x, it.second.x)
            val maybeNewMinY = min(it.first.y, it.second.y)
            val maybeNewMaxY = max(it.first.y, it.second.y)
            minX = if (maybeNewMinX < minX) maybeNewMinX else minX
            maxX = if (maybeNewMaxX > maxX) maybeNewMaxX else maxX
            minY = if (maybeNewMinY < minY) maybeNewMinY else minY
            maxY = if (maybeNewMaxY > maxY) maybeNewMaxY else maxY
        }
        sensors = SensorGrid(minX, maxX, minY, maxY)

        locations.map {
            sensors.addSensor(it.first)
            sensors.addBeacon(it.second)
            distances[it.first] = distanceBetween(it.first, it.second)
        }
    }

    fun printGrid() {
        sensors.print()
    }

    fun computeSensorCoverage() {
        val sensorOrBeacon = listOf('S', 'B')
        val seen = mutableSetOf<Point>()
        locations.forEach {
            val (sensor, beacon) = it
            val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
            println("${sensor}, ${beacon}, distance = $distance")
            seen.add(sensor)
            val surrounding = sensors.getUncountedNeighbors(sensor).toMutableSet()
            while (surrounding.isNotEmpty()) {
                val point = surrounding.first()
                if (distanceBetween(sensor, point) <= distance && !seen.contains(point)) {
                    if (sensors.get(point.x, point.y) !in sensorOrBeacon) {
                        sensors.add(point.x, point.y, '#')
                    }
                    sensors.getUncountedNeighbors(point)
                        .filterNot { seen.contains(it) }
                        .map(surrounding::add)
                    seen.add(point)
                }
                surrounding.remove(point)
//                printGrid()
//                println()
            }
            printGrid()
        }
    }

    fun computeSensorCoverageBetter(targetRow: Int, condition: (row: Char) -> Boolean): Int {
        val sb = listOf('S', 'B')
        val count = getCoverageForRow(targetRow)
        return count.size - (sensors.points[targetRow]?.let { it.count { point -> condition(point.value) } } ?: 0)
    }

    fun getCoverageForRow(targetRow: Int, condition: (Int) -> Boolean = { true }): Set<Point> {
        var count = mutableSetOf<Point>()
        locations.forEach {
        val (sensor, beacon) = it
            val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
            //println(" checking ${it}, count = ${count.size}, dist = $distance")
            if (targetRow > sensor.y && targetRow in (sensor.y..sensor.y + distance) ||
                targetRow < sensor.y && targetRow in (sensor.y downTo sensor.y - distance)
            ) {
                val offset = abs(targetRow - sensor.y)
                val targetRowPoints = (distance * 2) - ((offset * 2) - 1)
                count.add(Point(sensor.x, targetRow))
                for (i in 1..(targetRowPoints - 1) / 2) {
                    if (condition(sensor.x - i)) count.add(Point(sensor.x - i, targetRow))
                    if (condition(sensor.x + i)) count.add(Point(sensor.x + i, targetRow))
                }
            }
            if (targetRow == sensor.y) {
                val targetRowPoints = (distance * 2)
                for (i in 1 .. targetRowPoints) {
                    if (condition(sensor.x - i)) count.add(Point(sensor.x - i, targetRow))
                    if (condition(sensor.x + i)) count.add(Point(sensor.x + i, targetRow))
                }
                count.add(Point(sensor.x, targetRow))
            }
        }
        return count
    }

    fun findHiddenBeacon(maxDim: Int) {
       var rowOfHidden: List<Point> = emptyList()
       (0 .. maxDim).forEach {
           val rowCoverage = getCoverageForRow(it, condition = { p: Int -> p in (0 .. maxDim)})
           val sorted = rowCoverage.sortedBy { sensor -> sensor.x }
           if ((sorted.first().x .. sorted.last().x).count() != sorted.count()) {
               rowOfHidden = sorted
           }
       }
       val one = rowOfHidden.map { it.x }.reduce { a: Int, b: Int -> a.xor(b) }
       val two = (rowOfHidden.first().x..rowOfHidden.last().x).reduce { a: Int, b: Int -> a.xor(b) }
       println("hidden @ ${one.xor(two)},${rowOfHidden.first().y}")
    }

    fun findHiddenBeaconBetter(maxDim: Int) {
        val searchRange = (0 .. maxDim)
        val hiddenBeacon = locations.firstNotNullOf { pair ->
            val sensor = pair.first
            val dist = distances[sensor]!!

            // just outside the boundaries of sensor range
            val up = Point(sensor.x, sensor.y - dist - 1)
            val down = Point(sensor.x, sensor.y + dist + 1)
            val left = Point(sensor.x - dist - 1, sensor.y)
            val right = Point(sensor.x + dist + 1, sensor.y)

            // outer perimeter
            val candidate = (up.lineTo(right) + right.lineTo(down) + down.lineTo(left) + left.lineTo(up))
                .filter { it.x in searchRange && it.y in searchRange }
                .firstOrNull { possibleBeacon ->
                    locations.none { sensor -> distanceBetween(sensor.first, possibleBeacon) <= distances[sensor.first]!! }
                }
            candidate
        }
        println("Hidden beacon? $hiddenBeacon, tuning freq = ${(hiddenBeacon.x * 4_000_000L) + hiddenBeacon.y}")
    }

    private fun distanceBetween(pt1: Point, pt2: Point): Int {
        return abs(pt1.x - pt2.x) + abs(pt1.y - pt2.y)
    }
}

data class Point(val x: Int, val y: Int) {

    fun lineTo(other: Point): List<Point> {
        val xDelta = (other.x - x).sign
        val yDelta = (other.y - y).sign
        val increments = maxOf((other.x - this.x).absoluteValue, (other.y - this.y).absoluteValue)
        return (1 .. increments).scan(this) { last, _ -> Point(last.x + xDelta, last.y + yDelta) }
    }
}

class SensorGrid(minX: Int, maxX: Int, minY: Int, maxY: Int) : SimpleGrid<Char>(minX, maxX, minY, maxY) {

    fun addSensor(pt: Point) {
        add(pt.x, pt.y, 'S')
    }

    fun addBeacon(pt: Point) {
        add(pt.x, pt.y, 'B')
    }

    fun getUncountedNeighbors(pt: Point): List<Point> {
        val next = mutableListOf<Point>()
        // up
        next.add(Point(pt.x, pt.y - 1))
        // upper right
        next.add(Point(pt.x + 1, pt.y - 1))
        // right
        next.add(Point(pt.x + 1, pt.y))
        // lower right
        next.add(Point(pt.x + 1, pt.y + 1))
        // down
        next.add(Point(pt.x, pt.y + 1))
        // lower left
        next.add(Point(pt.x - 1, pt.y + 1))
        // left
        next.add(Point(pt.x - 1, pt.y))
        // upper left
        next.add(Point(pt.x - 1, pt.y - 1))

        return next
    }

    override fun output(contents: Char?) {
        contents?.let { print(it) }
    }

    override fun outputUnoccupied(s: Any?) {
        print(".")
    }
}
