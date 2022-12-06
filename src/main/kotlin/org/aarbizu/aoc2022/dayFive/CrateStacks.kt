package org.aarbizu.aoc2022.dayFive

import org.aarbizu.aoc2022.util.MutableStack
import org.aarbizu.aoc2022.util.multipop
import org.aarbizu.aoc2022.util.multipush
import org.aarbizu.aoc2022.util.mutableStackOf

class CrateStacks(private val numStacks: Int, parsedCrates: List<Array<Crate>>) {
    private val stacks: List<MutableStack<Crate>> = List(numStacks) { mutableStackOf() }

    init {
        for (n in 0..parsedCrates[0].lastIndex) {
            for (k in parsedCrates.indices) {
                if (!parsedCrates[k][n].isEmpty()) {
                    stacks[n].push(parsedCrates[k][n])
                }
            }
        }
    }

    fun print() {
        (0 until numStacks).forEach {
            println("$it ${stacks[it]}")
        }
    }

    private fun moveOne(count: Int, src: Int, dest: Int) {
        (0 until count).forEach {
            val crate = stacks[src].pop()
            stacks[dest].push(crate)
        }
    }

    private fun moveMultiple(count: Int, src: Int, dest: Int) {
        val crates = stacks[src].multipop(count)
        stacks[dest].multipush(crates)
    }

    fun runMoves(moveList: List<Move>) {
        moveList.forEach {
            moveOne(it.count, it.fromStack - 1, it.toStack - 1)
        }
    }

    fun runMovesNew(moveList: List<Move>) {
        moveList.forEach {
            moveMultiple(it.count, it.fromStack - 1, it.toStack - 1)
        }
    }

    fun getStacks(): List<MutableStack<Crate>> {
        return stacks
    }

    companion object {
        val movesRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

        // 9 crates of ('[x]') == 27, plus 8 spaces.  If last crate is not the 9th, might be shorter
        // crate1 = 0,*1*,2
        // crate2 = 4,*5*,6
        // etc., labels are every 4 chars, when populated, else they're empty.
        // This parsing helps identify where there are empty crates in the initial state
        fun cratesFromString(line: String, numStacks: Int): Array<Crate> {
            val crates = Array(numStacks) { emptyCrate }
            val cratesArray = line.toCharArray()
            var k = 0
            for (i in 1..cratesArray.size step 4) {
                crates[k++] = Crate.fromChar(cratesArray[i])
            }
            return crates
        }

        fun getMoves(movesDesc: List<String>): List<Move> {
            return movesDesc
                .map { movesRegex.find(it)!!.destructured }
                .map { destructured ->
                    Move(
                        destructured.component1().toInt(),
                        destructured.component2().toInt(),
                        destructured.component3().toInt()
                    )
                }
                .toList()
        }
    }
}

val emptyCrate = Crate(label = 0.toChar())
class Crate(val label: Char) {
    fun isEmpty(): Boolean = this == emptyCrate

    companion object {
        fun fromChar(c: Char): Crate {
            return when (c) {
                ' ' -> emptyCrate
                in 'A'..'Z' -> Crate(c)
                else -> throw Exception("unexpected crate label!")
            }
        }
    }

    override fun toString(): String {
        return if (this == emptyCrate) "[ ]" else "[$label]"
    }
}

data class Move(val count: Int, val fromStack: Int, val toStack: Int)
