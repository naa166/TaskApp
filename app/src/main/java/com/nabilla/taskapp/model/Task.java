package com.dhendra.taskapp.model;

public class Task {
    public String title;
    public String deadline; // format: "10/05/2025"

    public Task(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
    }
}
