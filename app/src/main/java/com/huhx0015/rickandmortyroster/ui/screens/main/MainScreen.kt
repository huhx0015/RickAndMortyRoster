package com.huhx0015.rickandmortyroster.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.huhx0015.rickandmortyroster.R
import com.huhx0015.rickandmortyroster.navigation.NavigationItem
import com.huhx0015.rickandmortyroster.ui.screens.detail.CharacterDetailScreen
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListScreen
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  startDestination: String = NavigationItem.Characters.route,
) {
  val viewModel: CharacterListViewModel = viewModel()
  val state = viewModel.state.collectAsStateWithLifecycle()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.SemiBold,
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
      )
    },
  ) { innerPadding ->
    NavHost(
      modifier = modifier.padding(innerPadding),
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
}