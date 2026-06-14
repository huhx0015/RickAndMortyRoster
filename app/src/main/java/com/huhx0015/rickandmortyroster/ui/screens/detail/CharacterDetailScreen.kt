package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
  val character = state.value.character

  LaunchedEffect(characterId) {
    viewModel.loadCharacterData(characterId)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(all = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    CharacterDetailPortrait(
      imageUrl = character?.image
    )
    CharacterDetailInformation(
      modifier = Modifier.padding(top = 16.dp),
      characterName = character?.name,
      species = character?.species,
      gender = character?.gender,
      status = character?.status
    )
  }
}

@Composable
private fun CharacterDetailPortrait(
  imageUrl: String?,
  modifier: Modifier = Modifier
) {
  AsyncImage(
    modifier = modifier
      .size(256.dp)
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

@Composable
private fun CharacterDetailInformation(
  modifier: Modifier = Modifier,
  characterName: String?,
  species: String?,
  gender: String?,
  status: String?
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = characterName ?: "Unknown Character",
      color = Color.Black,
      fontSize = 32.sp,
      fontWeight = FontWeight.SemiBold,
      fontFamily = FontFamily.Cursive,
    )
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = species ?: "Unknown Species",
      color = Color.Black,
      fontSize = 20.sp,
      fontWeight = FontWeight.Normal
    )
    Text(
      text = gender ?: "Unknown Gender",
      color = Color.Black,
      fontSize = 20.sp,
      fontWeight = FontWeight.Normal
    )
    Text(
      text = status?.capitalize(LocalLocale.current.platformLocale) ?: "Unknown Status",
      color = Color.Black,
      fontSize = 20.sp,
      fontWeight = FontWeight.Normal,
      fontStyle = FontStyle.Italic
    )
  }
}

@Preview
@Composable
private fun CharacterDetailScreenPreview() {
  CharacterDetailScreen(
    characterId = Random.nextInt()
  )
}