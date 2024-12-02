package dimassi.habib.search.domain.use_cases

import dimassi.habib.search.domain.model.Recipe
import dimassi.habib.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(recipe: Recipe) = flow<Unit> {
        searchRepository.insertRecipe(recipe)
    }.flowOn(Dispatchers.IO)

}