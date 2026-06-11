package com.huhx0015.rickandmortyroster.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huhx0015.rickandmortyroster.data.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CharacterListState> = MutableStateFlow(
        CharacterListState()
    )
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    companion object {
        private const val VAL_INITIAL_PAGE = 1
    }

    init {
        initObserver()
        initData()
    }

    private fun initObserver() {
        viewModelScope.launch {
            repository.characterListStateFlow.collectLatest { characterList ->
                _state.update { state ->
                    state.copy(characterList = characterList)
                }
            }
        }
    }

    private fun initData() {
        _state.update { it.copy(isLoading = true, isError = false) }

        if (repository.isCharacterListEmpty()) {
            loadData(page = VAL_INITIAL_PAGE)
        } else {
            _state.update { state ->
                state.copy(
                    characterList = repository.getCharacterList(),
                    isLoading = false,
                    isError = false
                )
            }
        }
    }

    private fun loadData(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.loadCharacters(page = page)
            }.onSuccess {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        isError = false
                    )
                }
            }.onFailure {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun loadMoreData() {
        val nextPage = state.value.currentPage + 1

        _state.update { state ->
            state.copy(
                currentPage = nextPage,
                isLoadingMore = true
            )
        }

        loadData(page = nextPage)
    }
}