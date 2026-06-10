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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.huhx0015.rickandmortyroster.R
import com.huhx0015.rickandmortyroster.navigation.NavigationItem
import com.huhx0015.rickandmortyroster.ui.screens.detail.CharacterDetailScreen
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  startDestination: NavigationItem = NavigationItem.Characters,
) {
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
      composable<NavigationItem.Characters> {
        CharacterListScreen(
          rowClickAction = { characterId ->
            navController.navigate(
              route = NavigationItem.CharacterDetail(characterId = characterId)
            )
          }
        )
      }
      composable<NavigationItem.CharacterDetail> { backStackEntry ->
        val characterDetail: NavigationItem.CharacterDetail = backStackEntry.toRoute()
        CharacterDetailScreen(characterId = characterDetail.characterId)
      }
    }
  }
}