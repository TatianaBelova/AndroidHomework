package course.androidhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        workWithBooks()
//        workWithUsers()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, ContactsFragment(), "Contacts_TAG")
            .commit()
    }
}