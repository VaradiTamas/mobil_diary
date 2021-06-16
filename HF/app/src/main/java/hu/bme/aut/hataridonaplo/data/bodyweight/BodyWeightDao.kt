package hu.bme.aut.hataridonaplo.data.bodyweight

import androidx.room.*
import hu.bme.aut.hataridonaplo.data.bodyweight.BodyWeight
import java.util.*

@Dao
interface BodyWeightDao {
    @Query("SELECT * FROM bodyweight WHERE date BETWEEN :from AND :to")
    fun getBodyWeightsForDate(from: Date, to: Date): List<BodyWeight>

    @Query("SELECT count(id) FROM bodyweight WHERE date BETWEEN :from AND :to")
    fun numOfWeights(from: Date, to: Date): Integer

    @Insert
    fun insert(weight: BodyWeight): Long

    @Update
    fun update(weight: BodyWeight)

    @Delete
    fun deleteItem(weight: BodyWeight)
}