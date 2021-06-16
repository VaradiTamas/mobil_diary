package hu.bme.aut.hataridonaplo.data.bodyweight

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeight
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeightDao

@Database(entities = [BodyWeight::class], version = 1)
@TypeConverters(BodyWeight.Converters::class)
abstract class BodyWeightDatabase : RoomDatabase() {
    abstract fun bodyWeightDao(): BodyWeightDao
}