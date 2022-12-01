package org.aarbizu.aoc2022.day_one

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class ElfCalorieCounterTest {

    @Test
    fun `get max calories`() {
        val inventory = File("$INPUTS_DIR/d1-p1.txt").readLines()
        val maxLoad = ElfCalorieCounter(inventory).findMaximumLoad()
        println("maximum load = $maxLoad")
        assertThat(maxLoad).isEqualTo(69836)
    }

    @Test
    fun `get max of top three`() {
        val inventory = File("$INPUTS_DIR/d1-p1.txt").readLines()
        val maxLoad = ElfCalorieCounter(inventory).findTopNLoad(3)
        println("maximum load of top 3 elves = $maxLoad")
        assertThat(maxLoad).isEqualTo(207968)
    }
}