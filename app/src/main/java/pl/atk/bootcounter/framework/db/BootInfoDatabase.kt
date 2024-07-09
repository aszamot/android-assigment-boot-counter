package pl.atk.bootcounter.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.atk.bootcounter.framework.db.daos.BootInfoDao
import pl.atk.bootcounter.framework.db.models.DbBootInfo

@Database(
    entities = [DbBootInfo::class],
    version = 1
)
abstract class BootInfoDatabase : RoomDatabase() {

    abstract fun bootInfoDao(): BootInfoDao
}