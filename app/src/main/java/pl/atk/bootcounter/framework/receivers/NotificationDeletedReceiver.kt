package pl.atk.bootcounter.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import pl.atk.bootcounter.Constants
import pl.atk.bootcounter.framework.schedulers.NotificationsScheduler
import javax.inject.Inject

@AndroidEntryPoint
class NotificationDeletedReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationDeletedReceiver"
        const val DELETE_NOTIFICATION_ACTION = "pl.atk.bootcounter.DELETE_NOTIFICATION"
    }

    @Inject
    lateinit var notificationsScheduler: NotificationsScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "NOTIFICATION DELETED")
        notificationsScheduler.scheduleNotifications(Constants.DEFAULT_NOTIFICATION_AFTER_DISMISS_INTERVAL_IN_MS)
    }
}