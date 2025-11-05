package com.informatika.TaskApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.widget.Toast;

import com.informatika.TaskApp.model.DataManager;
import com.informatika.TaskApp.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etDeadline;
    private Button btnSave;

    private int editIndex = -1; // -1 = mode tambah, >=0 = mode edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.et_title);
        etDeadline = findViewById(R.id.et_deadline);
        btnSave = findViewById(R.id.btn_save);


        // Cek apakah ini mode edit
        // 🔍 DEBUG: Cek apakah ada extra
        Log.d("AddTaskActivity", "Intent has edit_index? " + getIntent().hasExtra("edit_index"));
        if (getIntent().hasExtra("edit_index")) {
            editIndex = getIntent().getIntExtra("edit_index", -1);
            Task task = DataManager.taskList.get(editIndex);
            etTitle.setText(task.title);
            etDeadline.setText(task.deadline);
            btnSave.setText("Simpan Perubahan");
        }



        btnSave.setOnClickListener(v -> saveTask());

    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        if (title.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(this, "Judul dan deadline tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan ke daftar global
//        DataManager.taskList.add(new Task(title, deadline));
//        Toast.makeText(this, "Tugas berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
//        finish(); // kembali ke MainActivity

        if (editIndex == -1) {
            // Mode tambah
            DataManager.taskList.add(new Task(title, deadline));
            Toast.makeText(this, "Tugas ditambahkan!", Toast.LENGTH_SHORT).show();
        } else {
            // Mode edit
            Task updated = new Task(title, deadline);
            DataManager.taskList.set(editIndex, updated);
            Toast.makeText(this, "Tugas diperbarui!", Toast.LENGTH_SHORT).show();
        }
        finish();

    }
}