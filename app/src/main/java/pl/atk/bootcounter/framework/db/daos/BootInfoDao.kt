package pl.atk.bootcounter.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.atk.bootcounter.framework.db.models.DbBootInfo

@Dao
interface BootInfoDao {

    @Query("SELECT * FROM boot_info")
    fun getAllFlow(): Flow<List<DbBootInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbBootInfo: DbBootInfo)

    @Query("SELECT * FROM boot_info ORDER BY time DESC LIMIT 2")
    suspend fun getLatestTwo(): List<DbBootInfo>
}