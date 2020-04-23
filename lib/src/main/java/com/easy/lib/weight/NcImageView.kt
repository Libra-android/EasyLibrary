package com.easy.lib.weight

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.easy.lib.expand.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * @author too young
 * @date 2019/8/12 16:24
 */
class NcImageView : AppCompatImageView {

    companion object {
        const val CIRCLE = -0X0111
    }

    enum class ScaleType {
        FitCenter,
        CenterCrop,
        CenterInside,
        NO
    }

    private var radius = 0F
    fun setRadius(radius: Int) {
        this.radius = radius.toFloat()
        invalidate()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun loadImage(imageUrl: String?, scaleType: ScaleType = ScaleType.CenterCrop) {
        url = imageUrl
        load(scaleType) {
            it.load(imagePathProcess(imageUrl))
        }
    }

    fun loadImage(imageUrl: String?, loadFinish: () -> Unit) {
        url = imageUrl

        val options = getOptions(
            format = DecodeFormat.DEFAULT,
            skipMemoryCache = false,
            diskCacheStrategy = DiskCacheStrategy.ALL,
            errorId = 0,
            scaleType = ScaleType.NO
        )

        if ((context as Activity).isFinishing || (context as Activity).isDestroyed) {

        } else {
            Glide.with(this)
                .asDrawable()
                .apply(options)
                .load(imagePathProcess(imageUrl))
                .into(object : ImageViewTarget<Drawable>(this) {
                    override fun setResource(resource: Drawable?) {
                        resource?.let {
                            setDrawable(resource)
                            loadFinish.invoke()
                        }
                    }
                })
        }

    }

    fun loadImage(imageUrl: String?, width: Int, height: Int) {
        val viewWidth = dp(width)
        val viewHeight = dp(height)
        val options = getOptions(
            format = DecodeFormat.PREFER_RGB_565,
            skipMemoryCache = false,
            diskCacheStrategy = DiskCacheStrategy.ALL,
            errorId = 0,
            scaleType = ScaleType.FitCenter
        )


        if ((context as Activity).isFinishing || (context as Activity).isDestroyed) {

        } else {
            Glide.with(this)
                .asDrawable()
                .apply(options)
                .load(imagePathProcess(imageUrl))
                .into(object : ImageViewTarget<Drawable>(this) {
                    override fun setResource(resource: Drawable?) {
                        resource?.let {
                            if (resource.intrinsicHeight / resource.intrinsicWidth > viewHeight / viewWidth) {
                                layoutParams.height = viewHeight
                                layoutParams.width =
                                    viewHeight * resource.intrinsicWidth / resource.intrinsicHeight
                            } else {
                                layoutParams.width = viewWidth
                                layoutParams.height =
                                    viewWidth * resource.intrinsicHeight / resource.intrinsicWidth
                            }
                            setDrawable(resource)
                        }
                    }

                })
        }


    }

    private fun getOptions(
        format: DecodeFormat,
        skipMemoryCache: Boolean,
        diskCacheStrategy: DiskCacheStrategy,
        errorId: Int,
        scaleType: ScaleType
    ): RequestOptions {
        val options = RequestOptions()
            .format(format)
            .skipMemoryCache(skipMemoryCache)
            .diskCacheStrategy(diskCacheStrategy)
            .error(errorId)
            .placeholder(errorId)
        return when (scaleType) {
            ScaleType.FitCenter -> options.fitCenter()
            ScaleType.CenterInside -> options.centerInside()
            ScaleType.CenterCrop -> options.centerCrop()
            ScaleType.NO -> options
        }
    }

    fun loadImage(resId: Int, scaleType: ScaleType = ScaleType.CenterCrop) {
        load(scaleType) {
            it.load(resId)
        }
    }

    private fun load(scaleType: ScaleType, loader: (RequestManager) -> RequestBuilder<*>) {
        val options = getOptions(
            format = DecodeFormat.DEFAULT,
            skipMemoryCache = false,
            diskCacheStrategy = DiskCacheStrategy.ALL,
            errorId = 0,
            scaleType = scaleType
        )
        if (context is Activity) {
            if ((context as Activity).isFinishing || (context as Activity).isDestroyed) {

            } else {
                loader.invoke(Glide.with(this))
                    .apply(options)
                    .into(this@NcImageView)
            }
        }
    }

    /**
     * 统一处理图片路径，让图片路径不会为空
     *
     * @param imagePath
     * @return
     */
    fun imagePathProcess(imagePath: String?): String {
        var imagePath = imagePath
        if (TextUtils.isEmpty(imagePath)) {
            imagePath = "http"
        }
        return imagePath ?: "http"
    }

    override fun onDraw(canvas: Canvas?) {

        if (drawable == null || radius == 0F) {
            super.onDraw(canvas)
            return
        }

        val path = Path()
        if (radius == CIRCLE.toFloat()) {
            path.addCircle(
                width.toFloat() / 2,
                height.toFloat() / 2,
                width.toFloat() / 2,
                Path.Direction.CCW
            )
        } else {
            val rect = RectF(0F, 0F, width.toFloat(), height.toFloat())
            path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        }
        canvas?.clipPath(path)
        super.onDraw(canvas)

    }

    private var url: String? = ""


}
