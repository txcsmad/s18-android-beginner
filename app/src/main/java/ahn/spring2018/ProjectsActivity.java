package ahn.spring2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity {

    ArrayList<String> projectNames;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        projectNames = new ArrayList<>();
        projectNames.add("Scarne's Dice");
        projectNames.add("Schedule App");
        projectNames.add("Infinite Kittens");
        projectNames.add("MADGram");

        listView = findViewById(R.id.projects_list);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
                R.id.list_item, projectNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(position) {
                    case 0:
                        intent = new Intent(ProjectsActivity.this, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ProjectsActivity.this, ScheduleActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ProjectsActivity.this, KittensActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ProjectsActivity.this, FeedActivity.class);
                        break;
                    default:
                        intent = new Intent(ProjectsActivity.this, MainActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });
    }
}
