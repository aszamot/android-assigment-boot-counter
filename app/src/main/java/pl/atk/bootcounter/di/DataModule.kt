package pl.atk.bootcounter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.atk.bootcounter.data.local.LocalBootInfoDataSource
import pl.atk.bootcounter.data.repositoriesimpl.BootInfoRepositoryImpl
import pl.atk.bootcounter.domain.repositories.BootInfoRepository
import pl.atk.bootcounter.framework.data.LocalBootInfoDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindBootInfoRepository(
        bootInfoRepositoryImpl: BootInfoRepositoryImpl
    ): BootInfoRepository

    @Binds
    abstract fun bindsLocalBootInfoDataSource(
        localBootInfoDataSourceImpl: LocalBootInfoDataSourceImpl
    ): LocalBootInfoDataSource
}