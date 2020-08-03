package com.babysleep.navigation

import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.babysleep.ui.MainActivity
import com.babysleep.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class CustomFragmentNavigator(activity: MainActivity) :
    SupportAppNavigator(activity, R.id.container) {

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        nextFragment?.enterTransition = Slide(Gravity.BOTTOM)
    }
}