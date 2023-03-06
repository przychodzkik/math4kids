package pl.przychodzki.krzysztof.math4kids.data

import pl.przychodzki.krzysztof.math4kids.R

object DataSource {
    val quantityOptions = listOf(
        Pair(R.string.addition, OperationRange.Add),
        Pair(R.string.subtraction, OperationRange.Subtract),
        Pair(R.string.multiplication, OperationRange.Multiply),
//        Pair(R.string.division, OperationRange.Divide)
    )
}