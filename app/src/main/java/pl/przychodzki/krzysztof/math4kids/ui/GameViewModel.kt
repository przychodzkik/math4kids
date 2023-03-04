package pl.przychodzki.krzysztof.math4kids.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.przychodzki.krzysztof.math4kids.data.GameAction
import pl.przychodzki.krzysztof.math4kids.data.GameState
import pl.przychodzki.krzysztof.math4kids.data.GameUiState
import pl.przychodzki.krzysztof.math4kids.data.Operation
import pl.przychodzki.krzysztof.math4kids.data.OperationRange

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var state by mutableStateOf(GameState())
        private set


    fun setOperation(operationRange: OperationRange) {
        state = state.copy(operationRange = operationRange, wasItGood = null)
        reset()
        newGame()
    }


    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.GuessAnswer -> checkIfCorrect(action.guessingNumber, state.ans)
        }
    }

    fun reset() {
        state = state.copy(
            correct = 0,
            games = 0,
            a = 0,
            b = 0,
            ans = 0,
            posAns = Triple(0, 0, 0),
            opSymbol = "", wasItGood = null
        )
    }

    private fun checkIfCorrect(result: Int, answer: Int) {
        val oldCorrect = state.correct
        val oldGames = state.games
        if (result == answer) {
            state = state.copy(
                correct = oldCorrect + 1,
                games = oldGames + 1,
                wasItGood = true
            )
            newGame()
        } else {
            val pA = state.posAns.toList().shuffled()
            state = state.copy(games = oldGames + 1, posAns = pA.toTriple(), wasItGood = false)
        }
    }

    private fun List<Int>.toTriple() = Triple(this[0], this[1], this[2])

    private fun newGame() {
        val operation = Operation(state.operationRange)
        val vals = operation.getValues()
        val answer = operation.getAnswer()
        val posAnswer = operation.getPossibleAnswers()
        val symbol = operation.getSymbol()
        state = state.copy(
            a = vals.first,
            b = vals.second,
            ans = answer,
            posAns = posAnswer,
            opSymbol = symbol
        )
    }

}