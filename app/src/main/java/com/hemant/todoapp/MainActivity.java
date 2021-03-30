package com.hemant.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.hemant.todoapp.adapter.TodoListAdapter;
import com.hemant.todoapp.db.TodoTask;
import com.hemant.todoapp.viewModels.TodoTaskViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public TodoTaskViewModel todoTaskViewModel;
    public CoordinatorLayout coordinatorLayout;
    private CardView noitemcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        noitemcv = findViewById(R.id.noItemcv);
        TodoListAdapter mtodoListAdapter = new TodoListAdapter(this);
        recyclerView= findViewById(R.id.home_recylerview);
        recyclerView.setAdapter(mtodoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoTaskViewModel = new ViewModelProvider(this).get(TodoTaskViewModel.class);
        todoTaskViewModel.getAllTasks().observe(this, new Observer<List<TodoTask>>() {
            @Override
            public void onChanged(List<TodoTask> todoTasks) {
                mtodoListAdapter.setTaskList(todoTasks);
                if(todoTasks.size() > 0){
                    // dialog disappear
                    noitemcv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    // dialog appear
                    noitemcv.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        mtodoListAdapter.setOnClickListener(new TodoListAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(TodoTask task) {
                todoTaskViewModel.delete(task);
                Snackbar.make(coordinatorLayout, "Task deleted!",Snackbar.LENGTH_SHORT).show();
            }
            @Override
            public void onUndoClick(TodoTask task) {
                todoTaskViewModel.insert(task);
                Snackbar.make(coordinatorLayout,"Task marked as Incomplete.",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCheckboxClick(TodoTask task) {
                todoTaskViewModel.insert(task);
                Snackbar.make(coordinatorLayout,"Great going! Complete rest of them.",Snackbar.LENGTH_SHORT).show();

            }
        });
    }
    // function to generate custom alert dialog
    public void customAlertDialog (){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View myview = inflater.inflate(R.layout.custom_dialog,null);

        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);
        dialog.show();

        final TextInputEditText type_et = myview.findViewById(R.id.taskName_et);
        final TextInputEditText description_et = myview.findViewById(R.id.descriptonet);
        final Spinner priority_s = myview.findViewById(R.id.priority);
        Button addButton = myview.findViewById(R.id.addBtn);
        final int[] priority = new int[1];
        priority[0] = 0;
        priority_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = type_et.getText().toString().trim();
                String description = description_et.getText().toString().trim();
                if(title.isEmpty()){
                    dialog.dismiss();
                    Snackbar.make(coordinatorLayout,"Title can't be empty",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(description.isEmpty()){
                    description = "no description";
                }
                TodoTask newtodo = new TodoTask(title,false, priority[0],description);
                todoTaskViewModel.insert(newtodo);
                dialog.dismiss();
                Snackbar.make(coordinatorLayout,"Task added to your list",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_btn :
                customAlertDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
