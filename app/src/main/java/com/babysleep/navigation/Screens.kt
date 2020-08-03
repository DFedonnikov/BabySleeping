package com.babysleep.navigation

import com.babysleep.ui.SoundsFragment
import com.babysleep.ui.start.StartFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object StartScreen : SupportAppScreen() {

    override fun getFragment() = StartFragment()
}

object SoundsScreen : SupportAppScreen() {

    override fun getFragment() = SoundsFragment()
}