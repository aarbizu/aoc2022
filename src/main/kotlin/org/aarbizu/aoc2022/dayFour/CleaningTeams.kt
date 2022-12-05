package org.aarbizu.aoc2022.dayFour

class CleaningTeams {
    fun getCountOfFullyOverlappingTeams(teams: List<Team>): Int {
        return teams.filter {
            team -> team.oneRangeFullyContainsTheOther()
        }.count()
    }

    fun getCountOfAnyOverappingTeams(teams: List<Team>): Int {
        return teams.filter {
            team -> team.anyOverlapBetweenTeams()
        }.count()
    }
}

data class Team(var rangeOne: IntRange, var rangeTwo: IntRange) {

    fun oneRangeFullyContainsTheOther(): Boolean {
        return rangeOne.all { rangeTwo.contains(it) } || rangeTwo.all { rangeOne.contains(it) }
    }

    fun anyOverlapBetweenTeams(): Boolean {
        return rangeOne.any { rangeTwo.contains(it) } || rangeTwo.any { rangeOne.contains(it) }
    }

    companion object {
        private val teamRegex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

        fun fromAssignment(assignment: String): Team {
            val (start1, end1, start2, end2) = teamRegex.find(assignment)!!.destructured
            return Team((start1.toInt() .. end1.toInt()), (start2.toInt() .. end2.toInt()))
        }
    }

}