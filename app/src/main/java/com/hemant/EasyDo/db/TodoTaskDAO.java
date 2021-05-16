package com.hemant.EasyDo.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoTaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TodoTask todoTask);

    @Delete
    void deleteTask(TodoTask todoTask);

    @Update
    void updateTask(TodoTask todoTask);

    @Query("SELECT * FROM todoTask_table ORDER BY task_priority DESC")
    LiveData<List<TodoTask>> getAllTasks();

    @Query("DELETE FROM todoTask_table")
    void deleteAll();
}
