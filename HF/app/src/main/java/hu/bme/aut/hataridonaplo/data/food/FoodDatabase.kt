package hu.bme.aut.hataridonaplo.data.food

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.hataridonaplo.data.food.Food
import hu.bme.aut.hataridonaplo.data.food.FoodDao

@Database(entities = [Food::class], version = 1)
@TypeConverters(Food.Converters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}