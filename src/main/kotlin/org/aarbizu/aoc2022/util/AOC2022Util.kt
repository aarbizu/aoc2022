package org.aarbizu.aoc2022.util

const val INPUTS_DIR = "src/main/resources"

/**
 * Thank you, https://play.kotlinlang.org/byExample/01_introduction/06_Generics
 */
class MutableStack<E>(vararg items: E) {

    private val elements = items.toMutableList()
    fun push(element: E) = elements.add(element)
    fun peek(): E = elements.last()
    fun pop(): E = elements.removeAt(elements.size - 1)
    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    override fun toString() = "MutableStack(${elements.joinToString()})"
}
fun <E> mutableStackOf(vararg elements: E) = MutableStack(*elements)

/**
 * support pop()'ing more than one element at a time, preserving order
 * basically a slice() operation
 */
fun <E> MutableStack<E>.multipop(numElements: Int): Collection<E> {
    val popped = mutableListOf<E>()
    (0 until numElements).forEach { _ ->
        popped.add(this.pop())
    }
    return popped.toList()
}

fun <E> MutableStack<E>.multipush(pushed: Collection<E>) {
    for (n in pushed.size - 1 downTo 0) {
        this.push(pushed.elementAt(n))
    }
}

/**
 * Int Grid
 * see https://github.com/aarbizu/aoc2021/blob/main/src/main/kotlin/org/aarbizu/aoc2021/util/Aoc2021Utils.kt
 **/

fun printGrid(input: List<List<Any>>): String {
    return input.joinToString("\n") { it.joinToString("") }
}
fun parseGrid(input: List<String>): IntGrid {
    return IntGrid(
        input.map {
            it.trim().toList().map { d -> d.digitToInt() }.toMutableList()
        }
    )
}

class IntGrid(val gridContents: List<MutableList<Int>>) {
    val rows: Int
        get() = gridContents.size
    val cols: Int
        get() = gridContents[0].size

    private fun outOfBounds(r: Int, c: Int): Boolean {
        return (r < 0 || r >= rows || c < 0 || c >= cols)
    }

    fun at(r: Int, c: Int): GridSquare<Int>? {
        return if (outOfBounds(r, c)) {
            null
        } else {
            gridValue(r, c)
        }
    }

    fun inc(r: Int, c: Int) {
        if (outOfBounds(r, c)) { return }
        gridContents[r][c]++
    }

    fun set(r: Int, c: Int, value: Int) {
        if (outOfBounds(r, c)) { return }
        gridContents[r][c] = value
    }

    operator fun get(r: Int, c: Int): Int {
        return if (outOfBounds(r, c)) -1 else gridContents[r][c]
    }

    fun search(clause: (Int) -> Boolean): List<GridSquare<Int>> {
        val matching = mutableListOf<GridSquare<Int>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (clause.invoke(gridContents[i][j])) {
                    at(i, j)?.also { matching.add(it) }
                }
            }
        }
        return matching
    }

    fun getNonDiagonalNeighbors(r: Int, c: Int): List<GridSquare<Int>?> {
        return listOf(above(r, c), below(r, c), left(r, c), right(r, c))
    }

    fun getNonDiagonalNeighbors(r: Int, c: Int, threshold: Int): List<GridSquare<Int>> {
        return getNonDiagonalNeighbors(r, c).filterNotNull().filterNot { it.value == threshold }
    }

    fun getDiagonalNeighbors(r: Int, c: Int): List<GridSquare<Int>?> {
        return listOf(aboveRight(r, c), aboveLeft(r, c), belowRight(r, c), belowLeft(r, c))
    }

    fun getAllNeighbors(r: Int, c: Int): List<GridSquare<Int>?> {
        return getNonDiagonalNeighbors(r, c) + getDiagonalNeighbors(r, c)
    }

    fun aboveRight(row: Int, col: Int): GridSquare<Int>? {
        return if (row <= 0 || col >= cols - 1) {
            null
        } else {
            gridValue(row - 1, col + 1)
        }
    }

    fun aboveLeft(row: Int, col: Int): GridSquare<Int>? {
        return if (row <= 0 || col <= 0) {
            null
        } else {
            gridValue(row - 1, col - 1)
        }
    }

    fun belowRight(row: Int, col: Int): GridSquare<Int>? {
        return if (row >= rows - 1 || col >= cols - 1) {
            null
        } else {
            gridValue(row + 1, col + 1)
        }
    }

    fun belowLeft(row: Int, col: Int): GridSquare<Int>? {
        return if (row >= rows - 1 || col <= 0) {
            null
        } else {
            gridValue(row + 1, col - 1)
        }
    }

    fun above(row: Int, col: Int): GridSquare<Int>? {
        return if (row <= 0) {
            null
        } else {
            gridValue(row - 1, col)
        }
    }

    fun below(row: Int, col: Int): GridSquare<Int>? {
        return if (row >= rows - 1) {
            null
        } else {
            gridValue(row + 1, col)
        }
    }

    fun left(row: Int, col: Int): GridSquare<Int>? {
        return if (col <= 0) {
            null
        } else {
            gridValue(row, col - 1)
        }
    }

    fun right(row: Int, col: Int): GridSquare<Int>? {
        return if (col >= cols - 1) {
            null
        } else {
            gridValue(row, col + 1)
        }
    }

    fun gridValue(row: Int, col: Int): GridSquare<Int> {
        return GridSquare(row, col, gridContents[row][col])
    }

    fun getGridSet(): Set<GridSquare<Int>> {
        return gridContents
            .flatMapIndexed { row: Int, colValues: MutableList<Int> ->
                colValues
                    .mapIndexed { col: Int, _ ->
                        at(row, col)!!
                    }
            }.toSet()
    }

    fun perimeter(): Set<GridSquare<Int>> {
        val gridSquareSet = mutableSetOf<GridSquare<Int>>()

        (0 until cols).forEach { gridSquareSet.add(gridValue(0, it)) }
        (0 until cols).forEach { gridSquareSet.add(gridValue(gridContents.lastIndex, it)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, 0)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, cols - 1)) }

        return gridSquareSet
    }

    override fun toString(): String {
        return gridContents.joinToString("\n") { list -> list.joinToString("") }
    }
}

data class GridSquare<T>(val row: Int, val col: Int, var value: T) {
    var shortestPath: List<GridSquare<T>> = emptyList()

    override fun toString(): String {
        return "$row,$col"
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridSquare<*>

        if (row != other.row) return false
        if (col != other.col) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }
}
