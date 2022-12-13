package org.aarbizu.aoc2022.dayTwelve

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.CharGrid
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class HillClimbingTest {
    private val sample = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    @Test
    fun `sample data`() {
        val grid = CharGrid(sample.lines().map { it.toMutableList() })
        assertThat(HillClimbing().ascend(grid, 'S', 'E')).isEqualTo(31)
    }

    @Test
    fun `part 1`() {
        val grid = CharGrid(File("$INPUTS_DIR/d12-p1.txt").readLines().map { it.toMutableList() })
        assertThat(HillClimbing().ascend(grid, 'S', 'E')).isEqualTo(490)
    }

    @Test
    fun `sample data 2`() {
        val grid = CharGrid(sample.lines().map { it.toMutableList() })
        val shortestFromA = HillClimbing().findShortestFromAny(grid, 'a', 'E')
        println("shortest from A = $shortestFromA")
    }

    @Test
    fun `part 2`() {
        val grid = CharGrid(File("$INPUTS_DIR/d12-p1.txt").readLines().map { it.toMutableList() })
        val shortest = HillClimbing().findShortestFromAny(grid, 'a', 'E')
        println("shortest from A = ${shortest.joinToString(":")}")
        println("${shortest.min()}")
        assertThat(shortest.min()).isEqualTo(488)
    }
}
