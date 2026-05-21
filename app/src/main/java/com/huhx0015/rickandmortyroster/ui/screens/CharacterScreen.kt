package com.huhx0015.rickandmortyroster.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.huhx0015.rickandmortyroster.RMState
import com.huhx0015.rickandmortyroster.data.RMCharacter
import kotlin.random.Random

@Composable
fun CharacterScreen(
    state: RMState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = state.characterList,
            key = { it.id }
        ) { character ->
            CharacterRow(character = character)
        }
    }
}

@Composable
private fun CharacterRow(
    character: RMCharacter,
    modifier: Modifier = Modifier
) {
  Row(modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
      AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
              .data(character.image)
              .crossfade(true)
              .build(),
          contentDescription = "",
          contentScale = ContentScale.Crop,
          modifier = Modifier.clip(CircleShape),
      )
      Column() {
          Text(text = character.name)
          Text(text = character.status)
          Text(text = character.species)
          Text(text = character.gender)
      }
  }
}

@Preview
@Composable
private fun CharacterRowPreview() {
    CharacterRow(
        character = RMCharacter(
            id = Random.nextInt(),
            name = "Rick",
            species = "Human",
            gender = "Male",
            status = "Blah",
            image = ""
        )
    )
}