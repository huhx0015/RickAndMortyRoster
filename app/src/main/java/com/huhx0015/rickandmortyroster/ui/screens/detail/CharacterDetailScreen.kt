package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.huhx0015.rickandmortyroster.R
import kotlin.random.Random

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

  CharacterDetailPortrait(
    imageUrl = state.value.character?.image
  )
}

@Composable
private fun CharacterDetailPortrait(
  imageUrl: String?,
  modifier: Modifier = Modifier
) {
  AsyncImage(
    modifier = modifier
      .size(128.dp)
      .clip(CircleShape),
    model = ImageRequest.Builder(LocalContext.current)
      .data(imageUrl)
      .crossfade(true)
      .placeholder(R.mipmap.ic_launcher_round)
      .build(),
    alignment = Alignment.Center,
    contentDescription = String(),
    contentScale = ContentScale.Crop
  )
}

@Preview
@Composable
private fun CharacterDetailScreenPreview() {
  CharacterDetailScreen(
    characterId = Random.nextInt()
  )
}