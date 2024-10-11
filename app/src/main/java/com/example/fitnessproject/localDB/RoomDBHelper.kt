package com.example.fitnessproject.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnessproject.model.Activity

@Database(entities = [Activity::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class RoomDBHelper: RoomDatabase() {
    abstract val activityDao: ActivityDao
    companion object {
        @Volatile
        private var INSTANCE: RoomDBHelper? = null

        fun getInstance(context: Context): RoomDBHelper {
            return INSTANCE ?: synchronized(this) {

                val instance = Room
                    .databaseBuilder(context, RoomDBHelper::class.java, "DB")
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}