package org.aarbizu.aoc2022.dayNine

import kotlin.math.abs

class RopeModel(val moves: List<String>) {
    private val head = Knot(0,0)
    val tail = Knot(0,0)

    val tailVisits = mutableSetOf<Pair<Int,Int>>()
    fun processMoves() {
        tailVisits.add(tail.loc)
        moves.map {
            doMove(it)
        }
    }

    private fun doMove(moveLine: String) {
        val (moveLabel, countStr) = moveLine.split(" ")
        val move = when(moveLabel) {
            "R" -> Right
            "L" -> Left
            "U" -> Up
            "D" -> Down
            else -> throw Exception("unknown move label!")
        }
        (0 until countStr.toInt()).forEach { _ ->
            move.process(head)
            processTail()
        }
    }

    private fun processTail() {
        if(!tail.isAdjacentOrOverlaying(head)) {
            if (tail.x == head.x) {
                if (tail.y > head.y) {
                    Down.process(tail)
                } else {
                    Up.process(tail)
                }
            } else if (tail.y == head.y) {
                if (tail.x > head.x) {
                    Left.process(tail)
                } else {
                    Right.process(tail)
                }
            } else {
                if (tail.x > head.x && tail.y > head.y) {
                    Left.process(tail)
                    Down.process(tail)
                } else if (tail.x < head.x && tail.y > head.y) {
                    Right.process(tail)
                    Down.process(tail)
                } else if (tail.x > head.x) {
                    Left.process(tail)
                    Up.process(tail)
                } else {
                    Right.process(tail)
                    Up.process(tail)
                }

            }
            tailVisits.add(tail.loc)
        }
    }
}

sealed interface Move {
    var process: (Knot) -> Unit
}
object Right : Move {
    override var process: (Knot) -> Unit = { k -> k.x++ }
}
object Left : Move {
    override var process: (Knot) -> Unit = { k -> k.x-- }
}
object Up : Move {
    override var process: (Knot) -> Unit = { k -> k.y++ }
}
object Down : Move {
    override var process: (Knot) -> Unit = { k -> k.y-- }
}

data class Knot(var x: Int, var y: Int) {
    val loc: Pair<Int,Int>
        get() = Pair(x, y)
    fun isAdjacentOrOverlaying(other: Knot): Boolean {
        return  (this.x == other.x && this.y == other.y) || // overlap
                (this.x == other.x && abs(this.y - other.y) == 1) ||  // same col, above or below 1
                (this.y == other.y && abs(this.x - other.x) == 1) ||  // same row, left or right 1
                (abs(this.y - other.y) == 1 && abs(this.x - other.x) == 1) // diagonally adjacent
    }
}