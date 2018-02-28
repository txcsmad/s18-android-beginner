package ahn.spring2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    private final ArrayList<String> events = new ArrayList<>();

    private ArrayAdapter<String> eventAdapter;

    ListView eventsList;
    EditText etAddEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        fillEvents();

        this.eventsList = findViewById(R.id.list_events);
        this.etAddEvent = findViewById(R.id.et_add_event);

        this.eventAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, events);

        eventsList.setAdapter(eventAdapter);
    }

    public void updateSchedule(View view) {
        String currentText = etAddEvent.getText().toString();
        events.add(currentText);

        eventAdapter.notifyDataSetChanged();
    }

    private void fillEvents() {
        String day = getIntent().getStringExtra(ScheduleActivity.EXTRA_DAY);
        day = day.toLowerCase();

        switch (day) {
            case "sunday":
                events.add("Make Coffee @ 8am");
                events.add("Finish DSC prelab.");
                break;
            case "monday":
                events.add("Go to mechanism design.");
                events.add("DSC Lab");
                break;
            case "tuesday":
                events.add("Statistics");
                events.add("Lunch at Cabo Bob's.");
                break;
            case "wednesday":
                events.add("Meet with Benito.");
                events.add("Stats discussion.");
                break;
            case "thursday":
                events.add("Vector Calc Quiz.");
                events.add("Drive to Houston.");
                break;
            case "friday":
                events.add("Statistics homework due at 5pm.");
                events.add("Volunteer at robotics from 5-8pm.");
                break;
            case "saturday":
                events.add("Make Coffee @ 8am.");
                events.add("Go back to sleep.");
                break;
        }
    }
}
