package com.example.weightliftingbuddy

import android.content.Context
import androidx.room.Room
import com.example.weightliftingbuddy.repositories.ExerciseRepository
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import com.example.weightliftingbuddy.room.database.ExerciseDatabase
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
    fun provideExerciseDatabase(@ApplicationContext context: Context): ExerciseDatabase {
    return Room.databaseBuilder(context, ExerciseDatabase::class.java, ExerciseDatabase.NAME).build()
    }

    @Provides
    fun provideExerciseDao(exerciseDatabase: ExerciseDatabase): ExerciseDao {
        return exerciseDatabase.dao
    }

    @Provides
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDao)
    }
}
