package com.babysleep.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import com.babysleep.ui.SoundsFragment
import com.babysleep.ui.start.StartFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object StartScreen : SupportAppScreen() {

    override fun getFragment() = StartFragment()
}

object SoundsScreen : SupportAppScreen() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun getFragment() = SoundsFragment()
}