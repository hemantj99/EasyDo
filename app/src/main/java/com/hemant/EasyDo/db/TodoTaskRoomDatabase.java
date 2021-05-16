package com.hemant.EasyDo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TodoTask.class}, version = 1, exportSchema = false)
public abstract class TodoTaskRoomDatabase extends RoomDatabase{
    public abstract TodoTaskDAO todoTaskDAO();
    private static volatile TodoTaskRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodoTaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoTaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoTaskRoomDatabase.class, "TodoTask_Database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
