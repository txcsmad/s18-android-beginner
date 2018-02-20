package ahn.spring2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleActivity extends AppCompatActivity {
    ArrayList<String> days;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday"));

        listView = findViewById(R.id.schedule_list);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, days);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element = (String) listView.getItemAtPosition(position);

                Intent intent = new Intent(ScheduleActivity.this, EventsActivity.class);
                intent.putExtra("day", element);
                startActivity(intent);
            }
        });
    }
}
