package pl.atk.bootcounter.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.atk.bootcounter.Constants
import pl.atk.bootcounter.data.local.LocalBootInfoDataSource
import pl.atk.bootcounter.framework.data.LocalBootInfoDataSourceImpl
import pl.atk.bootcounter.framework.db.BootInfoDatabase
import pl.atk.bootcounter.framework.db.daos.BootInfoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    @Singleton
    @Provides
    fun provideBootInfoDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        BootInfoDatabase::class.java,
        Constants.DB_NAME
    ).build()

    @Provides
    fun provideBootInfoDao(bootInfoDatabase: BootInfoDatabase): BootInfoDao =
        bootInfoDatabase.bootInfoDao()
}