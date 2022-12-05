package org.aarbizu.aoc2022.dayTwo

class RockPaperScissors {
    fun evaluateGuide(strategyGuide: List<Pair<Move, Move>>): Long {
        return strategyGuide.sumOf {
            playRound(it).toLong()
        }
    }

    fun matchGuideRoundOutcomes(strategyGuide: List<Pair<Move, Result>>): Long {
        return strategyGuide.sumOf {
            matchGuideForThisRound(it).toLong()
        }
    }

    /**
     * Pair<Opponent Move, Our Move>
     */
    private fun playRound(moves: Pair<Move, Move>): Int {
        return moves.second.value + moves.second.vs(moves.first).value
    }

    private fun matchGuideForThisRound(moveResult: Pair<Move, Result>): Int {
        return moveResult.second.value + moveResult.first.matchResult(moveResult.second).value
    }
}

sealed interface Move {
    val value: Int
    val moveToResult: Map<Move, Result>
    val resultToMove: Map<Result, Move>
    fun vs(opp: Move): Result { return moveToResult[opp]!! }
    fun matchResult(res: Result): Move { return resultToMove[res]!! }
}

sealed class Result(val value: Int)

object Win : Result(6)
object Draw : Result(3)
object Lose : Result(0)

object Rock : Move {
    override val value = 1
    override val moveToResult = mapOf(
        Scissors to Win,
        Rock to Draw,
        Paper to Lose
    )
    override val resultToMove = mapOf(
        Win to Paper,
        Draw to Rock,
        Lose to Scissors
    )
}

object Paper : Move {
    override val value = 2
    override val moveToResult = mapOf(
        Rock to Win,
        Paper to Draw,
        Scissors to Lose
    )
    override val resultToMove = mapOf(
        Win to Scissors,
        Draw to Paper,
        Lose to Rock
    )
}

object Scissors : Move {
    override val value = 3
    override val moveToResult = mapOf(
        Paper to Win,
        Scissors to Draw,
        Rock to Lose
    )
    override val resultToMove = mapOf(
        Win to Rock,
        Draw to Scissors,
        Lose to Paper
    )
}

fun getMove(encodedMove: String): Move {
    return when (encodedMove) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissors
        else -> throw Exception("unknown move!")
    }
}

fun getResult(encodedResult: String): Result {
    return when (encodedResult) {
        "X" -> Lose
        "Y" -> Draw
        "Z" -> Win
        else -> throw Exception("unknown result!")
    }
}
