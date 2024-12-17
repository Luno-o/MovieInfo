package com.example.movieinfo.utils

import android.content.Context

interface AppContainer {
    val context: Context
}

class AppContainerImpl(
                       override val context: Context
) : AppContainer
