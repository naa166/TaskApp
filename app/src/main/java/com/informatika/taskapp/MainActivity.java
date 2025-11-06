package com.informatika.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.informatika.taskapp.model.DataManager;
import com.informatika.taskapp.model.Task;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerTasks;
    private Button btnAddTask;

    private int editIndex = -1; // -1 = mode tambah, >=0 = mode edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerTasks = findViewById(R.id.container_tasks);
        btnAddTask = findViewById(R.id.btn_add_task);

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTasks();
    }

    private void displayTasks() {
        Log.d("MainActivity", "displayTasks() dipanggil. Jumlah tugas: " + DataManager.taskList.size());
        containerTasks.removeAllViews();
 

        for (int i = 0; i < DataManager.taskList.size(); i++) {
            Task task = DataManager.taskList.get(i);
            final int index = i;

            // Inflasi layout dari XML
            View itemView = getLayoutInflater().inflate(R.layout.item_task_row, containerTasks, false);
            TextView tv = itemView.findViewById(R.id.tv_task_info);
            Button btnDelete = itemView.findViewById(R.id.btn_delete);

            tv.setText("📌 " + task.title + "\n   Deadline Dari View: " + task.deadline);

            // Klik untuk edit
            tv.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                intent.putExtra("edit_index", index);
                startActivity(intent);
            });

            // Klik untuk hapus
            btnDelete.setOnClickListener(v -> {
                DataManager.taskList.remove(index);
                displayTasks();
                Toast.makeText(MainActivity.this, "Tugas dihapus", Toast.LENGTH_SHORT).show();
            });

            containerTasks.addView(itemView);
        }


        Toast.makeText(this, "Refresh daftar. Total: " + DataManager.taskList.size(), Toast.LENGTH_SHORT).show();
    }
}
