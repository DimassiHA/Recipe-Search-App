package dimassi.habib.search.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimassi.habib.common.utils.NetworkResult
import dimassi.habib.common.utils.UiText
import dimassi.habib.search.domain.model.Recipe
import dimassi.habib.search.domain.model.RecipeDetails
import dimassi.habib.search.domain.use_cases.DeleteRecipeUseCase
import dimassi.habib.search.domain.use_cases.GetRecipeDetailsUseCase
import dimassi.habib.search.domain.use_cases.InsertRecipeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow(dimassi.habib.search.screens.details.RecipeDetails.UiState())
    val uiState: StateFlow<dimassi.habib.search.screens.details.RecipeDetails.UiState> get() = _uiState.asStateFlow()

    private val _navigation =
        Channel<dimassi.habib.search.screens.details.RecipeDetails.Navigation>()
    val navigation: Flow<dimassi.habib.search.screens.details.RecipeDetails.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: dimassi.habib.search.screens.details.RecipeDetails.Event) {
        when (event) {
            is dimassi.habib.search.screens.details.RecipeDetails.Event.FetchRecipeDetails -> recipeDetails(
                event.id
            )

            dimassi.habib.search.screens.details.RecipeDetails.Event.GoToRecipeListScreen -> viewModelScope.launch {
                _navigation.send(dimassi.habib.search.screens.details.RecipeDetails.Navigation.GoToRecipeListScreen)
            }

            is dimassi.habib.search.screens.details.RecipeDetails.Event.DeleteRecipe -> {
                deleteRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)

            }

            is dimassi.habib.search.screens.details.RecipeDetails.Event.InsertRecipe -> {
                insertRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)
            }

            is dimassi.habib.search.screens.details.RecipeDetails.Event.GoToMediaPlayer -> {
                viewModelScope.launch {
                    _navigation.send(
                        dimassi.habib.search.screens.details.RecipeDetails.Navigation.GoToMediaPlayer(
                            event.youtubeUrl
                        )
                    )
                }
            }
        }
    }

    private fun recipeDetails(id: String) = getRecipeDetailsUseCase.invoke(id)
        .onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        dimassi.habib.search.screens.details.RecipeDetails.UiState(
                            error = UiText.RemoteString(result.message.toString())
                        )
                    }
                }

                is NetworkResult.Loading -> _uiState.update {
                    dimassi.habib.search.screens.details.RecipeDetails.UiState(
                        isLoading = true
                    )
                }

                is NetworkResult.Success -> _uiState.update {
                    dimassi.habib.search.screens.details.RecipeDetails.UiState(
                        data = result.data
                    )
                }

            }

        }.launchIn(viewModelScope)

    fun RecipeDetails.toRecipe(): Recipe {
        return Recipe(
            idMeal,
            strArea,
            strMeal,
            strMealThumb,
            strCategory,
            strTags,
            strYoutube,
            strInstructions
        )
    }

}

object RecipeDetails {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: RecipeDetails? = null
    )

    sealed interface Navigation {
        data object GoToRecipeListScreen : Navigation
        data class GoToMediaPlayer(val youtubeUrl: String) : Navigation
    }

    sealed interface Event {

        data class FetchRecipeDetails(val id: String) : Event

        data class InsertRecipe(val recipeDetails: RecipeDetails) : Event
        data class DeleteRecipe(val recipeDetails: RecipeDetails) : Event

        data object GoToRecipeListScreen : Event

        data class GoToMediaPlayer(val youtubeUrl: String) : Event

    }

}
