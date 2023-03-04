package pl.przychodzki.krzysztof.math4kids.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.przychodzki.krzysztof.math4kids.R
import pl.przychodzki.krzysztof.math4kids.data.GameAction
import pl.przychodzki.krzysztof.math4kids.data.GameState
import pl.przychodzki.krzysztof.math4kids.ui.GameViewModel

@Composable
fun PlayScreen(
    state: GameState,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Scores(state = state)
            Question(Pair(state.a, state.b), state.opSymbol)
            Possibilities(state = state, onAction = viewModel::onAction)
            Info(state)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayScreenPreview() {
    PlayScreen(GameState(), GameViewModel())
}


@Composable
fun Scores(state: GameState, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Text(
                text = stringResource(R.string.correct),
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = state.correct.toString(),
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Right
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(
                text = stringResource(R.string.games_count),
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = state.games.toString(),
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Right
            )
        }
    }
}


@Composable
fun Question(values: Pair<Int, Int>, symbol: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.width(24.dp))
    Text(
        text = stringResource(R.string.what_is_result), fontSize = 24.sp,
        color = Color.Black
    )
    Spacer(modifier = Modifier.width(24.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        QuestionButton(
            value = values.first.toString(), modifier = Modifier
                .aspectRatio(1f)
                .weight(1f)
        )
        QuestionButton(
            value = symbol, modifier = Modifier
                .aspectRatio(1f)
                .weight(1f)
        )
        QuestionButton(
            value = values.second.toString(), modifier = Modifier
                .aspectRatio(1f)
                .weight(1f)
        )
    }
}


@Composable
fun Possibilities(state: GameState, modifier: Modifier = Modifier, onAction: (GameAction) -> Unit) {
    val things = state.posAns
    val shouldAnswer = state.ans
    Text(
        text = stringResource(R.string.Choose_your_answer),
        fontSize = 24.sp,
        color = Color.Black
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GuessButton(
            value = things.first,
            onClick = { onAction(GameAction.GuessAnswer(things.first)) },
            modifier = Modifier
                .background(Color.LightGray)
                .aspectRatio(1f)
                .weight(1f)
        )
        GuessButton(
            value = things.second,
            onClick = { onAction(GameAction.GuessAnswer(things.second)) },
            modifier = Modifier
                .background(Color.LightGray)
                .aspectRatio(1f)
                .weight(1f)
        )
        GuessButton(
            value = things.third,
            onClick = { onAction(GameAction.GuessAnswer(things.third)) },
            modifier = Modifier
                .background(Color.LightGray)
                .aspectRatio(1f)
                .weight(1f)
        )
    }
}

@Composable
fun Info(state: GameState, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    if (state.wasItGood == null) return
    val toastMsg =
        if (state.wasItGood) stringResource(id = R.string.Brawo) else stringResource(id = R.string.TryAgain)
    Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
}

@Composable
fun GuessButton(
    value: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .then(modifier)
    ) {
        Text(
            text = value.toString(),
            fontSize = 36.sp
        )
    }
}

@Composable
fun QuestionButton(
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RectangleShape)
            .then(modifier)
    ) {
        Text(
            text = value,
            fontSize = 36.sp
        )
    }
}

