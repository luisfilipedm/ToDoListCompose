package br.edu.satc.todolistcompose.data

import androidx.room.*

@Dao
interface TarefaDao {

    @Query("SELECT * FROM tarefas")
    suspend fun getAll(): List<TaskData>

    @Insert
    suspend fun inserir(task: TaskData)

    @Update
    suspend fun atualizar(task: TaskData)

    @Delete
    suspend fun deletar(task: TaskData)
}