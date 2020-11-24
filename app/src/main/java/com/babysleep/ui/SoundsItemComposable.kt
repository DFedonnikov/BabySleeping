package com.babysleep.ui

import android.net.Uri
import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import coil.ImageLoader
import com.babysleep.R
import com.babysleep.databinding.LayoutSoundItemShimmerBinding
import com.google.firebase.storage.StorageReference
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun SoundItem(
    state: RenderState,
    imageLoader: ImageLoader,
    modifier: Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        when (state) {
            is Loading -> LoadingState()
            is RenderData -> RenderDataState(state, imageLoader, modifier)
        }
    }
}

@Composable
private fun LoadingState() {
    AndroidViewBinding(factory = { inflater, parent, attachToParent ->
        LayoutSoundItemShimmerBinding.inflate(inflater, parent, attachToParent)
    },
        modifier = Modifier
            .requiredWidth(100.dp)
            .requiredHeight(150.dp)
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(colorResource(id = R.color.port_gore)),
        update = { root.showShimmer(true) })
}

@Composable
fun RenderDataState(data: RenderData, imageLoader: ImageLoader, modifier: Modifier) {
    val imageModifier = when {
        data.isSelected -> modifier.drawColoredShadow(
            color = data.highlightBackgroundColor,
            offsetY = 20.dp
        )
        else -> modifier
    }.then(Modifier.requiredWidth(100.dp))
    CoilImage(
        data = data.iconUrl ?: "",
        contentDescription = "",
        modifier = imageModifier,
        imageLoader = imageLoader,
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth,
        error = {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(colorResource(id = R.color.port_gore))
            )
            println("IMAGE_ERROR: ${it.throwable}")
        },
        loading = { LoadingState() }
    )
    Text(
        text = data.title,
        fontSize = 16.sp,
        color = if (data.isSelected) Color(data.highlightTextColor) else Color.White,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(Font(R.font.montserrat_alternates)),
        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
    )
}

fun Modifier.drawColoredShadow(
    @ColorInt color: Int,
    alpha: Float = 0.5f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {

    val transparentColor = Color(color).copy(alpha = 0.0f).toArgb()
    val shadowColor = Color(color).copy(alpha = alpha).toArgb()
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}

sealed class RenderState

object Loading : RenderState()

data class RenderData(
    val iconUrl: StorageReference?,
    val soundUrl: Uri,
    val title: String,
    @ColorInt
    val highlightTextColor: Int,
    @ColorInt
    val highlightBackgroundColor: Int,
    val isSelected: Boolean = false
) : RenderState()
