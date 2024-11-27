package com.example.movieinfo.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.Navigator
import com.example.movieinfo.entity.MovieGallery
import com.example.movieinfo.entity.MovieGalleryImpl
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory


fun NavController.navigate(route: String, param: Pair<String, Parcelable>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    param?.let { this.currentBackStackEntry?.arguments?.putParcelable(param.first, param.second)  }

    navigate(route, builder)
}

fun NavController.navigate(route: String, params: List<Pair<String, Parcelable>>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    params?.let {
        val arguments = this.currentBackStackEntry?.arguments
        params.forEach { arguments?.putParcelable(it.first, it.second) }
    }

    navigate(route, builder)
}

fun NavController.navigate(route: String, params: Bundle?, builder: NavOptionsBuilder.() -> Unit = {}) {
    this.currentBackStackEntry?.arguments?.putAll(params)

    navigate(route, builder)
}


@SuppressLint("RestrictedApi")
fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}
class AssetParamType: NavType<MovieGalleryImpl>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MovieGalleryImpl? {
        return bundle.getParcelable(key,MovieGalleryImpl::class.java)
    }

    override fun parseValue(value: String): MovieGalleryImpl {

return Moshi.Builder().build().adapter(MovieGalleryImpl::class.java).fromJson(value)?: error("unable to convert")

    }

    override fun put(bundle: Bundle, key: String, value: MovieGalleryImpl) {
        bundle.putParcelable(key,value)
    }
}