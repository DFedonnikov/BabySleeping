package com.babysleep.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.request.LoadRequest
import coil.size.Scale
import com.babysleep.R
import com.babysleep.databinding.LayoutSoundItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_sound_item.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SoundItemLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding = LayoutSoundItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        View.inflate(context, R.layout.layout_sound_item, this)
    }


    @Inject
    lateinit var imageLoader: ImageLoader

    fun render(data: RenderData) {
        val request = LoadRequest.Builder(context)
            .data(data.background)
            .target(soundImage)
            .scale(Scale.FIT)
            .listener(onSuccess = { _, _ -> soundImage.background = when {
                data.isSelected -> buildHighlight(data.highlightBackgroundColor)
                else -> null
            } })
            .build()
        imageLoader.execute(request)
        title.text = data.title
        val textColor = if (data.isSelected) data.highlightTextColor else Color.WHITE
        title.setTextColor(textColor)
    }

    private fun buildHighlight(@ColorInt highlightColor: Int): Drawable? {
        val cornerRadiusValue: Float = context.resources.getDimension(R.dimen.sound_item_radius)
        val elevationValue = context.resources.getDimension(R.dimen.sound_item_elevation).toInt()
        val outerRadius = floatArrayOf(
            cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
            cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
            cornerRadiusValue
        )
        val shapeDrawablePadding = Rect()
        shapeDrawablePadding.top = 0
        shapeDrawablePadding.bottom = elevationValue * 2
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setPadding(shapeDrawablePadding)
        shapeDrawable.paint.color = 0
        shapeDrawable.paint.setShadowLayer(
            cornerRadiusValue / 3,
            0f,
            elevationValue.toFloat(),
            highlightColor
        )
//        binding.soundImage.setLayerType(View.LAYER_TYPE_NONE, shapeDrawable.paint)
        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)
        val drawable =
            LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(
            0,
            (elevationValue * 1.5).toInt(),
            0,
            (elevationValue * 1.5).toInt(),
            elevationValue
        )
        return drawable
    }

    data class RenderData(
        @DrawableRes
        val background: Int,
        val title: String,
        @ColorInt
        val highlightTextColor: Int,
        @ColorInt
        val highlightBackgroundColor: Int,
        val isSelected: Boolean = false
    )
}