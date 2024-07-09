package pl.atk.bootcounter.framework.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.domain.repositories.BootInfoRepository
import pl.atk.bootcounter.presentation.notifications.NotificationBuilder
import pl.atk.bootcounter.utils.extensions.toDate
import pl.atk.bootcounter.utils.extensions.toReadableDateTimeString

@HiltWorker
class BootInfoNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationBuilder: NotificationBuilder,
    private val bootInfoRepository: BootInfoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val latestBootInfo = bootInfoRepository.getLatestBootInfoNotificationInfo()
            val notificationText = getNotificationContentText(latestBootInfo)
            notificationBuilder.showNotification(notificationText)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun getNotificationContentText(latestBootInfo: List<BootInfo>): String {
        return when {
            latestBootInfo.isEmpty() -> "No boots detected"
            latestBootInfo.size == 1 -> "The boot was detected = ${
                latestBootInfo.first().time.toDate().toReadableDateTimeString()
            }"

            else -> "Last boots time delta = ${latestBootInfo[0].time - latestBootInfo[1].time}"
        }
    }
}