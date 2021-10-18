package id.scodeid.kotlin_setup_starterpackjelly2021

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.ActivityMainBinding
import id.scodeid.kotlin_setup_starterpackjelly2021.service.MyFirebaseMessagingService
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signin.LoginActivity
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.score.ScoreFragment
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.score.ScoreMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val myFirebaseMessagingService = MyFirebaseMessagingService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.messaging.isAutoInitEnabled = true


        binding.txtHello.setOnClickListener {
            Log.d("test", "clicked")
            startActivity(Intent(this.applicationContext, ScoreMainActivity::class.java))
        }

        if (checkGooglePlayServices()) {
            Log.w(TAG, "Device have google play services")
        } else {
            //You won't be able to send notifications to this device
            Log.w(TAG, "Device doesn't have google play services")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        binding.btnSubscribe.setOnClickListener {
            Log.d(TAG, "Subscribing to  ${binding.ipEdtSubscribe.text.toString()}")
            myFirebaseMessagingService.subscribeTopic(applicationContext, binding.ipEdtSubscribe.text.toString())
        }

        binding.btnUnsubscribe.setOnClickListener{
            Log.d(TAG, "UnSubscribing to  ${binding.ipEdtSubscribe.text.toString()}")
            myFirebaseMessagingService.unsubscribeTopic(applicationContext, binding.ipEdtSubscribe.text.toString())
        }

        binding.btnLogToken.setOnClickListener {
            // Get token
            // [START log_reg_token]
            Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
            // [END log_reg_token]
        }

        binding.btnSentNotification.setOnClickListener {
            myFirebaseMessagingService.sendMessage("title", binding.ipEdtMessage.text.toString(), binding.ipEdtSubscribe.text.toString())
        }
    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i(TAG, "Google play services updated")
            true
        }
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}