package org.aarbizu.aoc2022.dayEleven

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class MonkeyBusinessTest {

    @Test
    fun `parsing monkey actions`() {
        val monkeyChunks = sample.lines().filter { it.isNotBlank() }.chunked(6)
        val monkeyBiz = MonkeyBusiness()
        val barrelOMonkeys = monkeyChunks.map {
            monkeyBiz.parseMonkey(it)
        }.toList()

        assertThat(barrelOMonkeys.size).isEqualTo(4)
    }

    @Test
    fun `run sample`() {
        val monkeyChunks = sample.lines().filter { it.isNotBlank() }.chunked(6)
        val monkeyBiz = MonkeyBusiness()
        val barrelOMonkeys = monkeyChunks.map {
            monkeyBiz.parseMonkey(it)
        }.toList()

        monkeyBiz.monkeyAround(20, barrelOMonkeys)
        assertThat(barrelOMonkeys[0].items.size).isEqualTo(5)
        val business = barrelOMonkeys
            .sortedBy { -it.inspections }
            .take(2)
            .map { it.inspections }
            .reduceRight { a, b -> a * b }

        assertThat(business).isEqualTo(10605).also { println("monkey business factor $business") }
    }

    @Test
    fun `part 1`() {
        val monkeyChunks = File("$INPUTS_DIR/d11-p1.txt")
            .readLines()
            .filter { it.isNotBlank() }
            .chunked(6)

        val monkeyBiz = MonkeyBusiness()
        val barrelOMonkeys = monkeyChunks.map { monkeyBiz.parseMonkey(it) }.toList()

        monkeyBiz.monkeyAround(20, barrelOMonkeys)
        val business = barrelOMonkeys
            .sortedBy { -it.inspections }
            .take(2)
            .map { it.inspections }
            .reduceRight { a, b -> a * b }

        assertThat(business).isEqualTo(113220).also { println("monkey business factor $business") }
    }

    @Test
    fun `simulate sample 10k`() {
        val monkeyChunks = sample.lines().filter { it.isNotBlank() }.chunked(6)
        val monkeyBiz = MonkeyBusiness()
        val barrelOMonkeys = monkeyChunks.map {
            monkeyBiz.parseMonkey(it)
        }.toList()

        monkeyBiz.monkeyAround(10000, barrelOMonkeys, false)
        val business = barrelOMonkeys
            .sortedBy { -it.inspections }
            .take(2)
            .map { it.inspections }
            .reduceRight { a, b -> a * b }

        assertThat(business).isEqualTo(2713310158).also { println("monkey business factor $business") }
    }

    @Test
    fun `part 2`() {
        val monkeyChunks = File("$INPUTS_DIR/d11-p1.txt")
            .readLines()
            .filter { it.isNotBlank() }
            .chunked(6)

        val monkeyBiz = MonkeyBusiness()

        val barrelOMonkeys = monkeyChunks.map {
            monkeyBiz.parseMonkey(it)
        }.toList()

        monkeyBiz.monkeyAround(10000, barrelOMonkeys, false)
        val business = barrelOMonkeys
            .sortedBy { -it.inspections }
            .take(2)
            .map { it.inspections }
            .reduceRight { a, b -> a * b }

        assertThat(business).isEqualTo(30599555965).also { println("monkey business factor $business") }
    }

    private val sample = """
        Monkey 0:
            Starting items: 79, 98
            Operation: new = old * 19
            Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

        Monkey 1:
            Starting items: 54, 65, 75, 74
            Operation: new = old + 6
            Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0

        Monkey 2:
            Starting items: 79, 60, 97
            Operation: new = old * old
            Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3

        Monkey 3:
            Starting items: 74
            Operation: new = old + 3
            Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
    """.trimIndent()
}
