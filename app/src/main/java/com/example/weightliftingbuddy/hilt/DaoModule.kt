package com.example.weightliftingbuddy.hilt

import com.example.weightliftingbuddy.data.room.dao.ExerciseDao
import com.example.weightliftingbuddy.data.room.dao.ExerciseSessionDao
import com.example.weightliftingbuddy.data.room.dao.ExerciseSetDao
import com.example.weightliftingbuddy.data.room.dao.WorkoutDao
import com.example.weightliftingbuddy.data.room.database.GeneralDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideExerciseDao(generalDatabase: GeneralDatabase): ExerciseDao {
        return generalDatabase.exerciseDao
    }

    @Provides
    fun provideExerciseSessionDao(generalDatabase: GeneralDatabase): ExerciseSessionDao {
        return generalDatabase.exerciseSessionDao
    }

    @Provides
    fun providesExerciseSetDao(generalDatabase: GeneralDatabase): ExerciseSetDao {
        return generalDatabase.exerciseSetDao
    }

    @Provides
    fun providesWorkoutDao(generalDatabase: GeneralDatabase): WorkoutDao {
        return generalDatabase.workoutDao
    }

}