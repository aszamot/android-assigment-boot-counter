package pl.atk.bootcounter.framework.schedulers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.atk.bootcounter.Constants
import pl.atk.bootcounter.framework.receivers.NotificationReceiver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "NotificationsScheduler"
        private const val NOTIFICATION_REQUEST_CODE = 5002
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleNotifications(
        interval: Long = Constants.DEFAULT_NOTIFICATIONS_INTERVAL_IN_MS
    ) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, NOTIFICATION_REQUEST_CODE, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + interval

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            interval,
            pendingIntent
        )
        Log.i(TAG, "NOTIFICATIONS SCHEDULED")
    }
}