package pl.atk.bootcounter.framework.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.atk.bootcounter.domain.usecases.SaveBootInfoUseCase

@HiltWorker
class BootInfoOnBootWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val saveBootInfoUseCase: SaveBootInfoUseCase
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val INPUT_DATA_BOOT_TIME = "INPUT_DATA_BOOT_TIME"
    }

    override suspend fun doWork(): Result {
        return try {
            val bootTime = inputData.getLong(INPUT_DATA_BOOT_TIME, -1)
            if (bootTime == -1L) {
                throw Exception("Invalid boot time")
            }
            saveBootInfoUseCase(bootTime)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}