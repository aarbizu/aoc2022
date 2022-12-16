package org.aarbizu.aoc2022.dayFourteen

class FallingSand(private val rockScan: List<String>) {
    val cavern = buildRock()

    private fun buildRock(): CavernGrid {
        val cavern = CavernGrid()
        cavern.addSource(Point(500, 0))

        val rockLines = rockScan.map {
            it.split(" -> ")
                .map { it.split(",") }
                .map { Point(it.first().toInt(), it.last().toInt()) }
                .toList()
        }.toList()

        cavern.addRockLines(rockLines)
        return cavern
    }

    fun sand() {
        var intoTheAbyss = false
        while (!intoTheAbyss) {
            val sandGrain = Point(500, 0)
            intoTheAbyss = cavern.fall(sandGrain)
        }
    }

    fun sandWithFloor() {
        var restLocation = Point(0, 0)
        var count = 0
        while (!(restLocation.x == 500 && restLocation.y == 0)) {
            val sandGrain = Point(500, 0)
            restLocation = cavern.fallToFloor(sandGrain)
            if (++count % 100 == 0) {
                cavern.print(true)
                print("\n\n")
            }
        }
    }

    data class Point(val x: Int, val y: Int)

    /**
     * here, lower down means a higher value for the y-coordinate
     */
    class CavernGrid {
        private val points: MutableMap<Int, MutableMap<Int, Char>> = mutableMapOf()
        private val barriers = listOf('#', 'o')
        var minX = 500
        var maxX = 500
        var minY = 0
        var maxY = 0

        fun getFloor(): Int = maxY + 2

        /**
         * Return true if the grain of sand falls forever
         */
        fun fall(pt: Point): Boolean {
            var y = pt.y
            while (!collided(pt.x, y)) {
                y++
                if (y > maxY) return true
            }
            if (points[y]?.get(pt.x - 1) == null) {
                return fall(Point(pt.x - 1, y))
            } else if (points[y]?.get(pt.x + 1) == null) {
                return fall(Point(pt.x + 1, y))
            } else {
                points.getOrPut(y - 1) { mutableMapOf() }[pt.x] = 'o'
                return false
            }
        }

        fun fallToFloor(pt: Point): Point {
            var y = pt.y
            while (y < getFloor() && !collided(pt.x, y)) {
                y++
            }
            return if (y < getFloor() && points[y]?.get(pt.x - 1) == null) {
                fallToFloor(Point(pt.x - 1, y))
            } else if (y < getFloor() && points[y]?.get(pt.x + 1) == null) {
                fallToFloor(Point(pt.x + 1, y))
            } else {
                points.getOrPut(y - 1) { mutableMapOf() }[pt.x] = 'o'
                updateMinMaxX(pt.x)
                Point(pt.x, y - 1)
            }
        }

        fun collided(x: Int, y: Int): Boolean {
            return if (points[y]?.get(x) != null) {
                val surface = points[y]?.get(x)
                return surface in barriers
            } else {
                false
            }
        }

        fun atRest(): Int {
            return points.map { row -> row.value.filter { it.value == 'o' }.count() }.sum()
        }

        fun addRockLines(pts: List<List<Point>>) {
            pts.forEach { points -> points.windowed(2).map { addRockLine(it.first(), it.last()) } }
        }

        fun addRockLine(pt1: Point, pt2: Point) {
            if (pt1.x == pt2.x) {
                val yRange = if (pt1.y < pt2.y) (pt1.y..pt2.y) else (pt2.y..pt1.y)
                for (y in yRange) {
                    addRock(Point(pt1.x, y))
                }
            } else {
                val xRange = if (pt1.x < pt2.x) (pt1.x..pt2.x) else (pt2.x..pt1.x)
                for (x in xRange) {
                    addRock(Point(x, pt1.y))
                }
            }
        }

        fun addRock(pt: Point) {
            updateMinMax(pt)
            points.getOrPut(pt.y) { mutableMapOf() }[pt.x] = '#'
        }

        fun addSource(pt: Point) {
            updateMinMax(pt)
            points.getOrPut(pt.y) { mutableMapOf() }[pt.x] = '+'
        }

        private fun updateMinMax(pt: Point) {
            updateMinMaxX(pt.x)
            updateMinMaxY(pt.y)
        }

        private fun updateMinMaxX(x: Int) {
            if (x < minX) minX = x
            if (x > maxX) maxX = x
        }

        private fun updateMinMaxY(y: Int) {
            if (y > maxY) maxY = y
            if (y < minY) minY = y
        }

        fun print(withFloor: Boolean = false) {
            for (y in minY..if (withFloor) getFloor() else maxY) {
                for (x in minX..maxX) {
                    if (y == 0 && x == 500) {
                        print(if (points[y]?.get(x) != null) " ${points[y]!![x]} " else " + ")
                    } else {
                        if (points[y]?.get(x) != null) {
                            print(" ${points[y]?.get(x)} ")
                        } else {
                            print(" . ")
                        }
                    }
                }
                println()
            }
        }
    }
}
