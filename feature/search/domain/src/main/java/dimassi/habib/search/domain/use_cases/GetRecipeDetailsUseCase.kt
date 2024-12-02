package dimassi.habib.search.domain.use_cases

import dimassi.habib.common.utils.NetworkResult
import dimassi.habib.search.domain.model.RecipeDetails
import dimassi.habib.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(private val searchRepository: SearchRepository) {


    operator fun invoke(id: String) = flow<NetworkResult<RecipeDetails>> {
        emit(NetworkResult.Loading())
        val response = searchRepository.getRecipeDetails(id)
        if (response.isSuccess) {
            emit(NetworkResult.Success(data = response.getOrThrow()))
        } else {
            emit(NetworkResult.Error(message = response.exceptionOrNull()?.localizedMessage))
        }
    }.catch {
        emit(NetworkResult.Error(it.message))
    }.flowOn(Dispatchers.IO)

}