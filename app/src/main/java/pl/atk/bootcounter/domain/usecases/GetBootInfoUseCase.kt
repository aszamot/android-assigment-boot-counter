package pl.atk.bootcounter.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import pl.atk.bootcounter.di.IoDispatcher
import pl.atk.bootcounter.domain.repositories.BootInfoRepository
import javax.inject.Inject

class GetBootInfoUseCase @Inject constructor(
    private val bootInfoRepository: BootInfoRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke() = bootInfoRepository.getBootInfoFlow().flowOn(ioDispatcher)
}