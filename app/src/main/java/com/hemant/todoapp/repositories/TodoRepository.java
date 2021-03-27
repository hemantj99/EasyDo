package com.hemant.todoapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hemant.todoapp.db.TodoTask;
import com.hemant.todoapp.db.TodoTaskDAO;
import com.hemant.todoapp.db.TodoTaskRoomDatabase;

import java.util.List;

public class TodoRepository {
    private TodoTaskDAO mtodoTaskDao;
    private LiveData<List<TodoTask>> mallTasks;

    public TodoRepository(Application application) {
        TodoTaskRoomDatabase db = TodoTaskRoomDatabase.getDatabase(application);
        mtodoTaskDao = db.todoTaskDAO();
        mallTasks = mtodoTaskDao.getAllTasks();
    }
    public LiveData<List<TodoTask>> getAllTasks() {
        return mallTasks;
    }
    public void insert(final TodoTask todoTask) {
        TodoTaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mtodoTaskDao.insertTask(todoTask);
        });
    }
    public void delete(final TodoTask todoTask) {
        TodoTaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mtodoTaskDao.deleteTask(todoTask);
        });
    }
    public void update(final TodoTask todoTask) {
        TodoTaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mtodoTaskDao.updateTask(todoTask);
        });
    }

}
