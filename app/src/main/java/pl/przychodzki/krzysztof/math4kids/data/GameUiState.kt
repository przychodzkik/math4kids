package pl.przychodzki.krzysztof.math4kids.data

import pl.przychodzki.krzysztof.math4kids.data.OperationRange.Add
import pl.przychodzki.krzysztof.math4kids.data.OperationRange.Divide
import pl.przychodzki.krzysztof.math4kids.data.OperationRange.Multiply
import pl.przychodzki.krzysztof.math4kids.data.OperationRange.Subtract

data class GameUiState(
    val operationRange: OperationRange = Add,
    val correctAnswers: Int = 0,
    val gamesCount: Int = 0
)

sealed class OperationRange(val range: IntRange, val symbol: String) {
    object Add : OperationRange(1..50, "+")
    object Subtract : OperationRange(1..20, "-")
    object Multiply : OperationRange(1..10, "*")
    object Divide : OperationRange(1..10, "/")
}


class Operation(private val operationRange: OperationRange) {
    private var a: Int = 0
    private var b: Int = 0

    fun getValues(): Pair<Int, Int> {
        a = operationRange.range.random()
        b = operationRange.range.random()

        return when (operationRange) {
            Subtract, Divide -> {
                if (a < b) {
                    val c = a
                    a = b
                    b = c
                }
                Pair(a, b)
            }

            else -> Pair(a, b)
        }
    }

    fun getAnswer(): Int {
        return when (operationRange) {
            Add -> a + b
            Subtract -> a - b
            Multiply -> a * b
            Divide -> a / b
        }
    }

    fun getSymbol(): String = operationRange.symbol

    fun getPossibleAnswers(): Triple<Int, Int, Int> {
        val answer = getAnswer()
        val factor = (1..10).random()
        val first = if (answer - factor < 1) 1 else answer - factor
        val third = answer + factor
        val list = listOf(first, answer, third).shuffled()
        return list.toTriple()
    }

    private fun List<Int>.toTriple() = Triple(this[0], this[1], this[2])
}