package dimassi.habib.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dimassi.habib.search.navigation.SearchFeatureApi
import dimassi.habib.search.navigation.SearchFeatureApiImpl

@InstallIn(SingletonComponent::class)
@Module
object UiModule {


    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi {
        return SearchFeatureApiImpl()
    }

}