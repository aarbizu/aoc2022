package org.aarbizu.aoc2022.dayEight

import org.aarbizu.aoc2022.util.GridSquare
import org.aarbizu.aoc2022.util.IntGrid
import org.aarbizu.aoc2022.util.parseToIntGrid

class TreeCover {
    fun findVisibleTrees(gridAsStrings: List<String>): List<GridSquare<Int>> {
        val theGrid = parseToIntGrid(gridAsStrings)

        val perimeterSet = theGrid.perimeter()
        val toBeEvaluated = theGrid.getGridSet() - perimeterSet

        val filtered = toBeEvaluated.filter { isVisible(it, theGrid) }

        return filtered + perimeterSet
    }

    fun maxVisibilityScore(gridAsStrings: List<String>): Int {
        val theGrid = parseToIntGrid(gridAsStrings)

        val perimeterSet = theGrid.perimeter()
        val toBeEvaluated = theGrid.getGridSet() - perimeterSet
        return toBeEvaluated.maxOf { vizScore(it, theGrid) }
    }

    private fun vizScore(tree: GridSquare<Int>, forest: IntGrid): Int {
        var row = tree.row
        var col = tree.col
        val height = tree.value

        var leftScore = 0
        do {
            col -= 1
            if (forest[row, col] != -1) {
                if (forest[row, col] < height) {
                    leftScore++
                } else {
                    leftScore++
                    break
                }
            }
        } while (forest[row, col] != -1)

        row = tree.row
        col = tree.col

        var rightScore = 0
        do {
            col += 1
            if (forest[row, col] != -1) {
                if (forest[row, col] < height) {
                    rightScore++
                } else {
                    rightScore++
                    break
                }
            }
        } while (forest[row, col] != -1)

        row = tree.row
        col = tree.col

        var upScore = 0
        do {
            row -= 1
            if (forest[row, col] != -1) {
                if (forest[row, col] < height) {
                    upScore++
                } else {
                    upScore++
                    break
                }
            }
        } while (forest[row, col] != -1)

        row = tree.row
        col = tree.col
        var downScore = 0
        do {
            row += 1
            if (forest[row, col] != -1) {
                if (forest[row, col] < height) {
                    downScore++
                } else {
                    downScore++
                    break
                }
            }
        } while (forest[row, col] != -1)

        return listOf(leftScore, rightScore, upScore, downScore)
            .filter { it > 0 }
            .reduceRight { a: Int, b: Int -> a * b }
    }

    private fun isVisible(tree: GridSquare<Int>, forest: IntGrid): Boolean {
        var row = tree.row
        var col = tree.col
        val height = tree.value

        // check visibility to left
        var leftVisible = true
        do {
            col -= 1
            if (forest[row, col] >= height) {
                leftVisible = false
            }
        } while (forest[row, col] != -1)

        row = tree.row
        col = tree.col

        var rightVisible = true
        do {
            col += 1
            if (forest[row, col] >= height) {
                rightVisible = false
            }
        } while ((forest[row, col] != -1))

        row = tree.row
        col = tree.col

        var topVisible = true
        do {
            row += 1
            if (forest[row, col] >= height) {
                topVisible = false
            }
        } while ((forest[row, col] != -1))

        row = tree.row
        col = tree.col

        var bottomVisible = true
        do {
            row -= 1
            if (forest[row, col] >= height) {
                bottomVisible = false
            }
        } while ((forest[row, col] != -1))

        return leftVisible || rightVisible || topVisible || bottomVisible
    }
}
