package cervantes.fedra.myfeelings.utilities

import android.content.Context
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class JSONFile(private val context: Context) {

    private val fileName = "emociones.json"

    fun saveData(data: JSONArray) {
        val file = File(context.filesDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        val outputStreamWriter = OutputStreamWriter(fileOutputStream)
        outputStreamWriter.write(data.toString())
        outputStreamWriter.close()
    }

    fun readData(): JSONArray? {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return null

        val content = file.readText()
        return JSONArray(content)
    }
}
