package ahn.spring2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScheduleActivity extends AppCompatActivity {

    public static final String EXTRA_DAY = "ahn.spring2018.ScheduleActivity.EXTRA_DAY";

    private static final String[] DAYS = new String[] {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    ListView listViewDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<>(this, R.layout.list_item_day, R.id.tv_day, DAYS);

        listViewDays = findViewById(R.id.list_schedule);
        listViewDays.setAdapter(daysAdapter);
        listViewDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScheduleActivity.this.openDay(position);
            }
        });
    }

    private void openDay(int day) {
        Intent dayIntent = new Intent(this, EventsActivity.class);
        dayIntent.putExtra(EXTRA_DAY, DAYS[day]);
        startActivity(dayIntent);
    }
}
