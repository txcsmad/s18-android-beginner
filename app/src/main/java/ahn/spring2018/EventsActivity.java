package ahn.spring2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> events;
    ArrayAdapter<String> adapter;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        listView = findViewById(R.id.event_list);
        editText = findViewById(R.id.edit_text);

        events = new ArrayList<>();

        Intent intent = getIntent();
        String day = intent.getStringExtra("day");

        switch (day) {
            case "Monday":
                events.add("10-11AM Class");
                events.add("12-2PM Contemplating life");
                events.add("3-5PM Class");
                events.add("6-7PM Attempting to do homework");
                events.add("7-9PM Social Media");
                events.add("9-12AM Actually doing homework but not really");
                break;
            case "Tuesday":
                // TIK TOK LYRICS lmao
                events.add("8-9AM Wake up feeling like P-Diddy");
                events.add("9-11AM Grab glasses and go out the door ready to hit the city");
                events.add("11AM-12PM Go back to brush teeth");
                events.add("12PM-12AM Never come back for night");
                break;
            case "Wednesday":
                events.add("10-11AM Class");
                events.add("12-2PM Contemplating life");
                events.add("3-5PM Class");
                events.add("6-7PM Attempting to do homework");
                events.add("7-9PM Social Media");
                events.add("9-12AM Actually doing homework but not really");
                break;
            case "Thursday":
                // TIK TOK LYRICS lmao
                events.add("8-9AM Wake up feeling like P-Diddy");
                events.add("9-11AM Grab glasses and go out the door ready to hit the city");
                events.add("11AM-12PM Go back to brush teeth");
                events.add("12PM-12AM Never come back for night");
                break;
            case "Friday":
                events.add("10-11AM Class");
                events.add("12-2PM Contemplating life");
                events.add("3-5PM Class");
                events.add("6-7PM Attempting to do homework");
                events.add("7-9PM Social Media");
                events.add("9-12AM Actually doing homework but not really");
                break;
            default:
                events.add("No events found");
                break;
        }

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, events);
        listView.setAdapter(adapter);
    }

    public void updateSchedule(View view) {
        String text = editText.getText().toString();

        if (!text.equals("")) {
            events.add(text);

            adapter.notifyDataSetChanged();
            editText.setText("");
        }

    }
}
