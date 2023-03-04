package pl.przychodzki.krzysztof.math4kids.data

import pl.przychodzki.krzysztof.math4kids.data.OperationRange.Add

data class GameState(
    val correct: Int = 0,
    val games: Int = 0,
    val a: Int = 0,
    val b: Int = 0,
    val ans: Int = 0,
    val posAns: Triple<Int, Int, Int> = Triple(0, 0, 0),
    val opSymbol: String = "",
    val wasItGood: Boolean? = null,
    val operationRange: OperationRange = Add
)

sealed class GameAction {
    data class GuessAnswer(val guessingNumber: Int) : GameAction()
}