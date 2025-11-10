package com.informatika.todolistnabilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.Toast;

import com.informatika.todolistnabilla.model.DataManager;
import com.informatika.todolistnabilla.model.Task;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etDeadline;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah/Edit Tugas");

        etTitle = findViewById(R.id.et_title);
        etDeadline = findViewById(R.id.et_deadline);
        btnSave = findViewById(R.id.btn_save);

        // Check if this is edit mode
        if (getIntent().hasExtra("edit_index")) {
            int editIndex = getIntent().getIntExtra("edit_index", -1);
            Task task = DataManager.taskList.get(editIndex);
            etTitle.setText(task.title);
            etDeadline.setText(task.deadline);
            btnSave.setText("Simpan Perubahan");
        }

        btnSave.setOnClickListener(v -> saveTask());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        if (title.isEmpty() || deadline.isEmpty()) {
            Toast.makeText(this, "Judul dan deadline tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getIntent().hasExtra("edit_index")) {
            int editIndex = getIntent().getIntExtra("edit_index", -1);
            Task updated = new Task(title, deadline);
            DataManager.taskList.set(editIndex, updated);
            Toast.makeText(this, "Tugas diperbarui!", Toast.LENGTH_SHORT).show();
        } else {
            DataManager.taskList.add(new Task(title, deadline));
            Toast.makeText(this, "Tugas ditambahkan!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

