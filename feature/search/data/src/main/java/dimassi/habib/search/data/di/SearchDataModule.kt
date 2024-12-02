package dimassi.habib.search.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dimassi.habib.search.data.local.RecipeDao
import dimassi.habib.search.data.remote.SearchApiService
import dimassi.habib.search.data.repository.SearchRepoImpl
import dimassi.habib.search.domain.repository.SearchRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://www.themealdb.com/"

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    fun provideSearchRepo(searchApiService: SearchApiService,
                          recipeDao: RecipeDao): SearchRepository {
        return SearchRepoImpl(searchApiService,recipeDao)
    }


}