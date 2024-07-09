package pl.atk.bootcounter.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.atk.bootcounter.di.IoDispatcher
import pl.atk.bootcounter.domain.entities.BootInfo
import pl.atk.bootcounter.domain.repositories.BootInfoRepository
import javax.inject.Inject

class SaveBootInfoUseCase @Inject constructor(
    private val bootInfoRepository: BootInfoRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(time: Long) = withContext(ioDispatcher) {
        val bootInfo = BootInfo(time = time)
        bootInfoRepository.saveBootInfo(bootInfo)
    }
}