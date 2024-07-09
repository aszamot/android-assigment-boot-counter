package pl.atk.bootcounter.framework.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import pl.atk.bootcounter.data.local.LocalBootInfoDataSource
import pl.atk.bootcounter.di.IoDispatcher
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.framework.db.daos.BootInfoDao
import pl.atk.bootcounter.framework.db.models.DbBootInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBootInfoDataSourceImpl @Inject constructor(
    private val bootInfoDao: BootInfoDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalBootInfoDataSource {

    override suspend fun saveBootInfo(bootInfo: DbBootInfo) = withContext(ioDispatcher) {
        bootInfoDao.insert(bootInfo)
    }

    override fun getBootInfoFlow(): Flow<List<DbBootInfo>> {
        return bootInfoDao.getAllFlow()
            .flowOn(ioDispatcher)
    }

    override suspend fun getLatestBootInfo(): List<DbBootInfo> {
        return bootInfoDao.getLatestTwo()
    }
}