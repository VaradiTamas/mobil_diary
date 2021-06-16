package hu.bme.aut.hataridonaplo.data.food

import androidx.room.*
import hu.bme.aut.hataridonaplo.data.food.Food
import java.util.*

@Dao
interface FoodDao {
    @Query("SELECT * FROM food WHERE date BETWEEN :from AND :to")
    fun getFoodForDate(from: Date, to: Date): List<Food>

    @Query("SELECT sum(calories) FROM food WHERE date BETWEEN :from AND :to")
    fun getAllCaloriesForDate(from: Date, to: Date): Integer

    @Query("SELECT sum(protein) FROM food WHERE date BETWEEN :from AND :to")
    fun getAllProteinForDate(from: Date, to: Date): Integer

    @Query("SELECT count(id) FROM food WHERE date BETWEEN :from AND :to")
    fun numOfFood(from: Date, to: Date): Integer

    @Query("SELECT sum(carbohydrate) FROM food WHERE date BETWEEN :from AND :to")
    fun getAllCarbohydrateForDate(from: Date, to: Date): Integer

    @Insert
    fun insert(food: Food): Long

    @Update
    fun update(food: Food)

    @Delete
    fun deleteItem(food: Food)
}