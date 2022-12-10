package org.aarbizu.aoc2022.dayNine

import kotlin.math.abs
import kotlin.math.max

class RopeModel(val moves: List<String>) {
    private val head = Knot('h', 0, 0)
    private val one = Knot('1', 0, 0)
    private val two = Knot('2', 0, 0)
    private val three = Knot('3', 0, 0)
    private val four = Knot('4', 0, 0)
    private val five = Knot('5', 0, 0)
    private val six = Knot('6', 0, 0)
    private val seven = Knot('7', 0, 0)
    private val eight = Knot('8', 0, 0)
    private val nine = Knot('9', 0, 0)
    val tail = Knot('t', 0, 0)

    val tailVisits = mutableSetOf<Pair<Int, Int>>()

    fun processMoves(doPrint: Boolean) {
        tailVisits.add(tail.loc)
        moves.map {
            doMove(it, doPrint)
        }
    }

    fun processMoves2(doPrint: Boolean) {
        tailVisits.add(nine.loc)
        moves.map {
            doMove2(it, doPrint)
        }
    }

    fun doMove2(moveLine: String, showOutput: Boolean) {
        val (move, count) = parseMove(moveLine)
        if (showOutput) {
            println("${move.name}, $count")
            printRope2()
        }
        (0 until count).forEach { _ ->
            move.process(head)
            processTail(head, one)
            processTail(one, two)
            processTail(two, three)
            processTail(three, four)
            processTail(four, five)
            processTail(five, six)
            processTail(six, seven)
            processTail(seven, eight)
            processTail(eight, nine)
            tailVisits.add(nine.loc)
            if (showOutput) printRope2()
        }
    }

    private fun doMove(moveLine: String, showOutput: Boolean) {
        val (move, count) = parseMove(moveLine)
        if (showOutput) {
            println("${move.name}, $count")
            printRope()
        }
        (0 until count).forEach { _ ->
            move.process(head)
            processTail(head, tail)
            tailVisits.add(tail.loc)
            if (showOutput) printRope()
        }
    }

    private fun parseMove(moveLine: String): Pair<Move, Int> {
        val (moveLabel, countStr) = moveLine.split(" ")
        val move = when (moveLabel) {
            "R" -> Right
            "L" -> Left
            "U" -> Up
            "D" -> Down
            else -> throw Exception("unknown move label!")
        }
        return Pair(move, countStr.toInt())
    }

    private fun processTail(lead: Knot, follow: Knot) {
        if (!follow.isAdjacentOrOverlaying(lead)) {
            if (follow.x == lead.x) {
                if (follow.y > lead.y) {
                    Down.process(follow)
                } else {
                    Up.process(follow)
                }
            } else if (follow.y == lead.y) {
                if (follow.x > lead.x) {
                    Left.process(follow)
                } else {
                    Right.process(follow)
                }
            } else {
                if (follow.x > lead.x && follow.y > lead.y) {
                    Left.process(follow)
                    Down.process(follow)
                } else if (follow.x < lead.x && follow.y > lead.y) {
                    Right.process(follow)
                    Down.process(follow)
                } else if (follow.x > lead.x) {
                    Left.process(follow)
                    Up.process(follow)
                } else {
                    Right.process(follow)
                    Up.process(follow)
                }
            }
        }
    }

    private fun printRope() {
        val xSize = max(abs(tail.x), abs(head.x))
        val ySize = max(abs(tail.y), abs(head.y))
        val margin = 2
        val grid = Array(ySize + margin) { Array(xSize + margin) { '.' } }
        grid[0][0] = 's'
        // ignore negative-point values for now
        if (tail.x >= 0 && tail.y >= 0 && head.x >= 0 && head.y >= 0) {
            grid[tail.y][tail.x] = 't'
            grid[head.y][head.x] = 'h'

            for (i in grid.lastIndex downTo 0) {
                for (j in grid[i].indices) {
                    print(grid[i][j])
                }
                println()
            }
            println("--")
        }
    }

    private fun printRope2() {
        val xSize = listOf(head, one, two, three, four, five, six, seven, eight, nine).maxOfOrNull { abs(it.x) }!!
        val ySize = listOf(head, one, two, three, four, five, six, seven, eight, nine).maxOfOrNull { abs(it.y) }!!
        val margin = 2
        val grid = Array(ySize + margin) { Array(xSize + margin) { '.' } }
        grid[0][0] = 's'
        val allNonNegative = listOf(head, one, two, three, four, five, six, seven, eight, nine)
            .all { it.x >= 0 && it.y >= 0 }
        if (allNonNegative) {
            listOf(nine, eight, seven, six, five, four, three, two, one, head)
                .map { grid[it.y][it.x] = it.label }

            for (i in grid.lastIndex downTo 0) {
                for (j in grid[i].indices) {
                    print(grid[i][j])
                }
                println()
            }
            println("--")
        }
    }
}

sealed interface Move {
    var process: (Knot) -> Unit
    val name: String
}

object Right : Move {
    override var process: (Knot) -> Unit = { k -> k.x++ }
    override val name: String
        get() = "R"
}
object Left : Move {
    override var process: (Knot) -> Unit = { k -> k.x-- }
    override val name: String
        get() = "L"
}
object Up : Move {
    override var process: (Knot) -> Unit = { k -> k.y++ }
    override val name: String
        get() = "U"
}
object Down : Move {
    override var process: (Knot) -> Unit = { k -> k.y-- }
    override val name: String
        get() = "D"
}

data class Knot(var label: Char, var x: Int, var y: Int) {
    val loc: Pair<Int, Int>
        get() = Pair(x, y)
    fun isAdjacentOrOverlaying(other: Knot): Boolean {
        return (this.x == other.x && this.y == other.y) || // overlap
            (this.x == other.x && abs(this.y - other.y) == 1) || // same col, above or below 1
            (this.y == other.y && abs(this.x - other.x) == 1) || // same row, left or right 1
            (abs(this.y - other.y) == 1 && abs(this.x - other.x) == 1) // diagonally adjacent
    }

    override fun toString(): String {
        return label.toString()
    }
}
