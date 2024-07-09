package pl.atk.bootcounter.domain.repositories

import kotlinx.coroutines.flow.Flow
import pl.atk.bootcounter.domain.entities.BootInfo

interface BootInfoRepository {

    fun getBootInfoFlow(): Flow<List<BootInfo>>

    suspend fun saveBootInfo(bootInfo: BootInfo)

    suspend fun getLatestBootInfoNotificationInfo(): List<BootInfo>
}