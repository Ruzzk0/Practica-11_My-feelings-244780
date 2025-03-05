package cervantes.fedra.myfeelings

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cervantes.fedra.myfeelings.utilities.CustomCircleDrawable
import cervantes.fedra.myfeelings.utilities.Emocion
import cervantes.fedra.myfeelings.utilities.JSONFile
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var jsonFile: JSONFile
    private lateinit var emociones: ArrayList<Emocion>
    private lateinit var icon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonFile = JSONFile(this)
        icon = findViewById(R.id.icon)

        emociones = arrayListOf(
            Emocion("Muy Feliz", 0, Color.YELLOW),
            Emocion("Feliz", 0, Color.GREEN),
            Emocion("Neutral", 0, Color.GRAY),
            Emocion("Triste", 0, Color.BLUE),
            Emocion("Muy Triste", 0, Color.RED)
        )

        cargarDatos()
        configurarBotones()
    }

    private fun cargarDatos() {
        val data = jsonFile.readData() ?: JSONArray()
        for (i in 0 until data.length()) {
            emociones[i].cantidad = data.optInt(i, 0)
        }
        actualizarGraficas()
    }

    private fun actualizarGraficas() {
        val graficaCircular = CustomCircleDrawable(emociones)
        findViewById<View>(R.id.graph).background = graficaCircular

        actualizarBarras()
        actualizarIcono()
    }

    private fun actualizarBarras() {
        val total = emociones.sumOf { it.cantidad }.toFloat()
        if (total == 0f) return

        actualizarBarra(R.id.graphVeryHappy, emociones[0].cantidad, total)
        actualizarBarra(R.id.graphHappy, emociones[1].cantidad, total)
        actualizarBarra(R.id.graphNeutral, emociones[2].cantidad, total)
        actualizarBarra(R.id.graphSad, emociones[3].cantidad, total)
        actualizarBarra(R.id.graphVerySad, emociones[4].cantidad, total)
    }

    private fun actualizarBarra(barraId: Int, cantidad: Int, total: Float) {
        val barra = findViewById<View>(barraId)
        val porcentaje = (cantidad / total) * 100
        val ancho = (porcentaje / 100) * resources.displayMetrics.widthPixels * 0.6f
        barra.layoutParams.width = ancho.toInt()
        barra.requestLayout()
    }

    private fun actualizarIcono() {
        val emocionMaxima = emociones.maxByOrNull { it.cantidad }
        when (emocionMaxima?.nombre) {
            "Muy Feliz" -> icon.setImageResource(R.drawable.ic_veryhappy)
            "Feliz" -> icon.setImageResource(R.drawable.ic_happy)
            "Neutral" -> icon.setImageResource(R.drawable.ic_neutral)
            "Triste" -> icon.setImageResource(R.drawable.ic_sad)
            "Muy Triste" -> icon.setImageResource(R.drawable.ic_sad)
        }
    }

    private fun configurarBotones() {
        findViewById<ImageButton>(R.id.veryHappyButton).setOnClickListener { registrarEmocion(0) }
        findViewById<ImageButton>(R.id.happyButton).setOnClickListener { registrarEmocion(1) }
        findViewById<ImageButton>(R.id.neutralButton).setOnClickListener { registrarEmocion(2) }
        findViewById<ImageButton>(R.id.sadButton).setOnClickListener { registrarEmocion(3) }
        findViewById<ImageButton>(R.id.verySadButton).setOnClickListener { registrarEmocion(4) }

        findViewById<Button>(R.id.guardarButton).setOnClickListener { guardarDatos() }
    }

    private fun registrarEmocion(index: Int) {
        emociones[index].cantidad++
        actualizarGraficas()
    }

    private fun guardarDatos() {
        val jsonArray = JSONArray()
        for (emocion in emociones) {
            jsonArray.put(emocion.cantidad)
        }
        jsonFile.saveData(jsonArray)
    }
}

