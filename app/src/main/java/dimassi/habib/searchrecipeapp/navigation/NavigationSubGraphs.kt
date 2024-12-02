package dimassi.habib.searchrecipeapp.navigation

import dimassi.habib.media_player.navigation.MediaPlayerFeatureAPi
import dimassi.habib.search.navigation.SearchFeatureApi

data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi:MediaPlayerFeatureAPi
)
