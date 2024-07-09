package pl.atk.bootcounter.presentation.screens.bootinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.domain.usecases.GetBootInfoUseCase
import pl.atk.bootcounter.framework.schedulers.NotificationsScheduler
import pl.atk.bootcounter.presentation.screens.bootinfo.model.UiBootInfo
import pl.atk.bootcounter.utils.extensions.toDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BootInfoViewModel @Inject constructor(
    private val getBootInfoUseCase: GetBootInfoUseCase,
    private val notificationsScheduler: NotificationsScheduler
) : ViewModel() {

    private val _bootInfoFlow: Flow<List<BootInfo>> = getBootInfoUseCase()
    val bootInfoUiState: StateFlow<BootInfoUiState> = _bootInfoFlow
        .map { bootInfoList ->
            val uiBootInfoList = mapBootInfoListToUiBootInfoList(bootInfoList)
            BootInfoUiState(bootInfo = uiBootInfoList, isLoading = false)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            BootInfoUiState()
        )

    private fun mapBootInfoListToUiBootInfoList(bootInfoList: List<BootInfo>): List<UiBootInfo> {
        val groupedByDate = mutableMapOf<Date, Int>()
        for (bootInfo in bootInfoList) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = bootInfo.time
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val date = calendar.time
            val count = groupedByDate[date] ?: 0
            groupedByDate[date] = count + 1
        }
        return groupedByDate.map { (date, counter) ->
            UiBootInfo(date, counter)
        }
    }

    fun scheduleNotifications() {
        notificationsScheduler.scheduleNotifications()
    }
}