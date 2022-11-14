package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.*
import org.w3c.dom.Node

@Dao
abstract class NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(note: Note)

    @Delete
    abstract suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    abstract fun getAllNotes() : LiveData<List<Note>>

}