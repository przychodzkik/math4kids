package pl.przychodzki.krzysztof.math4kids.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.przychodzki.krzysztof.math4kids.data.DataSource.quantityOptions
import pl.przychodzki.krzysztof.math4kids.data.OperationRange

@Composable
fun StartScreen(
    quantityOptions: List<Pair<Int, OperationRange>>,
    onNextButtonClicked: (OperationRange) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Math4Kids", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        quantityOptions.forEach { item ->
            SelectOperationButton(
                labelResourceId = item.first,
                onClick = { onNextButtonClicked(item.second) }
            )
        }
    }
}


@Composable
fun SelectOperationButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(quantityOptions = quantityOptions, {})
}