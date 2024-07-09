package pl.atk.bootcounter.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import pl.atk.bootcounter.framework.workers.BootInfoNotificationWorker
import pl.atk.bootcounter.framework.workers.BootInfoOnBootWorker
import pl.atk.bootcounter.presentation.notifications.NotificationBuilder
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NotificationReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "RECEIVED NOTIFICATION")
        startNotificationBootInfoWorker(context)
    }

    private fun startNotificationBootInfoWorker(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<BootInfoNotificationWorker>()
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}