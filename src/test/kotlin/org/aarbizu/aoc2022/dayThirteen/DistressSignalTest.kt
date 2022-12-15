package org.aarbizu.aoc2022.dayThirteen

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class DistressSignalTest {
    private val sample = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    @Test
    fun `parse sample`() {
        val compared = DistressSignal().comparePackets(sample.lines())
        println("$compared")
    }

    @Test
    fun `part one`() {
        val ordered = DistressSignal().comparePackets(File("$INPUTS_DIR/d13-p1.txt").readLines())
        assertThat(ordered).isGreaterThan(4894).also { println("$ordered") }
    }

    @Test
    fun `part two`() {
        val sorted = DistressSignal().sorted(File("$INPUTS_DIR/d13-p1.txt").readLines())
        println("$sorted")
    }
}
