package org.aarbizu.aoc2022.day_two

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class RockPaperScissorsTest {
    @Test
    fun `part one`() {
        val strategyGuide = File("$INPUTS_DIR/d2-p1.txt").readLines()
        val parsedStrategyGuide = strategyGuide
            .map { it.split(" ") }
            .map { Pair(getMove(it[0]), getMove(it[1])) }
            .toList()
        val score = RockPaperScissors().evaluateGuide(parsedStrategyGuide)
        println("score = $score")
        assertThat(score).isEqualTo(14163)
    }

    @Test
    fun `part two`() {
        val strategyGuide = File("$INPUTS_DIR/d2-p1.txt").readLines()
        val parsedStrategyGuide = strategyGuide
            .map { it.split(" ") }
            .map { Pair(getMove(it[0]), getResult(it[1])) }
            .toList()
        val score = RockPaperScissors().matchGuideRoundOutcomes(parsedStrategyGuide)
        println("score = $score")
    }
}