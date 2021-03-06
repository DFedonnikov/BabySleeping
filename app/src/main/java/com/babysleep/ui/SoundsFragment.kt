package com.babysleep.ui

import android.animation.Animator
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.babysleep.R
import com.babysleep.databinding.LayoutPlayStopButtonBinding
import com.babysleep.databinding.LayoutSeekBarBinding
import com.google.android.exoplayer2.C.WAKE_MODE_LOCAL
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class SoundsFragment : Fragment() {

    private var player: SimpleExoPlayer? = null
    private var playbackPosition = 0L
    private var currentWindow = 0

    @Inject
    lateinit var cacheDataSourceFactory: CacheDataSourceFactory

    private val soundControlViewModel: SoundControlViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent { buildContent() }
    }

    @Preview("Content")
    @Composable
    fun buildContent() {
        MaterialTheme {
            ProvideWindowInsets {
                ConstraintLayout(
                    modifier = Modifier.background(
                        color = Color(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.port_gore
                            )
                        )
                    )
                ) {
                    val contentTopEdge = createGuidelineFromTop(0.21f)
                    val contentBottomEdge = createGuidelineFromTop(0.71f)
                    val topWaveEdge = createGuidelineFromTop(0.3f)
                    val bottomWaveEdge = createGuidelineFromTop(0.62f)
                    val (pagerRef,
                        topWaveRef,
                        bottomWaveRef,
                        playStopButtonRef,
                        icSoundQuietRef,
                        icSoundLoudRef,
                        seekBarRef) = createRefs()
                    Pager(modifier = Modifier
                        .constrainAs(pagerRef) {
                            top.linkTo(contentTopEdge)
                            bottom.linkTo(contentBottomEdge)
                        }
                        .fillMaxHeight(0.5f))
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.bg_top_wave),
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(topWaveRef) {
                                bottom.linkTo(topWaveEdge)
                                top.linkTo(parent.top)
                            }
                            .fillMaxSize()
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.bg_bottom_wave),
                        modifier = Modifier
                            .constrainAs(bottomWaveRef) {
                                top.linkTo(bottomWaveEdge)
                                bottom.linkTo(parent.bottom)
                            }
                            .fillMaxSize(),
                        contentDescription = null
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_sound_quiet),
                        modifier = Modifier
                            .constrainAs(icSoundQuietRef) {
                                start.linkTo(parent.start)
                                top.linkTo(icSoundLoudRef.top)
                                bottom.linkTo(icSoundLoudRef.bottom)
                            }
                            .height(10.dp)
                            .padding(start = 50.dp)
                            .offset(y = (-50).dp),
                        contentDescription = null
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_sound_loud),
                        modifier = Modifier
                            .constrainAs(icSoundLoudRef) {
                                end.linkTo(parent.end)
                                bottom.linkTo(playStopButtonRef.top)
                            }
                            .padding(end = 50.dp)
                            .offset(y = (-50).dp),
                        contentDescription = null
                    )
                    PagerIndicator(
                        scope = this,
                        pagerRef = pagerRef
                    )
                    SeekBar(modifier = Modifier
                        .constrainAs(seekBarRef) {
                            top.linkTo(icSoundLoudRef.top)
                            bottom.linkTo(icSoundLoudRef.bottom)
                            start.linkTo(icSoundQuietRef.end)
                            end.linkTo(icSoundLoudRef.start)
                            width = Dimension.fillToConstraints
                        }
                        .offset(y = (-50).dp))
                    PlayStopButton(modifier = Modifier.constrainAs(playStopButtonRef) {
                        bottom.linkTo(parent.bottom)
                        centerHorizontallyTo(parent)
                    })
                }
            }
        }
    }

    @Composable
    private fun PagerIndicator(
        scope: ConstraintLayoutScope,
        pagerRef: ConstrainedLayoutReference
    ) {
        val indicatorRender by soundControlViewModel.pageChangedLiveData.observeAsState(
            IndicatorRender()
        )
        with(scope) {
            val (natureTitleRef, noiseTitleRef, indicatorRef, dummyStartRef, dummyEndRef) = createRefs()
            Text(
                modifier = Modifier
                    .constrainAs(natureTitleRef) {
                        linkTo(top = parent.top, bottom = pagerRef.top, bias = 0.7f)
                        linkTo(start = parent.start, end = parent.end, bias = 0.15f)
                    }
                    .statusBarsPadding(),
                text = stringResource(id = R.string.nature_title),
                fontSize = 20.sp,
                color = Color.White.copy(alpha = indicatorRender.natureTitleAlpha),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.montserrat_alternates))
            )
            Spacer(modifier = Modifier.constrainAs(dummyStartRef) {
                top.linkTo(natureTitleRef.bottom)
                linkTo(start = natureTitleRef.start, end = natureTitleRef.end)
            })
            Text(
                modifier = Modifier
                    .constrainAs(noiseTitleRef) {
                        linkTo(top = parent.top, bottom = pagerRef.top, bias = 0.7f)
                        linkTo(start = parent.start, end = parent.end, bias = 0.85f)
                    }
                    .statusBarsPadding(),
                text = stringResource(id = R.string.noise_title),
                fontSize = 20.sp,
                color = Color.White.copy(alpha = indicatorRender.noiseTitleAlpha),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.montserrat_alternates))
            )
            Spacer(modifier = Modifier.constrainAs(dummyEndRef) {
                top.linkTo(natureTitleRef.bottom)
                linkTo(start = noiseTitleRef.start, end = noiseTitleRef.end)
            })
            Image(
                modifier = Modifier
                    .constrainAs(indicatorRef) {
                        top.linkTo(natureTitleRef.bottom)
                        linkTo(
                            start = dummyStartRef.start,
                            end = dummyEndRef.end,
                            bias = indicatorRender.bias
                        )
                    }
                    .requiredSize(6.dp)
                    .offset(y = 2.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_indicator),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun Pager(modifier: Modifier) {
        val viewPager = ViewPager2(requireContext()).apply {
            adapter = SoundsCollectionAdapter(this@SoundsFragment)
        }
        AndroidView(
            factory = { viewPager },
            modifier = modifier,
            update = {
                it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        soundControlViewModel.onSoundPageScrolled(position, positionOffset)
                    }
                })
            })
    }

    @Composable
    private fun PlayStopButton(modifier: Modifier) {
        AndroidViewBinding(
            { inflater, parent, attachToParent ->
                LayoutPlayStopButtonBinding.inflate(
                    inflater,
                    parent,
                    attachToParent
                )
            }, modifier = modifier
                .requiredWidth(70.dp)
                .requiredHeight(70.dp)
                .offset(y = (-20).dp)
                .navigationBarsPadding(),
            update = {
                root.setMinProgress(0.5f)
                soundControlViewModel.soundChangeLiveData.observe(
                    viewLifecycleOwner,
                    onChanged = { uri ->
                        if (root.speed < 0) {
                            root.playAnimation()
                        }
                        initPlayer(uri, isReset = true)
                    })
                root.setOnClickListener {
                    player?.let { root.playAnimation() }
                }
                root.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {
                        root.reverseAnimationSpeed()
                        player?.let {
                            it.playWhenReady = root.speed > 0
                        }
                    }
                })

            })
    }

    @Composable
    private fun SeekBar(modifier: Modifier) {
        AndroidViewBinding(
            { inflater, parent, attachToParent ->
                LayoutSeekBarBinding.inflate(
                    inflater,
                    parent,
                    attachToParent
                )
            },
            modifier = modifier
                .heightIn(max = 3.dp),
            update = {
                root.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        player?.volume = p1 / 100f
                        soundControlViewModel.onSoundLevelChanged(p1)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }
                })
            })
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            soundControlViewModel.soundChangeLiveData.value?.let {
                initPlayer(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null)) {
            soundControlViewModel.soundChangeLiveData.value?.let {
                initPlayer(it)
            }
        }
    }

//    override fun onPause() {
//        super.onPause()
//        if (Util.SDK_INT < 24) {
//            releasePlayer()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (Util.SDK_INT >= 24) {
//            releasePlayer();
//        }
//    }

    private fun initPlayer(soundUri: Uri, isReset: Boolean = false) {
        if (player == null) {
            val soundLevel = soundControlViewModel.soundLevelLiveData.value ?: 100
            player = SimpleExoPlayer.Builder(requireContext()).build()
            player?.playWhenReady = true
            player?.repeatMode = Player.REPEAT_MODE_ALL
            player?.volume = soundLevel / 100f
            player?.seekTo(currentWindow, playbackPosition)
            player!!.setWakeMode(WAKE_MODE_LOCAL)
        }
        prepareSource(soundUri, isReset = isReset)
    }

    private fun prepareSource(soundUri: Uri, isReset: Boolean) {
        val mediaSource =
            ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(soundUri)
        player?.prepare(LoopingMediaSource(mediaSource), isReset, isReset)
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.contentPosition
            currentWindow = it.currentWindowIndex
            it.release()
            player = null
        }
    }
}