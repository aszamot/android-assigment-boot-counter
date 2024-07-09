package pl.atk.bootcounter.data.local

import kotlinx.coroutines.flow.Flow
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.framework.db.models.DbBootInfo

interface LocalBootInfoDataSource {
    suspend fun saveBootInfo(bootInfo: DbBootInfo)
    fun getBootInfoFlow(): Flow<List<DbBootInfo>>
    suspend fun getLatestBootInfo(): List<DbBootInfo>
}