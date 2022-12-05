package org.aarbizu.aoc2022.dayThree

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class RucksacksTest {

    @Test
    fun `part1`() {
        val rucksacks = File("$INPUTS_DIR/d3-p1.txt").readLines()
        val sumMisplacedItems = Rucksacks().sumMisplacedItems(rucksacks)

        println("sum of misplaced = $sumMisplacedItems")
        assertThat(sumMisplacedItems).isEqualTo(8233)
    }

    @Test
    fun `part2`() {
        val rucksacks = File("$INPUTS_DIR/d3-p1.txt").readLines()
        val trios = rucksacks.chunked(3).map { ElfTrio(it) }.toList()
        val sumOfTriBadges = Rucksacks().sumGroupBadgeItems(trios)

        println("sum of trios badges = $sumOfTriBadges")
        assertThat(sumOfTriBadges).isEqualTo(2821)
    }

    @Test
    fun `per trio unique items`() {
        val group1 = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
        """.trimIndent().split("\n")

        val elfTrio = ElfTrio(group1)

        val rucksacks = Rucksacks()
        var commonItem = rucksacks.sumGroupBadgeItems(listOf(elfTrio))
        assertThat(commonItem).isEqualTo(priorities['r'])
        println("$commonItem")

        val group2 = """
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().split("\n")

        val elfTrio2 = ElfTrio(group2)
        commonItem = rucksacks.sumGroupBadgeItems(listOf(elfTrio2))
        assertThat(commonItem).isEqualTo(priorities['Z'])
    }

    @Test
    fun `find dupe`() {
        val input = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val sumMisplacedItems = Rucksacks().sumMisplacedItems(listOf(input))
        assertThat(sumMisplacedItems).isEqualTo(16)
    }

    @Test
    fun `check compartments`() {
        val input = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val c1 = input.substring(0 until input.length / 2)
        val c2 = input.substring(input.length / 2 until input.length)

        assertThat(c1.length == c2.length).also { println("lengths: $c1 $c2") }

        println("$c1 $c2 ${c1.toSet().intersect(c2.toSet())}")
    }
}
