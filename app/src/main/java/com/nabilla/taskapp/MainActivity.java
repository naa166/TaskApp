package com.dhendra.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhendra.taskapp.model.DataManager;
import com.dhendra.taskapp.model.Task;

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
//        versi Simple
//        for (Task task : DataManager.taskList) {
//            TextView tv = new TextView(this);
//            tv.setText("📌 " + task.title + "\n   Deadline: " + task.deadline);
//            tv.setTextSize(16);
//            tv.setPadding(0, 16, 0, 16);
//            containerTasks.addView(tv);
//        }

//        for (int i = 0; i < DataManager.taskList.size(); i++) {
//            Task task = DataManager.taskList.get(i);
//            final int index = i; // ✅ DIDEKLARASIKAN DI AWAL
//            // Layout horizontal: teks di kiri, tombol hapus di kanan
//            LinearLayout itemLayout = new LinearLayout(this);
//            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
//            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            ));
//            itemLayout.setPadding(0, 8, 0, 8);
//
//            // TextView untuk judul & deadline
//            TextView tv = new TextView(this);
//            tv.setText("📌 " + task.title + "\n   Deadline: " + task.deadline);
//            tv.setTextSize(16);
//            tv.setLayoutParams(new LinearLayout.LayoutParams(
//                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f // bobot 1 = isi sisa ruang
//            ));
//
//            tv.setOnClickListener(v -> {
//                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
//                intent.putExtra("edit_index", index);
//                startActivity(intent);
//            });
//
//            // Tombol Hapus
//            Button btnDelete = new Button(this);
//            btnDelete.setText("❌");
//            btnDelete.setTextSize(12);
//            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            ));
//
//            btnDelete.setOnClickListener(v -> {
//                DataManager.taskList.remove(index);
//                displayTasks(); // refresh langsung
//                Toast.makeText(MainActivity.this, "Tugas dihapus", Toast.LENGTH_SHORT).show();
//            });
//
//            itemLayout.addView(tv);
//            itemLayout.addView(btnDelete);
//            containerTasks.addView(itemLayout);
//        }

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