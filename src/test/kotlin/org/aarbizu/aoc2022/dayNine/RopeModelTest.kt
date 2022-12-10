package org.aarbizu.aoc2022.dayNine

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class RopeModelTest {

    @Test
    fun `part 1`() {
        val moves = File("$INPUTS_DIR/d9-p1.txt").readLines()
        val ropeModel = RopeModel(moves)
        ropeModel.processMoves(false)
        assertThat(ropeModel.tailVisits.size).isEqualTo(6023).also { println(ropeModel.tailVisits.size) }
    }

    @Test
    fun `part 2`() {
        val moves = File("$INPUTS_DIR/d9-p1.txt").readLines()
        val ropeModel = RopeModel(moves)
        ropeModel.processMoves2(false)
        assertThat(ropeModel.tailVisits.size).isEqualTo(2533).also { println(ropeModel.tailVisits.size) }
    }

    @Test
    fun `sample data`() {
        val ropeModel = RopeModel(sample.lines())
        ropeModel.processMoves(true)
        assertThat(ropeModel.tailVisits.size).isEqualTo(13)
    }

    @Test
    fun `sample data, ten knots`() {
        val ropeModel = RopeModel(sample.lines())
        ropeModel.processMoves2(true)
        assertThat(ropeModel.tailVisits.size).isEqualTo(1)
    }

    @Test
    fun `sample data2, ten knots`() {
        val ropeModel = RopeModel(sample2.lines())
        ropeModel.processMoves2(true)
        assertThat(ropeModel.tailVisits.size).isEqualTo(36)
    }

    @Test
    fun `test tracking`() {
        var h = Knot('h', 1, 0)
        var t = Knot('t', 0, 0)

        // overlaid
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()

        Right.process(h)
        assertThat(h.x).isEqualTo(2)
        assertThat(t.isAdjacentOrOverlaying(h)).isFalse()

        Right.process(t)
        assertThat(t.loc).isEqualTo(Pair(1, 0))
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()

        h = Knot('h', 0, 0)
        t = Knot('t', 0, 0)

        Up.process(h)
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()
        Right.process(h)
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()
        Right.process(h)
        assertThat(t.isAdjacentOrOverlaying(h)).isFalse()

        h = Knot('h', 0, 0)
        t = Knot('t', 0, 0)

        Left.process(h)
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()
        assertThat(h.loc).isEqualTo(Pair(-1, 0))
        Left.process(h)
        assertThat(t.isAdjacentOrOverlaying(h)).isFalse()

        h = Knot('h', 0, 0)
        t = Knot('t', 0, 0)
        Left.process(h)
        Down.process(h)
        assertThat(h.loc).isEqualTo(Pair(-1, -1))
        assertThat(t.isAdjacentOrOverlaying(h)).isTrue()
    }

    val sample = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    val sample2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()
}
