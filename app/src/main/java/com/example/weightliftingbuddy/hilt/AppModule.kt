package com.example.weightliftingbuddy.hilt

import android.content.Context
import androidx.room.Room
import com.example.weightliftingbuddy.repositories.ExerciseRepository
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import com.example.weightliftingbuddy.room.database.GeneralDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExerciseDatabase(@ApplicationContext context: Context): GeneralDatabase {
        return Room.databaseBuilder(context, GeneralDatabase::class.java, GeneralDatabase.NAME)
            .build()
    }

    @Provides
    fun provideExerciseDao(generalDatabase: GeneralDatabase): ExerciseDao {
        return generalDatabase.exerciseDao
    }

    @Provides
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDao)
    }
}
