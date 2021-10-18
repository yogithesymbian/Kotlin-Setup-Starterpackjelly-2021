package id.scodeid.kotlin_setup_starterpackjelly2021.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.scodeid.kotlin_setup_starterpackjelly2021.MainActivity
import id.scodeid.kotlin_setup_starterpackjelly2021.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MyFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, """
            ----
            From:             ${remoteMessage.from}
            Data:                         ${remoteMessage.data}
            messageId:                         ${remoteMessage.messageId}
            messageType:                                     ${remoteMessage.messageType}
            sentTime:                                                 ${remoteMessage.sentTime}
            notification:                                                             ${remoteMessage.notification}
        """.trimIndent())


        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
        try {
            sendNotification(remoteMessage.notification)
        } catch (e: Exception){
            Log.d(TAG, "sendNotification $e")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(notification: RemoteMessage.Notification?) {

        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.img_micro)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("message", "${notification?.body}")
        // add this:
        intent.action = "showmessage"

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications_active)
            .setContentTitle(notification?.title)
            .setContentText(notification?.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Large Notification Title"))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    fun subscribeTopic(context: Context, topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
            Toast.makeText(context, "Subscribed $topic", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Subscribe $topic", Toast.LENGTH_LONG).show()
        }
    }

    fun unsubscribeTopic(context: Context, topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Toast.makeText(context, "Unsubscribed $topic", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Unsubscribe $topic", Toast.LENGTH_LONG).show()
        }
    }

    fun sendMessage(title: String, content: String,topic: String) {
        GlobalScope.launch {
            val endpoint = "https://fcm.googleapis.com/fcm/send"
            try {
                val url = URL(endpoint)
                val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                httpsURLConnection.readTimeout = 10000
                httpsURLConnection.connectTimeout = 15000
                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                // Adding the necessary headers
                httpsURLConnection.setRequestProperty("authorization", "key=AAAAVPIZbXs:APA91bELTtDGzcRCId8luhrbotLHBPLs-0CTWbeIYlgTC6pSyQrRPumZ37gI4qc9D-GassRdl5tzyEvKcG9c_fcy4FPfntpyFZ-2t1766lhDnias_-N2osg3_CosoHEO_YC5YknNQD2t")
                httpsURLConnection.setRequestProperty("Content-Type", "application/json")

                // Creating the JSON with post params
                val body = JSONObject()

                val data = JSONObject()
                data.put("title", title)
                data.put("body", content)
                body.put("notification",data)

                body.put("to","/topics/$topic")

                val outputStream: OutputStream = BufferedOutputStream(httpsURLConnection.outputStream)
                val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
                writer.write(body.toString())
                writer.flush()
                writer.close()
                outputStream.close()
                val responseCode: Int = httpsURLConnection.responseCode
                val responseMessage: String = httpsURLConnection.responseMessage
                Log.d("Response:", "$responseCode $responseMessage")
                var result = String()
                var inputStream: InputStream? = null
                inputStream = if (responseCode in 400..499) {
                    httpsURLConnection.errorStream
                } else {
                    httpsURLConnection.inputStream
                }

                if (responseCode == 200) {
                    Log.e("Success:", "notification sent $title \n $content")
                    // The details of the user can be obtained from the result variable in JSON format
                } else {
                    Log.e("Error", "Error Response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}