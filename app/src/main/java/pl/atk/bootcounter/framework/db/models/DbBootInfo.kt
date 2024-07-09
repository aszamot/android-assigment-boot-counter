package pl.atk.bootcounter.framework.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "boot_info")
data class DbBootInfo(
    @PrimaryKey val time: Long
)