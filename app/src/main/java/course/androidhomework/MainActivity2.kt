package course.androidhomework

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import course.androidhomework.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.button1.setOnClickListener { putIntentAndFinishActivity(1) }
        binding.button2.setOnClickListener { putIntentAndFinishActivity(2) }
        binding.button3.setOnClickListener { putIntentAndFinishActivity(3) }
    }

    private fun putIntentAndFinishActivity(number: Int) {
        val intent = Intent().apply {
            putExtra("number", number)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}