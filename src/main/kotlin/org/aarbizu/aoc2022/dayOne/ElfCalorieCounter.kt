package org.aarbizu.aoc2022.dayOne

class ElfCalorieCounter(private val inventory: List<String>) {

    fun findMaximumLoad(): Long {
        var payload = ElfPayload()
        var maxPayload = payload
        inventory.forEach {
            if (it.isEmpty()) {
                if (payload.total > maxPayload.total) {
                    maxPayload = payload
                }
                payload = ElfPayload()
            } else {
                payload.total += it.toLong()
            }
        }
        return maxPayload.total
    }

    fun findTopNLoad(numElves: Int): Long {
        val topElves = mutableListOf<ElfPayload>()
        var payload = ElfPayload()
        inventory.forEach {
            if (it.isEmpty()) {
                if (topElves.size < numElves) {
                    topElves.add(payload)
                    payload = ElfPayload()
                    return@forEach
                }
                if (topElves.any { topElf -> topElf.total < payload.total }) {
                    topElves.sortBy { it.total }
                    topElves.removeFirst()
                    topElves.add(payload)
                }
                payload = ElfPayload()
            } else {
                payload.total += it.toLong()
            }
        }
        return topElves.sumOf { it.total }
    }

    data class ElfPayload(var total: Long = 0) {
        override fun toString(): String {
            return total.toString()
        }
    }
}
