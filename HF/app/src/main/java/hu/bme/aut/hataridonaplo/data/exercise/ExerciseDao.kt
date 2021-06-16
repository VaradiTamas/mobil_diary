package hu.bme.aut.hataridonaplo.data.exercise

import androidx.room.*
import hu.bme.aut.hataridonaplo.data.exercise.Exercise
import java.util.*

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE isDone = 1")
    fun getDoneTasks(): List<Exercise>

    @Query("SELECT * FROM exercises WHERE isDone = 0")
    fun getToDoTasks(): List<Exercise>

    @Query("SELECT * FROM exercises WHERE date BETWEEN :from AND :to AND isDone = 1")
    fun getDoneTasksForDate(from: Date, to: Date): List<Exercise>

    @Query("SELECT * FROM exercises WHERE date BETWEEN :from AND :to AND isDone = 0")
    fun getToDoTasksForDate(from: Date, to: Date): List<Exercise>

    @Insert
    fun insert(taskTask: Exercise): Long

    @Update
    fun update(taskTask: Exercise)

    @Delete
    fun deleteItem(taskTask: Exercise)
}