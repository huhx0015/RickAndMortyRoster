package com.huhx0015.rickandmortyroster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.huhx0015.rickandmortyroster.ui.screens.CharacterScreen
import com.huhx0015.rickandmortyroster.ui.theme.AndroidInterviewTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidInterviewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CGAppNavHost(navController = rememberNavController())
                    }
                }
            }
        }
    }
}


@Composable
fun CGAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Characters.route,
) {
    val viewModel: RMViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
       // Add screen paths here
        composable(NavigationItem.Characters.route) {
            CharacterScreen(state = state.value)
        }
    }
}

enum class Screen {
    Characters,
    CharacterDetail,
}

@Serializable
sealed class NavigationItem(val route: String) {
    @Serializable
    data object Characters : NavigationItem(Screen.Characters.name)
    @Serializable
    data object CharacterDetail : NavigationItem(Screen.CharacterDetail.name)
}
