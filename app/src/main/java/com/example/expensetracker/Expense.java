package com.example.expensetracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "expense type")
    private String expenseType;

    public Expense(int id, String title, int amount, String expenseType) {
        this.id = id;
        this.amount = amount;
        this.title = title;
        this.expenseType = expenseType;
    }

    @Ignore
    public Expense(String title, int amount, String expenseType) {
        this.amount = amount;
        this.title = title;
        this.expenseType = expenseType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
