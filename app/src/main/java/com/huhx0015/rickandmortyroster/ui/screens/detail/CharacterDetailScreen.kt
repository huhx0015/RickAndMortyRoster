package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CharacterDetailScreen(
  characterId: Int,
  modifier: Modifier = Modifier
) {
  val viewModel: CharacterDetailViewModel = hiltViewModel()
  val state = viewModel.state.collectAsStateWithLifecycle()

  LaunchedEffect(characterId) {
    viewModel.loadCharacterData(characterId)
  }
}