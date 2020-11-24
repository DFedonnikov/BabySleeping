package com.babysleep.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babysleep.R
import com.babysleep.navigation.StartScreen
import com.babysleep.navigation.CustomFragmentNavigator
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router
    private val navigator = CustomFragmentNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.replaceScreen(StartScreen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}