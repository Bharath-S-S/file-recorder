package com.example.filerecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_FILE_REQUEST = 1;
    private static final int EDIT_FILE_REQUEST = 2;
    // creating a variables for our recycler view.
    private RecyclerView coursesRV;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our variable for our recycler view and fab.
        coursesRV = findViewById(R.id.idRVCourses);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        // adding on click listener for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new course
                // and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this, NewFileActivity.class);
                startActivityForResult(intent, ADD_FILE_REQUEST);
            }
        });

        // setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);

        // initializing adapter for recycler view.
        final FileRVAdapter adapter = new FileRVAdapter();

        // setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);

        // passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        // below line is use to get all the courses from view modal.
        viewmodal.getAllCourses().observe(this, new Observer<List<FileModal>>() {
            @Override
            public void onChanged(List<FileModal> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "File deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(coursesRV);
        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new FileRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FileModal model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(MainActivity.this, NewFileActivity.class);
                intent.putExtra(NewFileActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewFileActivity.EXTRA_FILE_NAME, model.getFileName());
                intent.putExtra(NewFileActivity.EXTRA_DATEIN, model.getDateIn());
                intent.putExtra(NewFileActivity.EXTRA_DATEOUT, model.getDateOut());

                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(intent, EDIT_FILE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FILE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(NewFileActivity.EXTRA_FILE_NAME);
            String courseDescription = data.getStringExtra(NewFileActivity.EXTRA_DATEIN);
            String courseDuration = data.getStringExtra(NewFileActivity.EXTRA_DATEOUT);
            FileModal model = new FileModal(courseName, courseDescription, courseDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_FILE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewFileActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "File can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(NewFileActivity.EXTRA_FILE_NAME);
            String courseDesc = data.getStringExtra(NewFileActivity.EXTRA_DATEIN);
            String courseDuration = data.getStringExtra(NewFileActivity.EXTRA_DATEOUT);
            FileModal model = new FileModal(courseName, courseDesc, courseDuration);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "File updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not saved", Toast.LENGTH_SHORT).show();
        }
    }
}