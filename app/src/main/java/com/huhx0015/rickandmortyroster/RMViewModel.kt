package com.huhx0015.rickandmortyroster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.huhx0015.rickandmortyroster.api.toCGCharacterList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RMViewModel @Inject constructor(
    val api: RickAndMortyApi
) : ViewModel() {

    private val _state: MutableStateFlow<RMState> = MutableStateFlow(RMState())
    val state: StateFlow<RMState> = _state.asStateFlow()

    init {
        initData()
    }

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            // MOCK DATA:
//            val characterList = listOf(
//                CGCharacter(
//                    id = Random.nextInt(),
//                    name = "Rick",
//                    species = "Human",
//                    gender = "Male",
//                    status = "Blah",
//                    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
//                ),
//                CGCharacter(
//                    id = Random.nextInt(),
//                    name = "Morty",
//                    species = "Human",
//                    gender = "Male",
//                    status = "Blah",
//                    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
//                ),
//                CGCharacter(
//                    id = Random.nextInt(),
//                    name = "Rick",
//                    species = "Human",
//                    gender = "Male",
//                    status = "Blah",
//                    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
//                )
//            )

            val characterList = api.getCharacters().toCGCharacterList()


            _state.update { state ->
                state.copy(
                    characterList = characterList
                )
            }
        }
    }
}

