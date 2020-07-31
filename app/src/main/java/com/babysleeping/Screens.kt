package com.babysleeping

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object StartScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return StartFragment()
    }
}