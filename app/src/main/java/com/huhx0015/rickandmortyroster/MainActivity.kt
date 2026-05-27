package com.huhx0015.rickandmortyroster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.huhx0015.rickandmortyroster.ui.screens.detail.CharacterDetailScreen
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListScreen
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListViewModel
import com.huhx0015.rickandmortyroster.ui.theme.RickMortyRosterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyRosterTheme {
                RMAppNavHost(navController = rememberNavController())
            }
        }
    }
}


@Composable
fun RMAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Characters.route,
) {
    val viewModel: CharacterListViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Characters.route) {
            CharacterListScreen(state = state.value)
        }
        composable(NavigationItem.CharacterDetail.route) {
            CharacterDetailScreen(state = state.value)
        }
    }
}

enum class Screen {
    CharacterList,
    CharacterDetail,
}

@Serializable
sealed class NavigationItem(val route: String) {
    @Serializable
    data object Characters : NavigationItem(Screen.CharacterList.name)
    @Serializable
    data object CharacterDetail : NavigationItem(Screen.CharacterDetail.name)
}
