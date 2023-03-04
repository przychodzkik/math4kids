package pl.przychodzki.krzysztof.math4kids

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.przychodzki.krzysztof.math4kids.data.DataSource.quantityOptions
import pl.przychodzki.krzysztof.math4kids.ui.GameViewModel
import pl.przychodzki.krzysztof.math4kids.ui.screens.PlayScreen
import pl.przychodzki.krzysztof.math4kids.ui.screens.StartScreen


enum class GameScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Play(title = R.string.play)
}

@Composable
fun GameAppBar(
    currentScreen: GameScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun GameApp(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = GameScreen.valueOf(
        backStackEntry?.destination?.route ?: GameScreen.Start.name
    )

    Scaffold(
        topBar = {
            GameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = GameScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = GameScreen.Start.name) {
                StartScreen(
                    quantityOptions = quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setOperation(it)
                        navController.navigate(GameScreen.Play.name)
                    }
                )
            }
            composable(route = GameScreen.Play.name) {
                val context = LocalContext.current
                PlayScreen(
                    state = viewModel.state,
                    viewModel = viewModel
                )
            }

        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: GameViewModel,
    navController: NavHostController
) {
    viewModel.reset()
    navController.popBackStack(GameScreen.Start.name, inclusive = false)
}