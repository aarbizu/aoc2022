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
 * Grid<T>/GridSquare<T> for when the contents at grid locations needs to be non-trivial
 * see https://github.com/aarbizu/aoc2021/blob/main/src/main/kotlin/org/aarbizu/aoc2021/util/Aoc2021Utils.kt
 **/

fun gridToString(input: List<List<Any>>): String {
    return input.joinToString("\n") { it.joinToString("") }
}

fun parseToCharGrid(input: List<String>): CharGrid {
    return CharGrid(
        input.map {
            it.trim().toList().toMutableList()
        }
    )
}

fun parseToIntGrid(input: List<String>): IntGrid {
    return IntGrid(
        input.map {
            it.trim().toList().map { d -> d.digitToInt() }.toMutableList()
        }
    )
}

abstract class Grid<T>(open val contents: List<MutableList<T>>) {
    val rows: Int
        get() = contents.size
    val cols: Int
        get() = contents[0].size

    fun outOfBounds(r: Int, c: Int): Boolean {
        return (r < 0 || r >= rows || c < 0 || c >= cols)
    }

    fun at(r: Int, c: Int): GridSquare<T>? {
        return if (outOfBounds(r, c)) {
            null
        } else {
            gridValue(r, c)
        }
    }

    fun set(r: Int, c: Int, value: T) {
        if (outOfBounds(r, c)) { return }
        contents[r][c] = value
    }

    open operator fun get(r: Int, c: Int): T? {
        return if (outOfBounds(r, c)) null else contents[r][c]
    }

    fun gridValue(row: Int, col: Int): GridSquare<T> {
        return GridSquare(row, col, contents[row][col])
    }

    fun getNonDiagonalNeighbors(r: Int, c: Int): List<GridSquare<T>?> {
        return listOf(above(r, c), below(r, c), left(r, c), right(r, c))
    }

    fun getNonDiagonalNeighbors(r: Int, c: Int, predicate: (T) -> Boolean): List<GridSquare<T>> {
        return getNonDiagonalNeighbors(r, c).filterNotNull().filterNot { predicate(it.value) }
    }

    fun getDiagonalNeighbors(r: Int, c: Int): List<GridSquare<T>?> {
        return listOf(aboveRight(r, c), aboveLeft(r, c), belowRight(r, c), belowLeft(r, c))
    }

    fun getAllNeighbors(r: Int, c: Int): List<GridSquare<T>?> {
        return getNonDiagonalNeighbors(r, c) + getDiagonalNeighbors(r, c)
    }

    fun aboveRight(row: Int, col: Int): GridSquare<T>? {
        return if (row <= 0 || col >= cols - 1) {
            null
        } else {
            gridValue(row - 1, col + 1)
        }
    }

    fun aboveLeft(row: Int, col: Int): GridSquare<T>? {
        return if (row <= 0 || col <= 0) {
            null
        } else {
            gridValue(row - 1, col - 1)
        }
    }

    fun belowRight(row: Int, col: Int): GridSquare<T>? {
        return if (row >= rows - 1 || col >= cols - 1) {
            null
        } else {
            gridValue(row + 1, col + 1)
        }
    }

    fun belowLeft(row: Int, col: Int): GridSquare<T>? {
        return if (row >= rows - 1 || col <= 0) {
            null
        } else {
            gridValue(row + 1, col - 1)
        }
    }

    fun above(row: Int, col: Int): GridSquare<T>? {
        return if (row <= 0) {
            null
        } else {
            gridValue(row - 1, col)
        }
    }

    fun below(row: Int, col: Int): GridSquare<T>? {
        return if (row >= rows - 1) {
            null
        } else {
            gridValue(row + 1, col)
        }
    }

    fun left(row: Int, col: Int): GridSquare<T>? {
        return if (col <= 0) null else { gridValue(row, col - 1) }
    }

    fun right(row: Int, col: Int): GridSquare<T>? {
        return if (col >= cols - 1) {
            null
        } else {
            gridValue(row, col + 1)
        }
    }

    fun getGridSet(): Set<GridSquare<T>> {
        return contents.flatMapIndexed { row: Int, colValues: MutableList<T> ->
            colValues.mapIndexed { col: Int, _ -> at(row, col)!! }
        }.toSet()
    }

    fun perimeter(): Set<GridSquare<T>> {
        val gridSquareSet = mutableSetOf<GridSquare<T>>()

        (0 until cols).forEach { gridSquareSet.add(gridValue(0, it)) }
        (0 until cols).forEach { gridSquareSet.add(gridValue(contents.lastIndex, it)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, 0)) }
        (0 until rows).forEach { gridSquareSet.add(gridValue(it, cols - 1)) }

        return gridSquareSet
    }

    override fun toString(): String {
        return contents.joinToString("\n") { list -> list.joinToString("") }
    }

    fun search(clause: (T) -> Boolean): List<GridSquare<T>> {
        val matching = mutableListOf<GridSquare<T>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (clause.invoke(contents[i][j])) {
                    at(i, j)?.also { matching.add(it) }
                }
            }
        }
        return matching
    }
}

class IntGrid(override val contents: List<MutableList<Int>>) : Grid<Int>(contents) {
    override operator fun get(r: Int, c: Int): Int {
        return if (outOfBounds(r, c)) -1 else contents[r][c]
    }
}

class CharGrid(override val contents: List<MutableList<Char>>) : Grid<Char>(contents)

data class GridSquare<T>(val row: Int, val col: Int, var value: T) {
    var shortestPath: List<GridSquare<T>> = emptyList()

    override fun toString(): String {
        return "[$row,$col]=$value"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridSquare<*>

        if (row != other.row) return false
        if (col != other.col) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }
}

/**
 * SimpleGrid<T>, for when the dimensions need to be more flexible than List/Arrays provide
 * Int-based x,y coordinates
 */
abstract class SimpleGrid<T>(
    var minX: Int,
    var maxX: Int,
    var minY: Int,
    var maxY: Int
) {
    val points: MutableMap<Int, MutableMap<Int, T>> = mutableMapOf()

    fun add(x: Int, y: Int, value: T) {
        updateMinMax(x, y)
        points.getOrPut(y) { mutableMapOf() }[x] = value
    }

    fun get(x: Int, y: Int): T? {
        return points[y]?.get(x)
    }

    private fun updateMinMax(x: Int, y: Int) {
        updateMinMaxX(x)
        updateMinMaxY(y)
    }

    private fun updateMinMaxX(x: Int) {
        if (x < minX) minX = x
        if (x > maxX) maxX = x
    }

    private fun updateMinMaxY(y: Int) {
        if (y > maxY) maxY = y
        if (y < minY) minY = y
    }

    fun print() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (points[y]?.get(x) != null) {
                    output(points[y]?.get(x))
                } else {
                    outputUnoccupied(".")
                }
            }
            println()
        }
    }

    open fun output(contents: T?) {
        contents?.let { print(" $it ") }
    }

    open fun outputUnoccupied(s: Any?) {
        s?.let { print(" $it ") }
    }
}

fun List<IntRange>.collapse(): List<IntRange> =
    if (this.size <= 1) this
    else {
        val sorted = this.sortedBy { it.first }
        sorted.drop(1).fold(mutableListOf(sorted.first())) { reduced, range ->
            val lastRange = reduced.last()
            if (range.first <= lastRange.last)
                reduced[reduced.lastIndex] = (lastRange.first..maxOf(lastRange.last, range.last))
            else
                reduced.add(range)
            reduced
        }
    }