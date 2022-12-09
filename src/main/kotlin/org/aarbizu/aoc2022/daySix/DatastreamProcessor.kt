package org.aarbizu.aoc2022.daySix

class DatastreamProcessor(private val windowSize: Int) {

    fun ingest(buffer: String): Int {
        var lookbackWindow = mutableListOf<Char>()

        var pos = 0
        buffer.forEach {
            if (lookbackWindow.size < windowSize) {
                lookbackWindow.add(it)
                pos++
            } else {
                if (noneMatch(lookbackWindow)) {
                    return pos
                } else {
                    lookbackWindow = lookbackWindow.slice(1 until windowSize).toMutableList()
                    lookbackWindow.add(it)
                    pos++
                }
            }
        }
        return -1
    }

    fun ingest2(buffer: String): Pair<String,Int> {
        return buffer
            .windowed(windowSize)
            .withIndex().first { noneMatch(it.value, it.index).first }
            .let { println("found ${it.value} at ${it.index + windowSize}"); Pair(it.value, it.index + windowSize) }
    }

    private fun noneMatch(s: String, i: Int): Pair<Boolean,Int> {
       return Pair(noneMatch(s.toList()), i)
    }

    private fun noneMatch(chars: List<Char>): Boolean {
        val map = mutableMapOf<Char, Int>()
        for (c in chars) {
            map.putIfAbsent(c, 0)
            map[c] = map[c]!! + 1
        }
        return map.values.all { it == 1 }
    }
}
