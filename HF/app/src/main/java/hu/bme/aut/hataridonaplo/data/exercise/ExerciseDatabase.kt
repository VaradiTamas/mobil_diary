package hu.bme.aut.hataridonaplo.data.exercise

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.hataridonaplo.data.exercise.Exercise
import hu.bme.aut.hataridonaplo.data.exercise.ExerciseDao

@Database(entities = [Exercise::class], version = 1)
@TypeConverters(Exercise.Converters::class)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}