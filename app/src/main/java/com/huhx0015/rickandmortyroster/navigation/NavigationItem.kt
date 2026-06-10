package com.huhx0015.rickandmortyroster.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationItem {
  @Serializable
  data object Characters : NavigationItem

  @Serializable
  data class CharacterDetail(val characterId: Int) : NavigationItem
}
