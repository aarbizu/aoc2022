package org.aarbizu.aoc2022.dayFive

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class CrateStacksTest {

    @Test
    fun `part one`() {
        val readLines = File("$INPUTS_DIR/d5-p1.txt").readLines()
        val crates = readLines.take(8).reversed()
        val allCrates = crates.map { CrateStacks.cratesFromString(it, 9) }.toList()
        val crateStacks = CrateStacks(9, allCrates)
        val moves = readLines.subList(10, readLines.size)
        val moveList = CrateStacks.getMoves(moves)
        crateStacks.runMoves(moveList)
        crateStacks.getStacks().map { print(it.peek().label) }.also { println() }
    }

    @Test
    fun `part two`() {
        val readLines = File("$INPUTS_DIR/d5-p1.txt").readLines()
        val crates = readLines.take(8).reversed()
        val allCrates = crates.map { CrateStacks.cratesFromString(it, 9) }.toList()
        val crateStacks = CrateStacks(9, allCrates)
        val moves = readLines.subList(10, readLines.size)
        val moveList = CrateStacks.getMoves(moves)
        crateStacks.runMovesNew(moveList)
        crateStacks.getStacks().map { print(it.peek().label) }.also { println() }
    }
    @Test
    fun `parse input`() {
        val readLines = File("$INPUTS_DIR/d5-p1.txt").readLines()
        val crates = readLines.take(8).reversed() // crates

        val allCrates = crates.map { CrateStacks.cratesFromString(it, 9) }.toList()
        assertThat(allCrates[1].size).isEqualTo(9)

        val crateStacks = CrateStacks(9, allCrates)
        crateStacks.print()
    }

    @Test
    fun `sample`() {
        val sampleData = sampleData.lines()
        val crateLines = sampleData.take(3).reversed()

        val allCrates = crateLines.map { CrateStacks.cratesFromString(it, 3) }.toList()
        val crateStacks = CrateStacks(3, allCrates)
        crateStacks.print()

        val moves = sampleData.subList(5, sampleData.size)
        val moveList = CrateStacks.getMoves(moves)
        assertThat(moveList.size).isEqualTo(4)
        assertThat(moveList[0]).isEqualTo(Move(1, 2, 1))
        assertThat(moveList[1]).isEqualTo(Move(3, 1, 3))
        assertThat(moveList[2]).isEqualTo(Move(2, 2, 1))
        assertThat(moveList[3]).isEqualTo(Move(1, 1, 2))

        crateStacks.runMoves(moveList)

        val stacks = crateStacks.getStacks()

        stacks.map { print(it.peek().label) }
    }

    @Test
    fun `sample two`() {
        val sampleData = sampleData.lines()
        val crateLines = sampleData.take(3).reversed()

        val allCrates = crateLines.map { CrateStacks.cratesFromString(it, 3) }.toList()
        val crateStacks = CrateStacks(3, allCrates)
        crateStacks.print()

        val moves = sampleData.subList(5, sampleData.size)
        val moveList = CrateStacks.getMoves(moves)
        crateStacks.runMovesNew(moveList)

        val stacks = crateStacks.getStacks()

        stacks.map { print(it.peek().label) }
    }

    val sampleData = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent()
}