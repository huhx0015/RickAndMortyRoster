package com.huhx0015.rickandmortyroster.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.huhx0015.rickandmortyroster.R
import com.huhx0015.rickandmortyroster.model.RMCharacter
import kotlin.random.Random

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    state: CharacterListState,
    rowClickAction: () -> Unit
) {
    when {
        state.isLoading -> CharacterListLoading(modifier = modifier)
        state.characterList.isEmpty() ->
            CharacterListEmptyError(message = stringResource(R.string.empty_character_message))
        state.isError ->
            CharacterListEmptyError(message = stringResource(R.string.error_message))
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
            ) {
                items(
                    items = state.characterList,
                    key = { it.id },
                ) { character ->
                    CharacterListRow(
                        character = character,
                        rowClickAction = rowClickAction
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterListRow(
    modifier: Modifier = Modifier,
    character: RMCharacter,
    rowClickAction: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight(),
        colors = CardColors(
          containerColor = Color.White,
          contentColor = Color.White,
          disabledContainerColor = Color.Gray,
          disabledContentColor = Color.Gray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentHeight()
                .clickable { rowClickAction.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(96.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .build(),
                alignment = Alignment.Center,
                contentDescription = String(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
            ) {
                Text(
                    text = character.name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = character.species,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = character.gender,
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = character.status.capitalize(LocalLocale.current.platformLocale),
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
private fun CharacterListLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CharacterListEmptyError(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun CharacterListRowPreview() {
    CharacterListRow(
        character = RMCharacter(
            id = Random.nextInt(),
            name = "Rick",
            species = "Human",
            gender = "Male",
            status = "Blah",
            image = ""
        ),
        rowClickAction = {}
    )
}

@Preview
@Composable
private fun CharacterListLoadingPreview() {
    CharacterListLoading()
}

@Preview
@Composable
private fun CharacterListEmptyErrorPreview() {
    CharacterListEmptyError(
        message = stringResource(R.string.empty_character_message)
    )
    CharacterListEmptyError(
        message = stringResource(R.string.error_message)
    )
}