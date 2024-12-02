package dimassi.habib.search.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dimassi.habib.common.navigation.FeatureApi
import dimassi.habib.common.navigation.NavigationRoute
import dimassi.habib.common.navigation.NavigationSubGraphRoute
import dimassi.habib.search.screens.details.RecipeDetails
import dimassi.habib.search.screens.details.RecipeDetailsScreen
import dimassi.habib.search.screens.details.RecipeDetailsViewModel
import dimassi.habib.search.screens.favorite.FavoriteScreen
import dimassi.habib.search.screens.favorite.FavoriteViewModel
import dimassi.habib.search.screens.recipe_list.RecipeList
import dimassi.habib.search.screens.recipe_list.RecipeListScreen
import dimassi.habib.search.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi


class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {

            composable(route = NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                ) { mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }

            }

            composable(route = NavigationRoute.RecipeDetails.route) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let {
                        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails(it))
                    }
                }
                RecipeDetailsScreen(
                    viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
                    },
                    onFavoriteClick = {
                        viewModel.onEvent(RecipeDetails.Event.InsertRecipe(it))
                    },
                    onDelete = {
                        viewModel.onEvent(RecipeDetails.Event.DeleteRecipe(it))
                    }, navHostController = navHostController
                )
            }

            composable(NavigationRoute.FavoriteScreen.route) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreen(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    onClick = { mealId ->
                        viewModel.onEvent(FavoriteScreen.Event.GoToDetails(mealId))
                    })
            }

        }


    }
}

