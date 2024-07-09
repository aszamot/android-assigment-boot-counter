package pl.atk.bootcounter.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import pl.atk.bootcounter.framework.workers.BootInfoOnBootWorker

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val bootTime = System.currentTimeMillis()
            startBootInfoWorker(context, bootTime)
        }
    }

    private fun startBootInfoWorker(context: Context, bootTime: Long) {
        val inputData = Data.Builder()
            .putLong(BootInfoOnBootWorker.INPUT_DATA_BOOT_TIME, bootTime)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<BootInfoOnBootWorker>()
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}