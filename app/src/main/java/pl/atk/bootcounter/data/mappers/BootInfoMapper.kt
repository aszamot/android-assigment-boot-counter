package pl.atk.bootcounter.data.mappers

import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.framework.db.models.DbBootInfo

fun DbBootInfo.toBootInfo(): BootInfo {
    return BootInfo(
        time = this.time
    )
}

fun BootInfo.toDbBootInfo(): DbBootInfo {
    return DbBootInfo(
        time = this.time
    )
}