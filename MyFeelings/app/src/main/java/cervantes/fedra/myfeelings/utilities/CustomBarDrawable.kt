package cervantes.fedra.myfeelings.utilities

import android.graphics.*
import android.graphics.drawable.Drawable

class CustomBarDrawable(private val emociones: List<Emocion>) : Drawable() {
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private val rect = RectF()

    override fun draw(canvas: Canvas) {
        val total = emociones.sumOf { it.cantidad }.toFloat()
        if (total == 0f) return

        emociones.forEachIndexed { index, emocion ->
            val porcentaje = emocion.cantidad / total
            val ancho = bounds.width() * porcentaje
            paint.color = emocion.color
            rect.set(0f, index * 100f, ancho, (index + 1) * 100f)
            canvas.drawRect(rect, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE
}
