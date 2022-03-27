package course.androidhomework

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkGoogleServices() == ConnectionResult.SUCCESS) {
            getFirebaseToken()
        }
    }

    private fun checkGoogleServices() : Int {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MyTag", it.result)
            }
        }
    }
}