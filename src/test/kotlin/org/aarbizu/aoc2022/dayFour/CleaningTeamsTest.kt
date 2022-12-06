package org.aarbizu.aoc2022.dayFour

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class CleaningTeamsTest {

    @Test
    fun `part1`() {
        val pairings = File("$INPUTS_DIR/d4-p1.txt").readLines()
        val cleanupTeams = pairings.map { Team.fromAssignment(it) }
        val overlappedTeams = CleaningTeams().getCountOfFullyOverlappingTeams(cleanupTeams)
        assertThat(overlappedTeams).isEqualTo(444).also { println("teams with overlapping assignments = $overlappedTeams") }
    }

    @Test
    fun `part2`() {
        val pairings = File("$INPUTS_DIR/d4-p1.txt").readLines()
        val cleanupTeams = pairings.map { Team.fromAssignment(it) }
        val overlappedTeams = CleaningTeams().getCountOfAnyOverappingTeams(cleanupTeams)
        assertThat(overlappedTeams).isEqualTo(801).also { println("teams with any overlap = $overlappedTeams") }
    }

    @Test
    fun `test team`() {
        val team = Team.fromAssignment("30-31,2-31")
        assertThat(team.rangeOne).isEqualTo((30..31))
        assertThat(team.rangeTwo).isEqualTo((2..31))
        assertThat(team.oneRangeFullyContainsTheOther()).isTrue()
    }
}
