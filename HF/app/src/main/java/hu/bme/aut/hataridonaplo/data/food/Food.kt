package hu.bme.aut.hataridonaplo.data.food

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "food")
data class Food(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "calories") val calories: Integer,
    @ColumnInfo(name = "protein") val protein: Integer,
    @ColumnInfo(name = "carbohydrate") val carbohydrate: Integer,
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