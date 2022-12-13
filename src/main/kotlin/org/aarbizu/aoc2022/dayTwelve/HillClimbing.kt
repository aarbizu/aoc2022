package org.aarbizu.aoc2022.dayTwelve

import org.aarbizu.aoc2022.util.CharGrid
import org.aarbizu.aoc2022.util.GridSquare

class HillClimbing {

    fun ascend(
        elevationMap: CharGrid,
        startValue: Char,
        destinationValue: Char
    ): Int {
        val start = elevationMap.search { it == startValue }.first()
        return ascendInner(elevationMap, start, destinationValue)
    }

    private fun ascendInner(
        elevationMap: CharGrid,
        start: GridSquare<Char>,
        destinationValue: Char
    ): Int {
        val discovered = mutableMapOf<GridSquare<Char>, Boolean>()
        elevationMap.getGridSet().map { discovered[it] = false }
        val parent = mutableMapOf<GridSquare<Char>, GridSquare<Char>>()

        // bfs
        val toVisit = mutableListOf<GridSquare<Char>>()
        discovered[start] = true
        toVisit.add(start)

        while (toVisit.isNotEmpty()) {
            val vertex = toVisit.removeLast()
            val vertexElevation = if (vertex.value == 'S') 'a' else if (vertex.value == 'E') 'z' else vertex.value
            val neighbors = elevationMap.getNonDiagonalNeighbors(vertex.row, vertex.col).filterNotNull().toMutableList()
            while (neighbors.isNotEmpty()) {
                val next = neighbors.removeLast()
                val nextElevation = if (next.value == 'S') 'a' else if (next.value == 'E') 'z' else next.value
                if (nextElevation - vertexElevation <= 1) {
                    if (discovered[next] == false) {
                        discovered[next] = true
                        parent[next] = vertex
                        toVisit.add(0, next)
                    }
                }
            }
        }

//        parent.map {
//            println("${it.key} (${it.key.value}) -> ${it.value} (${it.value.value})")
//        }

        val end = elevationMap.search { v: Char -> v == destinationValue }.first()
        val path = mutableListOf<GridSquare<Char>>()
        findPathTo(end, start.value, parent, path)
//        println("path: ${path.size} => ${path.joinToString(" ")}")
        return path.size
    }

    private fun findPathTo(
        end: GridSquare<Char>,
        startValue: Char,
        parent: Map<GridSquare<Char>, GridSquare<Char>>,
        path: MutableList<GridSquare<Char>>
    ): List<GridSquare<Char>> {
        if (parent.containsKey(end)) {
            val node = parent.entries.first { it.key == end }
            if (node.value.value == startValue) {
                path.add(node.value)
                return path
            }

            path.add(node.value)
            return findPathTo(node.value, startValue, parent, path)
        } else {
            return path
        }
    }

    fun findShortestFromAny(grid: CharGrid, start: Char, end: Char): List<Int> {
        val startingPoints = grid.search { it == start }
        return startingPoints.map { ascendInner(grid, it, end) }.filterNot { it == 0 }.toList()
    }
}
