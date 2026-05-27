package com.huhx0015.rickandmortyroster.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationItem(val route: String) {
  @Serializable
  data object Characters : NavigationItem(Screen.CharacterList.name)
  @Serializable
  data object CharacterDetail : NavigationItem(Screen.CharacterDetail.name)
}
