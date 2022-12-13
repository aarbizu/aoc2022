package org.aarbizu.aoc2022.dayEight

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.aarbizu.aoc2022.util.parseToIntGrid
import org.junit.jupiter.api.Test
import java.io.File

class TreeCoverTest {

    @Test
    fun `parsing to grid`() {
        val grid = parseToIntGrid(sample.lines())
        assertThat(grid).isNotNull()
    }

    @Test
    fun `count perimeter`() {
        val grid = parseToIntGrid(sample.lines())
        assertThat(grid.perimeter().count())
            .isEqualTo(16)
            .also { println("visible (perimeter trees) = ${grid.perimeter().count()}") }
    }

    @Test
    fun `count visible in sample`() {
        val grid = parseToIntGrid(sample.lines())
        val gridSet = grid.getGridSet()
        assertThat(gridSet.size).isEqualTo(25)

        val perimeterSet = grid.perimeter()
        val toBeEvaluated = gridSet - perimeterSet
        assertThat(toBeEvaluated.size).isEqualTo(9)
        assertThat(toBeEvaluated.map { it.value }).containsExactly(5, 5, 1, 5, 3, 3, 3, 5, 4)
    }

    @Test
    fun sample() {
        val visible = TreeCover().findVisibleTrees(sample.lines())
        assertThat(visible.size).isEqualTo(21)
    }

    @Test
    fun part1() {
        val treeGridStrings = File("$INPUTS_DIR/d8-p1.txt").readLines()
        val visible = TreeCover().findVisibleTrees(treeGridStrings)
        assertThat(visible.size).isEqualTo(1679).also { println("visible tree count = ${visible.size}") }
    }

    @Test
    fun `sample best visibility score of all trees`() {
        val maxVisibility = TreeCover().maxVisibilityScore(sample.lines())
        assertThat(maxVisibility).isEqualTo(8)
    }

    @Test
    fun part2() {
        val treeGridStrings = File("$INPUTS_DIR/d8-p1.txt").readLines()
        val maxVisibility = TreeCover().maxVisibilityScore(treeGridStrings)
        assertThat(maxVisibility).isEqualTo(536625).also { println("max visibility = $maxVisibility") }
    }

    val sample = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()
}
