package dimassi.habib.media_player.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dimassi.habib.media_player.navigation.MediaPlayerFeatureAPi
import dimassi.habib.media_player.navigation.MediaPlayerImpl

@InstallIn(SingletonComponent::class)
@Module
object MediaPlayerModule {


    @Provides
    fun provideMediaPlayerFeatureApi(): MediaPlayerFeatureAPi {
        return MediaPlayerImpl()
    }

}