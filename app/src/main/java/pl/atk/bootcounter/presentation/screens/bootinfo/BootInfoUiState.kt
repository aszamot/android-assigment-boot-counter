package pl.atk.bootcounter.presentation.screens.bootinfo

import pl.atk.bootcounter.presentation.screens.bootinfo.model.UiBootInfo

data class BootInfoUiState(
    val bootInfo: List<UiBootInfo> = listOf(),
    val isLoading: Boolean = true
)