package hu.bme.aut.hataridonaplo.data.exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "exercises")
data class Exercise(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "isDone") val isDone: Integer,
    @ColumnInfo(name = "date") val date: Date
) {
    class Converters {
        companion object {
            @JvmStatic
            @TypeConverter
            fun fromTimestamp(value: Long?): Date? {
                return value?.let { Date(it) }
            }
            @JvmStatic
            @TypeConverter
            fun dateToTimestamp(date: Date?): Long? {
                return date?.time?.toLong()
            }
        }
    }
}