package com.example.filerecorder;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

// below line is for setting table name.
@Entity(tableName = "file_table")
public class FileModal {

    // below line is to auto increment
    // id for each course.
    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    // below line is a variable
    // for course name.
    private String fileName;

    // below line is use for
    // course description.
    private String dateIn;

    // below line is use
    // for course duration.
    private String dateOut;

    // below line we are creating constructor class.
    // inside constructor class we are not passing
    // our id because it is incrementing automatically
    public FileModal(String fileName, String dateIn, String dateOut) {
        this.fileName = fileName;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    // on below line we are creating
    // getter and setter methods.
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
