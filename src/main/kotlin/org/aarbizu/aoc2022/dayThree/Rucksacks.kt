package org.aarbizu.aoc2022.dayThree

val lowerPriorities = ('a'..'z').toList().zip((1..26).toList()).toMap()
val upperPriorities = ('A'..'Z').toList().zip((27..52).toList()).toMap()
val priorities = lowerPriorities.plus(upperPriorities)

class Rucksacks {

    fun sumMisplacedItems(rucksacks: List<String>): Long {
        return rucksacks.sumOf {
            findMisplaced(it)
        }
    }

    fun sumGroupBadgeItems(trios: List<ElfTrio>): Long {
        return trios.sumOf {
            it.getGroupBadge()
        }
    }

    private fun findMisplaced(sack: String): Long {
        val compartmentOne = sack.substring(0 until sack.length / 2).toSet()
        val compartmentTwo = sack.substring(sack.length / 2 until sack.length).toSet()
        val dup = compartmentOne.intersect(compartmentTwo)
        return priorities[dup.first()]?.toLong() ?: 0
    }
}

data class ElfTrio(val rucksacks: List<String>) {
    init {
        require(rucksacks.size == 3) { "group size must be three" }
    }

    fun getGroupBadge(): Long {
        val rucksack1 = rucksacks[0].toSet()
        val rucksack2 = rucksacks[1].toSet()
        val rucksack3 = rucksacks[2].toSet()

        return priorities[rucksack1.intersect(rucksack2.intersect(rucksack3)).first()]?.toLong() ?: 0
    }
}
