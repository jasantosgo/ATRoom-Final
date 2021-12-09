package com.example.android.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Question::class],
    version = 4,
    exportSchema = false)
abstract class QuestionDB : RoomDatabase() {
    //referencia al DAO
    abstract val questionDAO: QuestionDAO

    companion object {
        //referencia a BD
        @Volatile
        private var INSTANCE: QuestionDB? = null

        //tomar la instancia
        fun getInstance(context: Context): QuestionDB {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDB::class.java,
                        "questions_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
