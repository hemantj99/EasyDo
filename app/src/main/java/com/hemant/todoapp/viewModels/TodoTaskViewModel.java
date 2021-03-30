package com.hemant.todoapp.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hemant.todoapp.repositories.TodoRepository;
import com.hemant.todoapp.db.TodoTask;

import java.util.List;

public class TodoTaskViewModel extends AndroidViewModel {
    public TodoRepository repo;
    private LiveData<List<TodoTask>> allTasks;

    public TodoTaskViewModel(@NonNull Application application) {
        super(application);
        repo = new TodoRepository(application);
        allTasks = repo.getAllTasks();
    }
    public LiveData<List<TodoTask>> getAllTasks(){
        return allTasks;
    }

    public void insert(TodoTask todoTask){
        repo.insert(todoTask);
    }
    public void delete(TodoTask todoTask){
        repo.delete(todoTask);
    }
    public void update(TodoTask todoTask){
        repo.update(todoTask);
    }

}
