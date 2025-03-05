package cervantes.fedra.myfeelings.utilities

import android.graphics.*
import android.graphics.drawable.Drawable

class CustomCircleDrawable(private val emociones: List<Emocion>) : Drawable() {
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 60f
        strokeCap = Paint.Cap.ROUND
    }
    private val rect = RectF()

    override fun draw(canvas: Canvas) {
        val total = emociones.sumOf { it.cantidad }
        val size = minOf(bounds.width(), bounds.height())
        val padding = 60f
        rect.set(
            bounds.centerX() - size / 2 + padding,
            bounds.centerY() - size / 2 + padding,
            bounds.centerX() + size / 2 - padding,
            bounds.centerY() + size / 2 - padding
        )

        if (total == 0) {
            paint.color = Color.LTGRAY
            canvas.drawArc(rect, 0f, 360f, false, paint)
            return
        }

        var startAngle = -90f
        for (emocion in emociones) {
            val sweepAngle = (emocion.cantidad.toFloat() / total) * 360
            paint.color = emocion.color
            canvas.drawArc(rect, startAngle, sweepAngle, false, paint)
            startAngle += sweepAngle
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}

