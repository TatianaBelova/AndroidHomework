package course.androidhomework

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import course.androidhomework.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var image: ImageView
    private lateinit var button: Button
    private var selectedNumber = 1

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedNumber = result.data?.extras?.getInt("number") ?: 1
            setImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        image = binding.imageView
        button = binding.button.also {
            val intent = Intent(this, MainActivity2::class.java)
            it.setOnClickListener { resultLauncher.launch(intent) }
        }
        if (savedInstanceState != null) {
            selectedNumber = savedInstanceState.getInt("savedNumber")
            setImage()
        }
        workWithBooks()
        workWithUsers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("savedNumber", selectedNumber)
        super.onSaveInstanceState(outState)
    }

    private fun setImage() {
        when (selectedNumber) {
            1 -> image.setImageResource(R.drawable.one)
            2 -> image.setImageResource(R.drawable.two)
            3 -> image.setImageResource(R.drawable.three)
        }
    }
}