package com.example.android.room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Question")
data class Question (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name="text")
    var text: String,
    @ColumnInfo(name="answer_one")
    var answer_one: String,
    @ColumnInfo(name="answer_two")
    var answer_two: String,
    @ColumnInfo(name="answer_three")
    var answer_three: String,
    @ColumnInfo(name="answer_four")
    var answer_four: String,
    @ColumnInfo(name="hint")
    var hint: String
)