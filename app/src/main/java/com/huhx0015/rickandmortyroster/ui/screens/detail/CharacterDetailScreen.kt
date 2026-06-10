package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListState
import com.huhx0015.rickandmortyroster.ui.screens.list.CharacterListViewModel

@Composable
fun CharacterDetailScreen(
  modifier: Modifier = Modifier
) {
  val viewModel: CharacterDetailViewModel = hiltViewModel()
  val state = viewModel.state.collectAsStateWithLifecycle()
}