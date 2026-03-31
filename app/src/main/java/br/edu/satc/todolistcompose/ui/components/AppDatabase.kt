package br.edu.satc.todolistcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarefas_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}