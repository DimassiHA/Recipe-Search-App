package dimassi.habib.media_player.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dimassi.habib.common.navigation.FeatureApi
import dimassi.habib.common.navigation.NavigationRoute
import dimassi.habib.common.navigation.NavigationSubGraphRoute
import dimassi.habib.media_player.screens.MediaPlayerScreen

interface MediaPlayerFeatureAPi : FeatureApi

class MediaPlayerImpl : MediaPlayerFeatureAPi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.MediaPlayer.route,
            startDestination = NavigationRoute.MediaPlayer.route
        ) {

            composable(route = NavigationRoute.MediaPlayer.route) {
                val mediaPlayerVideoId = it.arguments?.getString("video_id")
                mediaPlayerVideoId?.let {
                    MediaPlayerScreen(videoId = it)
                }
            }

        }
    }
}
