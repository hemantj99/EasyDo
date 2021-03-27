package com.hemant.todoapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoTask_table")
public class TodoTask {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    public int id;
    @ColumnInfo(name = "task_title")
    private String title;
    @ColumnInfo(name = "task_status")
    private Boolean status;
    @ColumnInfo(name = "task_priority")
    private int priority;
    @ColumnInfo(name = "task_description")
    private String description;

    public TodoTask(@NonNull String title, @NonNull Boolean status,@NonNull int priority,String description) {
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.description = description;
    }
    @Ignore
    public TodoTask(int id , @NonNull String title, @NonNull Boolean status,@NonNull int priority,@NonNull String description) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.description = description;
    }


    public int getId(){ return this.id; }
    public String getTitle() { return this.title; }
    public Boolean getStatus() { return  this.status; }
    public int getPriority(){ return this.priority; }
    public String getDescription() { return this.description; }
}
