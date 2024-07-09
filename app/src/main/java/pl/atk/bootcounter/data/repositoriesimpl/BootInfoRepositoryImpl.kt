package pl.atk.bootcounter.data.repositoriesimpl

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pl.atk.bootcounter.data.local.LocalBootInfoDataSource
import pl.atk.bootcounter.data.mappers.toBootInfo
import pl.atk.bootcounter.data.mappers.toDbBootInfo
import pl.atk.bootcounter.di.IoDispatcher
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.domain.repositories.BootInfoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootInfoRepositoryImpl @Inject constructor(
    private val localBootInfoDataSource: LocalBootInfoDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BootInfoRepository {

    override fun getBootInfoFlow(): Flow<List<BootInfo>> {
        return localBootInfoDataSource.getBootInfoFlow()
            .map { dbBootInfo ->
                dbBootInfo.map { it.toBootInfo() }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun saveBootInfo(bootInfo: BootInfo) = withContext(ioDispatcher) {
        val dbBootInfo = bootInfo.toDbBootInfo()
        localBootInfoDataSource.saveBootInfo(dbBootInfo)
    }

    override suspend fun getLatestBootInfoNotificationInfo(): List<BootInfo> {
        return localBootInfoDataSource.getLatestBootInfo().map { it.toBootInfo() }
    }
}