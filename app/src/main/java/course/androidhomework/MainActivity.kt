package course.androidhomework

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)

        if (savedInstanceState == null) {
            waitBeforeParsingAndShowProgress()
            parseWithThread()
            parseWithExecutor()
        }
    }

    private fun waitBeforeParsingAndShowProgress() {
        Thread.sleep(5000)
        var progressStatus = 0
        progressBar.max = 5000
        val handler = Handler(Looper.getMainLooper())
        Thread {
            while (progressStatus < progressBar.max) {
                progressStatus += 50
                Thread.sleep(10)
                handler.post {
                    progressBar.progress = progressStatus
                }
            }
        }.start()
    }

    private fun parseWithThread() {
        val thread = Thread(null, { printJson() }, "ThreadBackground")
        thread.start()
    }

    private fun parseWithExecutor() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            printJson()
        }
    }

    private fun printJson() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        try {
            val file = applicationContext.assets.open("jsonFile.json")
            val isr = InputStreamReader(file)
            val jsonText = BufferedReader(isr).readText()
            val listType: Type = object : TypeToken<ArrayList<JsonModel>>() {}.type
            val json: List<JsonModel> = gson.fromJson(jsonText, listType)
            print(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}