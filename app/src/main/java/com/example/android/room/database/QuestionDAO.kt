package com.example.android.room.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface QuestionDAO {
    @Insert
    suspend fun insertQuestion(question: Question)

    @Insert
    suspend fun insertQuestions(questions: List<Question>)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("DELETE FROM question")
    suspend fun deleteAll()

    @Query("SELECT * FROM Question ORDER BY RANDOM() LIMIT :num")
    suspend fun selectQuestions(num: Int): List<Question>

    @Query("SELECT * FROM Question")
    fun selectQuestions(): LiveData<List<Question>>

}

