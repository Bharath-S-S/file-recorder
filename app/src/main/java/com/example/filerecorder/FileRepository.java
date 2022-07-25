package com.example.filerecorder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FileRepository {

    // below line is the create a variable
    // for dao and list for all courses.
    private Dao dao;
    private LiveData<List<FileModal>> allCourses;

    // creating a constructor for our variables
    // and passing the variables to it.
    public FileRepository(Application application) {
        FileDatabase database = com.example.filerecorder.FileDatabase.getInstance(application);
        dao = database.Dao();
        allCourses = dao.getAllFiles();
    }

    // creating a method to insert the data to our database.
    public void insert(FileModal model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(FileModal model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(FileModal model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<FileModal>> getAllCourses() {
        return allCourses;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<FileModal, Void, Void> {
        private Dao dao;

        private InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FileModal... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateCourseAsyncTask extends AsyncTask<FileModal, Void, Void> {
        private Dao dao;

        private UpdateCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FileModal... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<FileModal, Void, Void> {
        private Dao dao;

        private DeleteCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FileModal... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllCoursesAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllFiles();
            return null;
        }
    }
}
