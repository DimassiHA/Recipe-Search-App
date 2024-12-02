package dimassi.habib.searchrecipeapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dimassi.habib.media_player.navigation.MediaPlayerFeatureAPi
import dimassi.habib.search.data.local.RecipeDao
import dimassi.habib.search.navigation.SearchFeatureApi
import dimassi.habib.searchrecipeapp.local.AppDatabase
import dimassi.habib.searchrecipeapp.navigation.NavigationSubGraphs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(
        searchFeatureApi: SearchFeatureApi,
        mediaPlayerFeatureAPi: MediaPlayerFeatureAPi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi, mediaPlayerFeatureAPi)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.getRecipeDao()
    }

}